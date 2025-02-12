package com.rcmiku.music.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import com.rcmiku.music.SearchHistory
import java.io.InputStream
import java.io.OutputStream

object SearchHistorySerializer : Serializer<SearchHistory> {

    override val defaultValue: SearchHistory = SearchHistory.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): SearchHistory {
        return try {
            SearchHistory.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            defaultValue
        }
    }

    override suspend fun writeTo(t: SearchHistory, output: OutputStream) = t.writeTo(output)
}

val Context.searchHistoryDataStore: DataStore<SearchHistory> by dataStore(
    fileName = "search_history.pb",
    serializer = SearchHistorySerializer
)