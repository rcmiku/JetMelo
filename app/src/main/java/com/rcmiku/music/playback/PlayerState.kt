package com.rcmiku.music.playback

import android.content.Context
import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.media3.common.AudioAttributes
import androidx.media3.common.DeviceInfo
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.PlaybackParameters
import androidx.media3.common.Player
import androidx.media3.common.Timeline
import androidx.media3.common.TrackSelectionParameters
import androidx.media3.common.Tracks
import androidx.media3.common.VideoSize
import androidx.media3.common.text.CueGroup
import com.rcmiku.music.constants.autoSkipNextOnErrorKey
import com.rcmiku.music.utils.dataStore
import com.rcmiku.music.utils.get

fun Player.state(context: Context): PlayerState {
    return PlayerStateImpl(this, context)
}

interface PlayerState {
    val player: Player

    val timeline: Timeline

    val mediaItemIndex: Int

    val tracks: Tracks

    val currentMediaItem: MediaItem?

    val mediaMetadata: MediaMetadata

    val playlistMetadata: MediaMetadata

    val isLoading: Boolean

    val availableCommands: Player.Commands

    val trackSelectionParameters: TrackSelectionParameters

    @get:Player.State
    val playbackState: Int

    val playWhenReady: Boolean

    @get:Player.PlaybackSuppressionReason
    val playbackSuppressionReason: Int

    val isPlaying: Boolean

    @get:Player.RepeatMode
    val repeatMode: Int

    val shuffleModeEnabled: Boolean

    val playerError: PlaybackException?

    val playbackParameters: PlaybackParameters

    val seekBackIncrement: Long

    val seekForwardIncrement: Long

    val maxSeekToPreviousPosition: Long

    val audioAttributes: AudioAttributes

    val volume: Float

    val deviceInfo: DeviceInfo

    val deviceVolume: Int

    val isDeviceMuted: Boolean

    val videoSize: VideoSize

    val cues: CueGroup

    val countDownTimer: CountDownTimer?

    val remainingTime: Long

    val isSleepTimerSet: Boolean

    fun dispose()

    fun startTimer(time: Long)

    fun cancelTimer()
}

