package com.rcmiku.music.utils

import com.rcmiku.ncmapi.model.Song
import com.rcmiku.ncmapi.utils.json
import java.io.File

object SongListUtil {

    private const val SONG_LIST = "song_list.json"
    private var file: File? = null
    private var songList: List<Song> = emptyList()

    fun init(file: File) {
        if (!file.exists()) file.mkdir()
        this.file = file
    }

    fun saveSong(song: Song) {
        val updatedSongList = songList.toMutableList()
        updatedSongList.add(song)
        saveSongList(updatedSongList)
    }

    fun removeSong(songId: Long) {
        val updatedSongList = songList.toMutableList()
        updatedSongList.removeIf { it.id == songId }
        songList = updatedSongList
        saveSongList(updatedSongList)
    }

    fun saveSong(song: Song, index: Int) {
        val updatedSongList = songList.toMutableList()
        updatedSongList.add(index, song)
        saveSongList(updatedSongList)
    }


    fun saveSongList(songList: List<Song>) {
        this.songList = songList
        val songListJson = json.encodeToString<List<Song>>(songList)
        File(file, SONG_LIST).writeText(songListJson)
    }

    fun loadSongList(): List<Song>? {
        val songListJson = File(file, SONG_LIST)
        return if (songListJson.exists()) {
            songList = json.decodeFromString<List<Song>>(songListJson.readText())
            return songList
        } else {
            null
        }
    }
}
