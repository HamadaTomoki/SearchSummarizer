package com.searchSummarizer.ui.browse

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.searchSummarizer.ui.components.BrowseTab

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BrowseScreen(
    favIconUrls: List<String>,
    modifier: Modifier = Modifier,
) {
    var extended by rememberSaveable { mutableStateOf(true) }
    val onTabClick = { if (extended) extended = !extended }
    Surface(
        color = MaterialTheme.colors.primary.copy(alpha = 0.5f),
        modifier = modifier.fillMaxSize()
    ) {
        Column(modifier.padding(top = 48.dp)) {
            BrowseTab(
                extended = extended,
                onTabClick = onTabClick,
                favIconUrls = favIconUrls
            )
            BrowseBody(
                url = "https://google.com/",
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
    url: String,
    extended: Boolean,
    modifier: Modifier = Modifier
) {
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
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(url)
        }
    }, update = {
        it.loadUrl(url)
    })
}
