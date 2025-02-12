package com.rcmiku.music.extensions

import android.content.Context
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.rcmiku.music.constants.currentPlayMediaIdKey
import com.rcmiku.music.utils.SongListUtil
import com.rcmiku.music.utils.dataStore
import com.rcmiku.music.utils.get
import com.rcmiku.ncmapi.model.CloudSong
import com.rcmiku.ncmapi.model.Radio
import com.rcmiku.ncmapi.model.Song
import kotlinx.coroutines.flow.MutableStateFlow

internal val Player.currentMediaItems: List<MediaItem>
    get() {
        return List(mediaItemCount, ::getMediaItemAt)
    }

internal val cacheSongs: MutableStateFlow<List<Song>?> = MutableStateFlow(null)

fun Player.init(context: Context) {
    val currentPlayMediaId = context.dataStore[currentPlayMediaIdKey]
    SongListUtil.loadSongList()?.toMediaItemList()?.let { playlist ->
        if (currentMediaItems.isEmpty()) {
            setMediaItems(playlist)
            val index =
                currentMediaItems.indexOfFirst { it.mediaId == currentPlayMediaId.toString() }
            if (index != -1)
                seekToDefaultPosition(index)
            prepare()
        }
    }
}

fun Player.setPlaylist(songs: List<Song>) {
    if (cacheSongs.value != songs) {
        cacheSongs.value = songs
        setMediaItems(songs.toMediaItemList())
        SongListUtil.saveSongList(songs)
    }
}

fun Player.setCloudSongPlaylist(uid: Long, cloudSongs: List<CloudSong>) {
    cacheSongs.value = null
    setMediaItems(cloudSongs.toCloudSongMediaItemList(uid = uid))
}

fun Player.setRadioPlaylist(radio: List<Radio>) {
    cacheSongs.value = null
    setMediaItems(radio.toRadioMediaItemList())
}

fun Player.addSong(song: Song) {
    if (nextMediaItemIndex != C.INDEX_UNSET) {
        val songIndex = currentMediaItems.indexOfFirst { it.mediaId == song.id.toString() }
        if (songIndex != -1) {
            playMediaAt(songIndex)
        } else {
            addMediaItem(nextMediaItemIndex, song.toMediaItem())
            playMediaAt(nextMediaItemIndex)
            SongListUtil.saveSong(song, nextMediaItemIndex)
        }
    } else {
        setMediaItem(song.toMediaItem())
        playMediaAt()
        SongListUtil.saveSong(song)
    }
}

fun Player.addToPlaylist(song: Song) {
    if (currentMediaItems.isNotEmpty()) {
        val songIndex = currentMediaItems.indexOfFirst { it.mediaId == song.id.toString() }
        if (songIndex == -1) {
            addMediaItem(song.toMediaItem())
            SongListUtil.saveSong(song)
        }
    } else {
        setMediaItem(song.toMediaItem())
        SongListUtil.saveSong(song)
    }
}

fun Player.insertToPlaylist(song: Song) {
    if (nextMediaItemIndex != C.INDEX_UNSET) {
        val songIndex = currentMediaItems.indexOfFirst { it.mediaId == song.id.toString() }
        if (songIndex != -1) {
            moveMediaItem(songIndex, nextMediaItemIndex)
        } else {
            addMediaItem(nextMediaItemIndex, song.toMediaItem())
            SongListUtil.saveSong(song, nextMediaItemIndex)
        }
    } else {
        setMediaItem(song.toMediaItem())
        SongListUtil.saveSong(song)
    }
}

fun Player.removeSong(mediaId: String) {
    if (currentMediaItems.isNotEmpty()) {
        val songIndex = currentMediaItems.indexOfFirst { it.mediaId == mediaId }
        if (songIndex != -1) {
            removeMediaItem(songIndex)
            SongListUtil.removeSong(mediaId.toLong())
        }
    }
}

fun Player.playMediaAt(index: Int? = null) {
    if (index != -1)
        index?.let { seekToDefaultPosition(it) }
    prepare()
    play()
}

fun Player.playMediaAtId(id: Long? = null) {
    val index = currentMediaItems.indexOfFirst { it.mediaId == id.toString() }
    if (index != -1)
        seekToDefaultPosition(index)
    prepare()
    play()
}