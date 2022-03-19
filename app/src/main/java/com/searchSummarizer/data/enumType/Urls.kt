package com.searchSummarizer.data.enumType

sealed class Urls(val url: String) {
    data class GoogleFavicon(private val iconUrl: String) :
        Urls("https://t1.gstatic.com/faviconV2?client=SOCIAL&type=FAVICON&fallback_opts=TYPE,SIZE,URL&url=$iconUrl/&size=64")

    object Default : Urls("https://www.google.com/")

    data class GoogleSearch(private val keyword: String) : Urls("${Default.url}search?q=$keyword")
}
