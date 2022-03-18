package com.searchSummarizer.data.repo.browser

import android.util.Log
import androidx.datastore.core.DataStore
import com.searchSummarizer.data.enumType.HttpRoutes
import com.searchSummarizer.data.model.BrowserHistory
import com.searchSummarizer.data.model.SummarizedUrl
import com.searchSummarizer.data.repo.browser.BrowserRepository.Companion.create
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException
import kotlinx.coroutines.flow.flow

class BrowserRepositoryImpl(
    private val browserHistoryStore: DataStore<BrowserHistory>
) : BrowserRepository {

    private val TAG: String = "BrowserRepo"

    override val browserHistoryFlow: Flow<BrowserHistory>
        get() = browserHistoryStore.data
            .catch { exception ->
                if (exception is IOException) {
                    Log.e(TAG, "Error reading sort order preferences.", exception)
                    emit(BrowserHistory())
                } else {
                    throw exception
                }
            }

    override suspend fun saveBrowserHistory(browserHistory: BrowserHistory) {
        browserHistoryStore.updateData { browserHistory }
    }

    override suspend fun findSummarizedUrl(id: String): SummarizedUrl? =
        create().get(HttpRoutes.SummarizedUrl(id = id).url) {
            contentType(ContentType.Application.Json)
        }
}
