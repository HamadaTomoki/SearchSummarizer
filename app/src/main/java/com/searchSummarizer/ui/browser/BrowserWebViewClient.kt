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
    private val vm: BrowserViewModel
) : WebViewClient() {
    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        requireNotNull(url)
        val tabIndex = vm.tabIndex
        val urlHistoryItem = vm.urlHistory[tabIndex]
        if (urlHistoryItem.isEmpty() || urlHistoryItem.last() != url) {
            urlHistoryItem.add(url)
        }
        vm.backEnabled = urlHistoryItem.size > 1
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        val title = view?.title
        requireNotNull(title)
        vm.titles[vm.tabIndex] = title
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