internal class PlayerStateImpl(
    override val player: Player,
    context: Context
) : PlayerState {

    override var remainingTime by mutableLongStateOf(0L)
        private set

    override var isSleepTimerSet by mutableStateOf(false)
        private set

    override var countDownTimer: CountDownTimer? = null

    override fun startTimer(time: Long) {
        isSleepTimerSet = true
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(time * 1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = millisUntilFinished / 1000
            }

            override fun onFinish() {
                isSleepTimerSet = false
                player.pause()
            }
        }
        countDownTimer?.start()
    }

    override fun cancelTimer() {
        isSleepTimerSet = false
        countDownTimer?.cancel()
    }

    override var timeline: Timeline by mutableStateOf(player.currentTimeline)
        private set

    override var mediaItemIndex: Int by mutableIntStateOf(player.currentMediaItemIndex)
        private set

    override var tracks: Tracks by mutableStateOf(player.currentTracks)
        private set

    override var currentMediaItem: MediaItem? by mutableStateOf(player.currentMediaItem)
        private set

    override var mediaMetadata: MediaMetadata by mutableStateOf(player.mediaMetadata)
        private set

    override var playlistMetadata: MediaMetadata by mutableStateOf(player.playlistMetadata)
        private set

    override var isLoading: Boolean by mutableStateOf(player.isLoading)
        private set

    override var availableCommands: Player.Commands by mutableStateOf(player.availableCommands)
        private set

    override var trackSelectionParameters: TrackSelectionParameters by mutableStateOf(player.trackSelectionParameters)
        private set

    @get:Player.State
    override var playbackState: Int by mutableIntStateOf(player.playbackState)
        private set

    override var playWhenReady: Boolean by mutableStateOf(player.playWhenReady)
        private set

    @get:Player.PlaybackSuppressionReason
    override var playbackSuppressionReason: Int by mutableIntStateOf(player.playbackSuppressionReason)
        private set

    override var isPlaying: Boolean by mutableStateOf(player.isPlaying)
        private set

    @get:Player.RepeatMode
    override var repeatMode: Int by mutableIntStateOf(player.repeatMode)
        private set

    override var shuffleModeEnabled: Boolean by mutableStateOf(player.shuffleModeEnabled)
        private set

    override var playerError: PlaybackException? by mutableStateOf(player.playerError)
        private set

    override var playbackParameters: PlaybackParameters by mutableStateOf(player.playbackParameters)
        private set

    override var seekBackIncrement: Long by mutableLongStateOf(player.seekBackIncrement)
        private set

    override var seekForwardIncrement: Long by mutableLongStateOf(player.seekForwardIncrement)
        private set

    override var maxSeekToPreviousPosition: Long by mutableLongStateOf(player.maxSeekToPreviousPosition)
        private set

    override var audioAttributes: AudioAttributes by mutableStateOf(player.audioAttributes)
        private set

    override var volume: Float by mutableFloatStateOf(player.volume)
        private set

    override var deviceInfo: DeviceInfo by mutableStateOf(player.deviceInfo)
        private set

    override var deviceVolume: Int by mutableIntStateOf(player.deviceVolume)
        private set

    override var isDeviceMuted: Boolean by mutableStateOf(player.isDeviceMuted)
        private set

    override var videoSize: VideoSize by mutableStateOf(player.videoSize)
        private set

    override var cues: CueGroup by mutableStateOf(player.currentCues)
        private set

    private val listener = object : Player.Listener {

        override fun onTimelineChanged(timeline: Timeline, reason: Int) {
            this@PlayerStateImpl.timeline = timeline
            this@PlayerStateImpl.mediaItemIndex = player.currentMediaItemIndex
        }

        override fun onTracksChanged(tracks: Tracks) {
            this@PlayerStateImpl.tracks = tracks
        }

        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            this@PlayerStateImpl.currentMediaItem = mediaItem
        }

        override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
            this@PlayerStateImpl.mediaMetadata = mediaMetadata
        }

        override fun onPlaylistMetadataChanged(mediaMetadata: MediaMetadata) {
            this@PlayerStateImpl.playlistMetadata = mediaMetadata
        }

        override fun onIsLoadingChanged(isLoading: Boolean) {
            this@PlayerStateImpl.isLoading = isLoading
        }

        override fun onAvailableCommandsChanged(availableCommands: Player.Commands) {
            this@PlayerStateImpl.availableCommands = availableCommands
        }

        override fun onTrackSelectionParametersChanged(parameters: TrackSelectionParameters) {
            this@PlayerStateImpl.trackSelectionParameters = parameters
        }

        override fun onPlaybackStateChanged(@Player.State playbackState: Int) {
            this@PlayerStateImpl.playbackState = playbackState
        }

        override fun onPlayWhenReadyChanged(
            playWhenReady: Boolean,
            @Player.PlayWhenReadyChangeReason reason: Int
        ) {
            this@PlayerStateImpl.playWhenReady = playWhenReady
        }

        override fun onPlaybackSuppressionReasonChanged(playbackSuppressionReason: Int) {
            this@PlayerStateImpl.playbackSuppressionReason = playbackSuppressionReason
        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            this@PlayerStateImpl.isPlaying = isPlaying
        }

        override fun onRepeatModeChanged(repeatMode: Int) {
            this@PlayerStateImpl.repeatMode = repeatMode
        }

        override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
            this@PlayerStateImpl.shuffleModeEnabled = shuffleModeEnabled
        }

        override fun onPlayerErrorChanged(error: PlaybackException?) {
            this@PlayerStateImpl.playerError = error
        }

        override fun onPositionDiscontinuity(
            oldPosition: Player.PositionInfo,
            newPosition: Player.PositionInfo,
            reason: Int
        ) {
            this@PlayerStateImpl.mediaItemIndex = player.currentMediaItemIndex
        }

        override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {
            this@PlayerStateImpl.playbackParameters = playbackParameters
        }

        override fun onSeekBackIncrementChanged(seekBackIncrementMs: Long) {
            this@PlayerStateImpl.seekBackIncrement = seekBackIncrementMs
        }

        override fun onSeekForwardIncrementChanged(seekForwardIncrementMs: Long) {
            this@PlayerStateImpl.seekForwardIncrement = seekForwardIncrementMs
        }

        override fun onMaxSeekToPreviousPositionChanged(maxSeekToPreviousPositionMs: Long) {
            this@PlayerStateImpl.maxSeekToPreviousPosition = maxSeekToPreviousPositionMs
        }

        override fun onAudioAttributesChanged(audioAttributes: AudioAttributes) {
            this@PlayerStateImpl.audioAttributes = audioAttributes
        }

        override fun onVolumeChanged(volume: Float) {
            this@PlayerStateImpl.volume = volume
        }

        override fun onDeviceInfoChanged(deviceInfo: DeviceInfo) {
            this@PlayerStateImpl.deviceInfo = deviceInfo
        }

        override fun onDeviceVolumeChanged(volume: Int, muted: Boolean) {
            this@PlayerStateImpl.deviceVolume = volume
            this@PlayerStateImpl.isDeviceMuted = muted
        }

        override fun onVideoSizeChanged(videoSize: VideoSize) {
            this@PlayerStateImpl.videoSize = videoSize
        }

        override fun onCues(cues: CueGroup) {
            this@PlayerStateImpl.cues = cues
        }

        override fun onPlayerError(error: PlaybackException) {
            if (context.dataStore.get(autoSkipNextOnErrorKey, false) &&
                player.hasNextMediaItem()
            ) {
                player.seekToNext()
                player.prepare()
                player.playWhenReady = true
            }
        }
    }

    init {
        player.addListener(listener)
    }

    override fun dispose() {
        player.removeListener(listener)
    }
}
