package com.searchSummarizer.app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.searchSummarizer.ui.HttpRoutes

class SearchSummarizerViewModel : ViewModel() {

    private val tabUrls: List<String> = mutableListOf(
        "https://google.com",
        "https://stackoverflow.com",
        "https://qiita.com/",
        "https://medium.com/",
        "https://youtube.com/",
        "https://developer.android.com/",
        "https://driveomjfjdsf/drive/u/0/my-drive",
        "https://github.com/",
    )

    val favIconUrls: List<String> = tabUrls.map { url -> HttpRoutes.FAVICON_URL(url) }

    var extended by mutableStateOf(true)

    var keyword: String by mutableStateOf("")

    var currentUrl: String by mutableStateOf("https://www.google.com")
    var urlIndex: Int by mutableStateOf(0)

    var backEnabled by mutableStateOf(false)

    fun search() {
        currentUrl = "https://www.google.com/search?q=$keyword"
    }
}
