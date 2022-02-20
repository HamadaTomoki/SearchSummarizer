package com.searchSummarizer.ui.browse

import android.graphics.Bitmap
import android.os.Build
import android.webkit.URLUtil
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import com.searchSummarizer.app.SearchSummarizerViewModel
import com.searchSummarizer.data.SiteInfo

class BrowseWebViewClient(
    val vm: SearchSummarizerViewModel
) : WebViewClient() {
    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        vm.siteInfoList[vm.currentTabIndex] = SiteInfo(
            url = url.toString(),
            title = if (!URLUtil.isValidUrl(view?.title)) view?.title.toString() else return,
            backEnabled = view?.canGoBack() == true,
            history = view?.copyBackForwardList()
        )
    }

    override fun onPageFinished(view: WebView, url: String?) {
        vm.siteInfoList[vm.currentTabIndex] = SiteInfo(
            url = url.toString(),
            title = if (!URLUtil.isValidUrl(view.title)) view.title.toString() else return,
            backEnabled = view.canGoBack(),
            history = view.copyBackForwardList()
        )
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
