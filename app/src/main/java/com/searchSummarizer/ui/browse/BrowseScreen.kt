package com.searchSummarizer.ui.browse

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.searchSummarizer.R

@Composable
fun BrowseScreen(
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colors.primary.copy(alpha = 0.5f),
        modifier = modifier.fillMaxSize()
    ) {
        Column() {
            Spacer(modifier.height(60.dp))
            BrowseTab()
            BrowseBody(url = "https://medium.com/")
        }
    }
}

@Preview
@Composable
fun BrowseScreenPreview() {
    BrowseScreen()
}

@Composable
fun BrowseTab(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = 8.dp,
                horizontal = 16.dp,
            )
            .height(40.dp)

    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_search_summarizer),
            contentDescription = null,
            modifier = modifier
                .fillMaxHeight()
                .padding(4.dp)
        )
        Spacer(modifier.padding(4.dp))
        Surface(
            shape = RoundedCornerShape(25.dp),
            modifier = modifier
                .weight(1f)
                .clickable { }
        ) {
            Row(
                modifier = modifier.padding(
                    horizontal = 12.dp
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = null,
                    modifier = modifier.fillMaxHeight()
                )
            }
        }
        Spacer(modifier.padding(4.dp))
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = null,
            tint = MaterialTheme.colors.onSurface,
            modifier = modifier.fillMaxHeight()
        )
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun BrowseBody(url: String) {
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
