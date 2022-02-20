package com.searchSummarizer.app

import android.app.Application
import android.webkit.URLUtil
import android.webkit.WebView
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.searchSummarizer.data.SiteInfo

class SearchSummarizerViewModel(application: Application) : AndroidViewModel(application) {

    private val context get() = getApplication<Application>().applicationContext

    var browseWebView by mutableStateOf(WebView(context))

    var siteInfoList: MutableList<SiteInfo> =
        mutableListOf(SiteInfo("https://google.com", "Google", false, null))

    var extended by mutableStateOf(true)

    var keyword: String by mutableStateOf("")

    var currentTabIndex by mutableStateOf(0)

    fun onSearch() {
        extended = !extended
        siteInfoList[currentTabIndex].url = when {
            keyword == "" -> return // empty
            URLUtil.isValidUrl(keyword) -> keyword // valid url
            else -> "https://www.google.com/search?q=$keyword" // keyword
        }
    }

    fun onSwitchTab(tabIndex: Int) {
        currentTabIndex = tabIndex
        extended = !extended
    }

    fun onAddTab() {
        siteInfoList.add(
            SiteInfo(
                "https://google.com",
                "Google",
                false,
                null
            )
        )
        currentTabIndex = siteInfoList.size - 1
        extended = !extended
    }

    fun onTabClick() {
        if (extended) {
            keyword = ""
            extended = !extended
        }
    }
}
