package com.rcmiku.music.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import com.rcmiku.music.FavoriteSongIds
import java.io.InputStream
import java.io.OutputStream

object FavoriteSongIdsSerializer : Serializer<FavoriteSongIds> {
    override val defaultValue: FavoriteSongIds
        get() = FavoriteSongIds.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): FavoriteSongIds {
        return try {
            FavoriteSongIds.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            defaultValue
        }
    }

    override suspend fun writeTo(t: FavoriteSongIds, output: OutputStream) = t.writeTo(output)

}

val Context.favoriteSongIdsDatastore: DataStore<FavoriteSongIds> by dataStore(
    fileName = "favorite_song_ids.pb",
    serializer = FavoriteSongIdsSerializer
)