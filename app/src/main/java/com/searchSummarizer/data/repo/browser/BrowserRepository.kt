package com.searchSummarizer.data.repo.browser

import com.searchSummarizer.data.model.BrowserHistory
import com.searchSummarizer.data.model.SummarizedUrl
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json

interface BrowserRepository {
    val browserHistoryFlow: Flow<BrowserHistory>

    suspend fun saveBrowserHistory(browserHistory: BrowserHistory)

    suspend fun findSummarizedUrl(id: String): SummarizedUrl?

    companion object {
        fun create(): HttpClient = HttpClient(Android) {
            install(JsonFeature) {
                serializer = KotlinxSerializer(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
            engine {
                connectTimeout = 60_000
                socketTimeout = 60_000
            }
        }
    }
}
