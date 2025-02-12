package com.rcmiku.music.extensions

import android.net.Uri
import android.os.Bundle
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.rcmiku.ncmapi.api.player.PlayerApi
import com.rcmiku.ncmapi.api.player.SongLevel
import com.rcmiku.ncmapi.model.CloudSong
import com.rcmiku.ncmapi.model.Radio
import com.rcmiku.ncmapi.model.Song
import com.rcmiku.ncmapi.utils.json

fun Song.toMediaItem() =
    MediaItem.Builder()
        .setUri(this.id.toString())
        .setMediaId(this.id.toString())
        .setMediaMetadata(
            MediaMetadata.Builder()
                .setArtist(this.ar.joinToString("/") { it.name })
                .setTitle(this.name)
                .setArtworkUri(this.al.picUrl.toUri())
                .setExtras(Bundle().apply {
                    putString(
                        "song",
                        json.encodeToString(this@toMediaItem)
                    )
                })
                .build()
        )
        .build()


fun List<Song>.toMediaItemList() =
    this.map { song ->
        val extras = Bundle().apply { putString("song", json.encodeToString(song)) }
        MediaItem.Builder()
            .setUri(song.id.toString())
            .setMediaId(song.id.toString())
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setArtist(song.ar.joinToString("/") { artist -> artist.name })
                    .setTitle(song.name)
                    .setArtworkUri(song.al.picUrl.toUri())
                    .setExtras(extras)
                    .build()
            )
            .build()
    }


fun List<CloudSong>.toCloudSongMediaItemList(uid: Long) =
    this.map { cloudSong ->
        MediaItem.Builder()
            .setUri("${cloudSong.simpleSong.id}_$uid")
            .setMediaId(cloudSong.simpleSong.id.toString())
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setArtist(cloudSong.artist)
                    .setTitle(cloudSong.simpleSong.name)
                    .setArtworkUri(cloudSong.simpleSong.al?.picUrl?.toUri())
                    .build()
            )
            .build()
    }

fun List<Radio>.toRadioMediaItemList() =
    this.map { radio ->
        MediaItem.Builder()
            .setUri(radio.mainSong.id.toString())
            .setMediaId(radio.mainSong.id.toString())
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setArtist(radio.mainSong.artists.joinToString { it.name })
                    .setTitle(radio.mainSong.name)
                    .setArtworkUri(radio.coverUrl.toUri())
                    .build()
            )
            .build()
    }

suspend fun updateMediaItemUri(songId: String, songLevel: SongLevel): Uri? {
    return PlayerApi.songPlayUrlV1(songId, songLevel = songLevel)
        .getOrNull()?.data?.firstOrNull()?.url?.toUri()
}