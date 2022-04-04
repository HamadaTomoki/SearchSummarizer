package com.searchSummarizer.data.serializer

import android.content.Context
import android.util.Log
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.searchSummarizer.data.model.BrowserHistory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.StringFormat
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

val Context.dataStore by dataStore(
    fileName = "web_history.json",
    serializer = BrowserHistorySerializer()
)

@OptIn(ExperimentalSerializationApi::class)
class BrowserHistorySerializer(
    private val stringFormat: StringFormat = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }
) : Serializer<BrowserHistory> {

    override val defaultValue: BrowserHistory = BrowserHistory()

    override suspend fun readFrom(input: InputStream): BrowserHistory {
        try {
            val bytes = input.readBytes()
            val string = bytes.decodeToString()
            Log.i("datastore", "read $string")
            return stringFormat.decodeFromString(string)
        } catch (e: SerializationException) {
            throw CorruptionException("Cannot read stored data", e)
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun writeTo(t: BrowserHistory, output: OutputStream) {
        val string = stringFormat.encodeToString(t)
        Log.i("datastore", "write $string")
        val bytes = string.encodeToByteArray()
        output.write(bytes)
        output.flush()
    }
}
