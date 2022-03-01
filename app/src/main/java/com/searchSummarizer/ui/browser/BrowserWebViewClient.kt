package com.searchSummarizer.ui.browser

import android.graphics.Bitmap
import android.os.Build
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import com.searchSummarizer.app.browser.BrowserViewModel

class BrowserWebViewClient(
    val vm: BrowserViewModel
) : WebViewClient() {
    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        requireNotNull(url)
        val urlHistoryItem = vm.urlHistory[vm.webViewIndex]
        if (urlHistoryItem.isEmpty() || urlHistoryItem.last() != url) vm.urlHistory[vm.webViewIndex].add(url)
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
