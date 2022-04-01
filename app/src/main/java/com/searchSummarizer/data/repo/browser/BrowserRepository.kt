package com.searchSummarizer.data.repo.browser

import com.searchSummarizer.data.model.BrowserHistory
import com.searchSummarizer.data.model.SummarizedUrl
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json

/**
 * browser機能を提供するrepository
 *
 * @constructor BrowserRepositoryを作成します。
 */
interface BrowserRepository {

    /**
     * Browser履歴
     */
    val browserHistoryFlow: Flow<BrowserHistory>

    /**
     * browser履歴を保存します。
     *
     * @param browserHistory browser履歴
     */
    suspend fun saveBrowserHistory(browserHistory: BrowserHistory)

    /**
     * Summarized url
     *
     * @param id 検索するSummarizedUrlのid
     * @return 検索で見つかったSummarizedUrl
     */
    suspend fun findSummarizedUrl(id: String): SummarizedUrl?

    companion object {
        /**
         * HttpClientを作成します。
         *
         * @return Android用HttpClient
         */
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
