package com.searchSummarizer.ui.browse

import android.graphics.Bitmap
import android.os.Build
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import com.searchSummarizer.app.SearchSummarizerViewModel

class BrowseWebViewClient(
    val vm: SearchSummarizerViewModel
) : WebViewClient() {
    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        requireNotNull(url)
        val urlHistoryItem = vm.urlHistory[vm.webViewIndex]
        if (urlHistoryItem.isEmpty() || urlHistoryItem.last() != url) urlHistoryItem.add(url)
        vm.backEnabled = urlHistoryItem.size > 1
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        super.onReceivedError(view, request, error)
        if (error?.errorCode == ERROR_HOST_LOOKUP) {
            view?.loadUrl("about:blank")
        }
    }
}
