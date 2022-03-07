package com.searchSummarizer.app.browser

import android.util.Log
import android.webkit.URLUtil
import android.webkit.WebView
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.searchSummarizer.data.enumType.Urls
import com.searchSummarizer.data.model.BrowserHistory
import com.searchSummarizer.data.repo.browser.BrowserRepository
import com.searchSummarizer.data.repo.context.ContextRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BrowserViewModel(
    private val browserRepository: BrowserRepository,
    private val contextRepository: ContextRepository,
) : ViewModel() {

    var webView: WebView by mutableStateOf(WebView(contextRepository.createContext()))
    var tabIndex: Int by mutableStateOf(0)
    var urlHistory: MutableList<MutableList<String>> =
        mutableStateListOf(mutableListOf(Urls.Google.url))

    var extended: Boolean by mutableStateOf(true)

    var keyword: String by mutableStateOf("")

    var backEnabled: Boolean by mutableStateOf(false)

    fun onBack() {
        urlHistory[tabIndex].removeLast()
        webView.loadUrl(urlHistory[tabIndex].last())
    }

    fun onSearch() {
        extended = !extended
        val url = when {
            keyword == "" -> return // empty
            URLUtil.isValidUrl(keyword) -> keyword // valid url
            else -> Urls.GoogleSearch(keyword).url // keyword
        }
        webView.loadUrl(url)
    }

    fun onSwitchTab(tabIndex: Int) {
        webView.loadUrl(urlHistory[tabIndex].last())
        this.tabIndex = tabIndex
        extended = !extended
    }

    fun onAddTab() {
        urlHistory.add(mutableListOf(Urls.Google.url))
        tabIndex++
        webView.loadUrl(Urls.Google.url)
        extended = !extended
    }

    fun onTabClick() {
        if (extended) {
            keyword = ""
            extended = !extended
        }
    }

    fun restoreBrowserHistory() {
        viewModelScope.launch {
            browserRepository.browserHistoryFlow.collect { browserHistory ->
                if (browserHistory.urls.isNotEmpty()) {
                    tabIndex = browserHistory.selectedTabIndex
                    urlHistory = browserHistory.urls.replace(" ", "").split("^").map {
                        it.split(",").toMutableList()
                    }.toMutableList()
                    webView.loadUrl(urlHistory[tabIndex].last())
                } else {
                    webView.loadUrl(urlHistory[0].last())
                }
            }
        }
    }

    fun saveBrowserHistory() {
        viewModelScope.launch {
            browserRepository.saveBrowserHistory(
                BrowserHistory(
                    tabIndex,
                    urlHistory.joinToString("^") // one dimensional array separator is "^"
                    { it.joinToString(",") } // two dimensional array separator is ","
                )
            )
        }
    }
}
