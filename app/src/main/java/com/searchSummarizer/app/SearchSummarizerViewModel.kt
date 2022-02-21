package com.searchSummarizer.app

import android.app.Application
import android.webkit.URLUtil
import android.webkit.WebView
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel

class SearchSummarizerViewModel(application: Application) : AndroidViewModel(application) {

    private val context get() = getApplication<Application>().applicationContext

    var webViewList: MutableList<WebView> = mutableStateListOf()

    init {
        webViewList.add(WebView(context))
        webViewList[0].loadUrl("https://google.com")
    }
    var extended: Boolean by mutableStateOf(true)

    var keyword: String by mutableStateOf("")

    var webViewIndex: Int by mutableStateOf(0)

    var backEnabled: Boolean by mutableStateOf(false)

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
        webViewList.add(WebView(context))
        webViewIndex = webViewList.size - 1
        webViewList[webViewIndex].loadUrl("https://google.com")
        extended = !extended
    }

    fun onTabClick() {
        if (extended) {
            keyword = ""
            extended = !extended
        }
    }
}
