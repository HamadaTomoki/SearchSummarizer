package com.searchSummarizer.data.repo.browser

import android.util.Log
import androidx.datastore.core.DataStore
import com.searchSummarizer.data.model.BrowserHistory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException

class BrowserRepositoryImpl(
    private val browserHistoryStore: DataStore<BrowserHistory>
) : BrowserRepository {

    private val TAG: String = "UserPreferencesRepo"

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

    override suspend fun updateBrowserHistory(browserHistory: BrowserHistory) {
        browserHistoryStore.updateData { browserHistory }
    }
}
