package com.searchSummarizer.data

import android.webkit.WebBackForwardList

data class SiteInfo(
    var url: String,
    var title: String,
    var backEnabled: Boolean,
    val history: WebBackForwardList?,
)
