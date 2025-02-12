package com.rcmiku.music.playback

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.annotation.OptIn
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player.REPEAT_MODE_ALL
import androidx.media3.common.TrackSelectionParameters
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.ResolvingDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.Renderer
import androidx.media3.exoplayer.RenderersFactory
import androidx.media3.exoplayer.audio.AudioRendererEventListener
import androidx.media3.exoplayer.audio.MediaCodecAudioRenderer
import androidx.media3.exoplayer.mediacodec.MediaCodecSelector
import androidx.media3.exoplayer.metadata.MetadataOutput
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.text.TextOutput
import androidx.media3.exoplayer.video.VideoRendererEventListener
import androidx.media3.session.CommandButton
import androidx.media3.session.CommandButton.ICON_UNDEFINED
import androidx.media3.session.DefaultMediaNotificationProvider
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import androidx.media3.session.SessionCommand
import androidx.media3.session.SessionResult
import com.google.common.collect.ImmutableList
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import com.rcmiku.music.MainActivity
import com.rcmiku.music.R
import com.rcmiku.music.constants.MediaSessionConstants
import com.rcmiku.music.constants.audioQualityKey
import com.rcmiku.music.constants.use40DpIconKey
import com.rcmiku.music.data.favoriteSongIdsDatastore
import com.rcmiku.music.extensions.updateMediaItemUri
import com.rcmiku.music.utils.FavoriteSongIdsUtil
import com.rcmiku.music.utils.dataStore
import com.rcmiku.music.utils.enumPreference
import com.rcmiku.music.utils.preference
import com.rcmiku.ncmapi.api.account.AccountApi
import com.rcmiku.ncmapi.api.player.SongLevel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.coroutines.runBlocking

@UnstableApi
class PlaybackService : MediaSessionService() {

    private var mediaSession: MediaSession? = null
    private var favoriteSongIds: List<Long> by mutableStateOf(emptyList())
    private val use40DpIcon by preference(this, use40DpIconKey, false)
    private val audioQuality by enumPreference(this, audioQualityKey, SongLevel.STANDARD)

    private val favoriteButton: CommandButton
        get() = CommandButton.Builder(ICON_UNDEFINED)
            .setCustomIconResId(if (use40DpIcon) R.drawable.ic_favorite_40_dp else R.drawable.ic_favorite)
            .setDisplayName("like")
            .setSessionCommand(MediaSessionConstants.CommandToggleLike)
            .build()

    private val favoriteButtonOn: CommandButton
        get() = CommandButton.Builder(ICON_UNDEFINED)
            .setCustomIconResId(if (use40DpIcon) R.drawable.ic_favorite_fill_40_dp else R.drawable.ic_favorite_fill)
            .setDisplayName("like_on")
            .setSessionCommand(MediaSessionConstants.CommandToggleLike)
            .build()

    private val shuffleButton: CommandButton
        get() = CommandButton.Builder(ICON_UNDEFINED)
            .setCustomIconResId(if (use40DpIcon) R.drawable.ic_shuffle_40_dp else R.drawable.ic_shuffle)
            .setDisplayName("shuffle")
            .setSessionCommand(MediaSessionConstants.CommandToggleShuffle)
            .build()

    private val shuffleButtonOn: CommandButton
        get() = CommandButton.Builder(ICON_UNDEFINED)
            .setCustomIconResId(if (use40DpIcon) R.drawable.ic_shuffle_on_40_dp else R.drawable.ic_shuffle_on)
            .setDisplayName("shuffle_on")
            .setSessionCommand(MediaSessionConstants.CommandToggleShuffle)
            .build()

    private var scope = CoroutineScope(Dispatchers.Main) + SupervisorJob()

