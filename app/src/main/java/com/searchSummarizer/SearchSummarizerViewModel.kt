package com.searchSummarizer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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

    val keyword: String by mutableStateOf("")
}
