package com.searchSummarizer.data.enumType

sealed class HttpRoutes(val url: String) {

    private object BaseUrl : Urls("https://search-summarizer.herokuapp.com")

    data class SummarizedUrl(private val id: String) :
        Urls("${BaseUrl.url}/summarized/urls?id=$id")
}