    @OptIn(UnstableApi::class)
    override fun onCreate() {
        super.onCreate()
        setMediaNotificationProvider(
            DefaultMediaNotificationProvider(
                this,
                { 2000 },
                DefaultMediaNotificationProvider.DEFAULT_CHANNEL_ID,
                DefaultMediaNotificationProvider.DEFAULT_CHANNEL_NAME_RESOURCE_ID
            ).apply {
                setSmallIcon(R.drawable.ic_music_note)
            }
        )
        val audioOnlyRenderersFactory =
            RenderersFactory {
                    handler: Handler,
                    _: VideoRendererEventListener,
                    audioListener: AudioRendererEventListener,
                    _: TextOutput,
                    _: MetadataOutput,
                ->
                arrayOf<Renderer>(
                    MediaCodecAudioRenderer(
                        this,
                        MediaCodecSelector.DEFAULT,
                        handler,
                        audioListener
                    )
                )
            }

        val player = ExoPlayer.Builder(this, audioOnlyRenderersFactory)
            .apply {
                val dataSourceFactory: ResolvingDataSource.Factory = ResolvingDataSource.Factory(
                    DefaultHttpDataSource.Factory()
                ) { dataSpec ->
                    runBlocking {
                        dataSpec.withUri(
                            updateMediaItemUri(dataSpec.uri.path.orEmpty(), audioQuality)
                                ?: throw PlaybackException(
                                    null,
                                    null,
                                    PlaybackException.ERROR_CODE_REMOTE_ERROR
                                )
                        )
                    }
                }
                setMediaSourceFactory(DefaultMediaSourceFactory(dataSourceFactory))
            }.build()

        val audioOffloadPreferences =
            TrackSelectionParameters.AudioOffloadPreferences.Builder()
                .setAudioOffloadMode(TrackSelectionParameters.AudioOffloadPreferences.AUDIO_OFFLOAD_MODE_ENABLED)
                .setIsGaplessSupportRequired(true)
                .build()
        player.trackSelectionParameters =
            player.trackSelectionParameters
                .buildUpon()
                .setAudioOffloadPreferences(audioOffloadPreferences)
                .build()
        player.repeatMode = REPEAT_MODE_ALL
        mediaSession = MediaSession.Builder(this, player)
            .setSessionActivity(
                PendingIntent.getActivity(
                    this,
                    0,
                    Intent(this, MainActivity::class.java),
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )
            ).setCallback(MediaSessionCallback())
            .setCustomLayout(ImmutableList.of(favoriteButton, shuffleButton)).build()
        observeIconPreference()
        observeFavoriteSongIds()
    }

    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
    }

    @OptIn(UnstableApi::class)
    override fun onTaskRemoved(rootIntent: Intent?) {
        pauseAllPlayersAndStopSelf()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? =
        mediaSession

    fun updateCustomLayout() {
        mediaSession?.setCustomLayout(
            ImmutableList.of(
                if (favoriteSongIds.contains(mediaSession?.player?.currentMediaItem?.mediaId?.toLong())) favoriteButtonOn else favoriteButton,
                if (mediaSession?.player?.shuffleModeEnabled != false) shuffleButtonOn else shuffleButton
            )
        )
    }

    override fun onUpdateNotification(session: MediaSession, startInForegroundRequired: Boolean) {
        updateCustomLayout()
        super.onUpdateNotification(session, startInForegroundRequired)
    }

    private fun toggleLike(like: Boolean, songId: Long) {
        scope.launch {
            AccountApi.songLike(like, songId).onSuccess {
                if (like) {
                    FavoriteSongIdsUtil.addSongId(applicationContext, songId)
                } else {
                    FavoriteSongIdsUtil.removeSongId(applicationContext, songId)
                }
            }
        }
    }

    @kotlin.OptIn(FlowPreview::class)
    private fun observeIconPreference() {
        scope.launch {
            applicationContext.dataStore.data.debounce(1000)
                .map { it[use40DpIconKey] ?: false }.distinctUntilChanged().collect {
                    updateCustomLayout()
                }
        }
    }

    @kotlin.OptIn(FlowPreview::class)
    private fun observeFavoriteSongIds() {
        scope.launch {
            applicationContext.favoriteSongIdsDatastore.data.debounce(1000).distinctUntilChanged()
                .collect { favoriteSongs ->
                    favoriteSongIds = favoriteSongs.songIdsList
                    updateCustomLayout()
                }
        }
    }

    private inner class MediaSessionCallback : MediaSession.Callback {

        override fun onConnect(
            session: MediaSession,
            controller: MediaSession.ControllerInfo
        ): MediaSession.ConnectionResult {
            return MediaSession.ConnectionResult.AcceptedResultBuilder(session)
                .setAvailableSessionCommands(
                    MediaSession.ConnectionResult.DEFAULT_SESSION_COMMANDS.buildUpon()
                        .add(MediaSessionConstants.CommandToggleLike)
                        .add(MediaSessionConstants.CommandToggleShuffle)
                        .build()
                )
                .build()
        }

        override fun onCustomCommand(
            session: MediaSession,
            controller: MediaSession.ControllerInfo,
            customCommand: SessionCommand,
            args: Bundle
        ): ListenableFuture<SessionResult> {
            if (customCommand.customAction == MediaSessionConstants.ACTION_TOGGLE_SHUFFLE) {
                session.player.shuffleModeEnabled = !session.player.shuffleModeEnabled
                updateCustomLayout()
                return Futures.immediateFuture(SessionResult(SessionResult.RESULT_SUCCESS))
            }
            if (customCommand.customAction == MediaSessionConstants.ACTION_TOGGLE_LIKE) {
                session.player.currentMediaItem?.mediaId?.toLongOrNull()?.let {
                    toggleLike(it !in favoriteSongIds, it)
                }
                return Futures.immediateFuture(SessionResult(SessionResult.RESULT_SUCCESS))
            }
            return super.onCustomCommand(session, controller, customCommand, args)
        }
    }
}
