package com.searchSummarizer.data.repo.browser

import com.searchSummarizer.data.model.BrowserHistory
import kotlinx.coroutines.flow.Flow

interface BrowserRepository {
    val browserHistoryFlow: Flow<BrowserHistory>

    suspend fun saveBrowserHistory(browserHistory: BrowserHistory)
}
