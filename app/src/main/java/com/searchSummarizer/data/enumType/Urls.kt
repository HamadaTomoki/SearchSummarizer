package com.searchSummarizer.data.enumType

/**
 * appで使用するurl
 *
 * @property url appで使用するurl
 * @constructor Urlsを作成します。
 */
sealed class Urls(val url: String) {

    /**
     * Google Favicon SnatcherAPIのurl
     *
     * @property iconUrl websiteのdomain url
     * @constructor GoogleFaviconを作成します。
     */
    data class GoogleFavicon(private val iconUrl: String) :
        Urls("https://t1.gstatic.com/faviconV2?client=SOCIAL&type=FAVICON&fallback_opts=TYPE,SIZE,URL&url=$iconUrl/&size=64")

    /**
     * web pageに表示するdefault url
     *
     * @constructor Defaultを作成します。
     */
    object Default : Urls("https://www.google.com/")

    /**
     * Web検索で使用するurl
     *
     * @property keyword 検索keyword
     * @constructor GoogleSearchを作成します。
     */
    data class GoogleSearch(private val keyword: String) : Urls("${Default.url}search?q=$keyword")
}
