package com.searchSummarizer.ui.browser

import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import com.searchSummarizer.app.browser.BrowserViewModel

class BrowserWebViewClient(
    private val browserViewModel: BrowserViewModel
) : WebViewClient() {
    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        requireNotNull(url)
        val tabIndex = browserViewModel.tabIndex
        val urlHistory = browserViewModel.urlHistory
        val urlHistoryItem = urlHistory[tabIndex]
        if (urlHistoryItem.isEmpty() || urlHistoryItem.last() != url) {
            urlHistory[tabIndex].add(url)
        }
        browserViewModel.backEnabled = urlHistoryItem.size > 1
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
