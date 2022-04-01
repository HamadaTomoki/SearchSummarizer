package com.searchSummarizer.data.enumType

/**
 * host通信のhttp route
 *
 * @property url http routeのurl
 * @constructor HttpRouteを作成します。
 */
sealed class HttpRoutes(private val url: String) {

    /**
     * host先のbase url
     *
     * @constructor BaseUrlを作成します。
     */
    private object BaseUrl : Urls("https://search-summarizer.herokuapp.com")

    /**
     * 共有されたSummarizedUrl
     *
     * @property id SummarizedUrlのid
     * @constructor SharedSummarizedUrlを作成します。
     */
    data class SharedSummarizedUrl(private val id: String) :
        Urls("${BaseUrl.url}/summarized/urls?id=$id")
}
