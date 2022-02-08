package com.searchSummarizer.ui.browse

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import com.searchSummarizer.app.SearchSummarizerViewModel
import com.searchSummarizer.ui.components.BrowseHeader
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BrowseScreen(
    modifier: Modifier = Modifier,
    vm: SearchSummarizerViewModel = getViewModel()
) {
    val extended = vm.extended
    Surface(
        color = MaterialTheme.colors.primary.copy(alpha = 0.5f),
        modifier = modifier.fillMaxSize()
    ) {
        Column(modifier.padding(top = 48.dp)) {
            BrowseHeader(
                extended = extended,
                onTabClick = {
                    vm.keyword = ""
                    if (extended) vm.extended = !extended
                },
                favIconUrls = vm.favIconUrls
            )
            BrowseBody(
                extended = extended
            )
        }
    }
}

@Preview
@Composable
fun BrowseScreenPreview() {
    // BrowseScreen()
}

@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BrowseBody(
    extended: Boolean,
    modifier: Modifier = Modifier,
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    vm: SearchSummarizerViewModel = getViewModel()
) {
    var backEnabled by remember { mutableStateOf(true) }
    var webView: WebView? = null
    AnimatedVisibility(
        visible = !extended,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Surface(
            modifier = modifier.fillMaxSize()
        ) {
        }
    }
    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    backEnabled = view?.canGoBack() == true
                    vm.currentUrl = url.toString()
                }
            }
            if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK) && useDarkTheme) {
                WebSettingsCompat.setForceDark(
                    this.settings,
                    WebSettingsCompat.FORCE_DARK_ON
                )
            }
            settings.javaScriptEnabled = true
            loadUrl(vm.currentUrl)
            webView = this
        }
    }, update = {
        webView = it
        webView?.loadUrl(vm.currentUrl)
    })

    BackHandler(
        enabled = backEnabled,
        onBack = {
            webView?.goBack()
        }
    )
}
