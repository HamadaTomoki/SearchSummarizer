package com.searchSummarizer.app.browser

import android.webkit.URLUtil
import android.webkit.WebView
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.searchSummarizer.data.model.BrowserHistory
import com.searchSummarizer.data.repo.browser.BrowserRepository
import com.searchSummarizer.data.repo.context.ContextRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BrowserViewModel(
    private val browserRepository: BrowserRepository,
    private val contextRepository: ContextRepository,
) : ViewModel() {

    var webViewList: MutableList<WebView> = mutableStateListOf(WebView(contextRepository.createContext()))
    var webViewIndex: Int by mutableStateOf(0)
    var urlHistory: MutableList<MutableList<String>> = mutableStateListOf(mutableListOf("https://www.google.com/"))

    var extended: Boolean by mutableStateOf(true)

    var keyword: String by mutableStateOf("")

    var backEnabled: Boolean by mutableStateOf(false)

    fun onBack() {
        urlHistory[webViewIndex].removeLast()
        webViewList[webViewIndex].loadUrl(urlHistory[webViewIndex].last())
    }

    fun onSearch() {
        extended = !extended
        val url = when {
            keyword == "" -> return // empty
            URLUtil.isValidUrl(keyword) -> keyword // valid url
            else -> "https://www.google.com/search?q=$keyword" // keyword
        }
        webViewList[webViewIndex].loadUrl(url)
    }

    fun onSwitchTab(tabIndex: Int) {
        webViewIndex = tabIndex
        extended = !extended
    }

    fun onAddTab() {
        webViewList.add(WebView(contextRepository.createContext()))
        urlHistory.add(mutableListOf())
        webViewIndex = webViewList.size - 1
        webViewList[webViewIndex].loadUrl("https://www.google.com/")
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
                    webViewList = mutableListOf()
                    webViewIndex = browserHistory.selectedTabIndex
                    urlHistory = browserHistory.urls.replace(" ", "").split("^").map {
                        it.split(",").toMutableList()
                    }.toMutableList()
                    repeat(urlHistory.size) {
                        webViewList.add(WebView(contextRepository.createContext()))
                        webViewList[it].loadUrl(urlHistory[it].last())
                    }
                } else {
                    webViewList[0].loadUrl(urlHistory[0].last())
                }
            }
        }
    }

    fun saveBrowserHistory() {
        viewModelScope.launch {
            browserRepository.saveBrowserHistory(
                BrowserHistory(
                    webViewIndex,
                    urlHistory.joinToString("^") // one dimensional array separator is "^"
                    { it.joinToString(",") } // two dimensional array separator is ","
                )
            )
        }
    }
}
