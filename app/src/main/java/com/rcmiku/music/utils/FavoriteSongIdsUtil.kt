package com.rcmiku.music.utils

import android.content.Context
import com.rcmiku.music.data.favoriteSongIdsDatastore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object FavoriteSongIdsUtil {

    suspend fun addSongId(context: Context, songId: Long) {
        context.favoriteSongIdsDatastore.updateData { currentData ->
            if (songId !in currentData.songIdsList) {
                currentData.toBuilder().addSongIds(songId).build()
            } else {
                currentData
            }
        }
    }

    suspend fun removeSongId(context: Context, songId: Long) {
        context.favoriteSongIdsDatastore.updateData { currentData ->
            val newSongIds = currentData.songIdsList.filter { it != songId }
            currentData.toBuilder().clearSongIds().addAllSongIds(newSongIds).build()
        }
    }

    suspend fun updateSongIds(context: Context, songIds: List<Long>) {
        context.favoriteSongIdsDatastore.updateData { currentData ->
            currentData.toBuilder().clearSongIds().addAllSongIds(songIds).build()
        }
    }

    fun getAllSongIds(context: Context): MutableList<Long> {
        return runBlocking(Dispatchers.IO) {
            context.favoriteSongIdsDatastore.data.first().songIdsList
                ?: emptyList<Long>().toMutableList()
        }
    }
}
