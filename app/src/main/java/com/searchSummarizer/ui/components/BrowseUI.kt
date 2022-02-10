package com.searchSummarizer.ui.components

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.searchSummarizer.R
import com.searchSummarizer.app.SearchSummarizerViewModel
import com.searchSummarizer.data.Urls
import com.searchSummarizer.ui.browse.BrowseWebViewClient

/** Header -------------------------------------------------- */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BrowseHeader(
    extended: Boolean,
    onTabClick: () -> Unit,
    favIconUrls: List<String>,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawLine(
                    color = Color.LightGray,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 8f
                )
            }
            .padding(
                vertical = 8.dp,
                horizontal = 12.dp,
            )
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        AppIcon(extended)
        Spacer(Modifier.padding(2.dp))
        BrowseTab(
            modifier = Modifier.weight(1f),
            onTabClick = onTabClick,
            extended = extended,
            favIconUrls = favIconUrls
        )
        Spacer(Modifier.padding(6.dp))
        TabManagerIcon(extended)
        MoreOptionIcon()
    }
}

@ExperimentalAnimationApi
@Composable
private fun AppIcon(extended: Boolean) {
    AnimatedVisibility(visible = extended) {
        Image(
            painter = painterResource(id = R.drawable.ic_search_summarizer),
            contentDescription = null,
            modifier = Modifier.padding(6.dp)
        )
    }
}

@ExperimentalAnimationApi
@Composable
private fun TabManagerIcon(
    extended: Boolean,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    AnimatedVisibility(visible = extended) {
        Row {
            Icon(
                imageVector = Icons.Filled.Tab,
                contentDescription = null,
                tint = MaterialTheme.colors.onSurface,
                modifier = modifier
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {}
            )
            Spacer(Modifier.padding(6.dp))
        }
    }
}

@Composable
private fun MoreOptionIcon() {
    val interactionSource = remember { MutableInteractionSource() }
    Icon(
        imageVector = Icons.Filled.MoreVert,
        contentDescription = null,
        tint = MaterialTheme.colors.onSurface,
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {}
    )
}

@ExperimentalAnimationApi
@Composable
private fun BrowseTab(
    modifier: Modifier,
    onTabClick: () -> Unit,
    extended: Boolean,
    favIconUrls: List<String>,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Surface(
        shape = RoundedCornerShape(25.dp),
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onTabClick() }
    ) {
        Row(
            modifier = modifier.padding(
                horizontal = 12.dp,
                vertical = 8.dp
            )
        ) {
            AnimatedVisibility(visible = extended) {
                BrowseTabContent(favIconUrls, modifier.weight(1f))
            }
            AnimatedVisibility(visible = !extended) {
                BrowseTextField()
            }
        }
    }
}

@Composable
private fun BrowseTabContent(
    favIconUrls: List<String>,
    modifier: Modifier = Modifier,
) {
    LazyRow(modifier) {
        items(favIconUrls) { url ->
            Favicon(
                url = url,
                modifier.size(32.dp)
            )
        }
    }
}

/** Body -------------------------------------------------- */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BrowseBody(
    vm: SearchSummarizerViewModel = viewModel()
) {
    val extended = vm.extended
    Box {
        BrowseWebView(vm, Modifier.fillMaxSize())
        AnimatedVisibility(
            visible = !extended,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Surface(
                Modifier.fillMaxSize()
            ) {
                Column {
                    CurrentTab(
                        title = vm.currentTitle,
                        url = vm.currentUrl
                    )
                }
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
private fun BrowseWebView(
    vm: SearchSummarizerViewModel,
    modifier: Modifier = Modifier,
    useDarkTheme: Boolean = isSystemInDarkTheme()
) {
    var webView: WebView? = null
    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = BrowseWebViewClient(vm)
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
        if (vm.extended) {
            webView?.loadUrl(vm.currentUrl)
            vm.currentTitle = webView?.title ?: ""
        }
    }, modifier = modifier)

    BackHandler(
        enabled = vm.backEnabled,
        onBack = {
            webView?.goBack()
        }
    )
}

@Composable
fun CurrentTab(
    title: String,
    url: String
) {
    Column {
        Row(
            modifier = Modifier.padding(
                vertical = 8.dp,
                horizontal = 20.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Favicon(
                url = Urls.GoogleFavicon(url).url,
                modifier = Modifier.size(28.dp)
            )
            Spacer(Modifier.padding(8.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    text = title,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = url,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.primary)
                )
            }
            Spacer(Modifier.padding(12.dp))
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = null
            )
            Spacer(Modifier.padding(12.dp))
            Icon(
                imageVector = Icons.Filled.ContentCopy,
                contentDescription = null
            )
            Spacer(Modifier.padding(12.dp))
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = null
            )
        }
        Divider()
    }
}

@Composable
private fun Favicon(
    url: String,
    modifier: Modifier = Modifier
) {
    Image(
        painter = rememberImagePainter(
            data = url,
            builder = {
                error(R.drawable.baseline_public)
                crossfade(true)
                placeholder(R.drawable.ic_launcher_foreground)
                transformations(CircleCropTransformation())
            }
        ),
        contentDescription = null,
        modifier = modifier
    )
}
