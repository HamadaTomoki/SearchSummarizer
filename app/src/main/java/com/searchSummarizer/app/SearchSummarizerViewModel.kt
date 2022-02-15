package com.searchSummarizer.app

import android.app.Application
import android.content.Context
import android.util.Log
import android.webkit.URLUtil
import android.webkit.WebView
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.searchSummarizer.data.Urls
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.KoinApplication.Companion.init

class SearchSummarizerViewModel(application: Application) : AndroidViewModel(application) {

    private val context get() = getApplication<Application>().applicationContext

    private val _browseWebView = MutableStateFlow<WebView?>(null)
    var browseWebView: StateFlow<WebView?> = _browseWebView

    init {
        viewModelScope.launch {
            _browseWebView.value = WebView(context)
        }
    }

    var tabUrls: List<String> = mutableListOf(
        "https://google.com",
        "https://stackoverflow.com",
        "https://qiita.com/",
        "https://medium.com/",
        "https://youtube.com/",
        "https://developer.android.com/",
        "https://driveomjfjdsf/drive/u/0/my-drive",
        "https://github.com/",
    )
        private set

    val favIconUrls: List<String> = tabUrls.map { url -> Urls.GoogleFavicon(url).url }

    var extended by mutableStateOf(true)

    var keyword: String by mutableStateOf("")

    var currentUrl: String by mutableStateOf("https://www.google.com")
    var currentTitle: String by mutableStateOf("")
    var urlIndex: Int by mutableStateOf(0)

    var backEnabled by mutableStateOf(false)

    fun onSearch() {

        extended = !extended
        if (keyword == "") return
        Log.i("myweb", "throw")
        currentUrl = "https://www.google.com/search?q=$keyword"
    }

    fun onTabClick() {
        keyword = ""
        if (extended) extended = !extended // only once
    }

}
