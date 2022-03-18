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
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import com.searchSummarizer.R
import com.searchSummarizer.app.browser.BrowserViewModel
import com.searchSummarizer.data.enumType.Urls
import com.searchSummarizer.ui.browser.BrowserWebViewClient
import org.koin.androidx.compose.getViewModel

/** Header -------------------------------------------------- */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BrowserHeader(vm: BrowserViewModel = getViewModel()) {
    val favIconUrls = vm.urlHistory.map { Urls.GoogleFavicon(it.last()).url }
    val extended = vm.expanded
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
        SearchTab(
            modifier = Modifier.weight(1f),
            onSearchTabClick = vm::onTabClick,
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
private fun SearchTab(
    modifier: Modifier,
    onSearchTabClick: () -> Unit,
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
            ) { onSearchTabClick() }
    ) {
        Row(
            modifier = modifier.padding(
                horizontal = 12.dp,
                vertical = 8.dp
            )
        ) {
            AnimatedVisibility(visible = extended) {
                SearchTabContent(favIconUrls, modifier.weight(1f))
            }
            AnimatedVisibility(visible = !extended) {
                SearchTextField()
            }
        }
    }
}

@Composable
private fun SearchTabContent(
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    vm: BrowserViewModel = getViewModel()
) {

    val value = vm.keyword
    val onValueChange: (String) -> Unit = { vm.keyword = it }

    Row {
        Image(
            painter = painterResource(id = R.drawable.ic_search_summarizer),
            contentDescription = null,
            modifier = modifier
                .background(
                    shape = RoundedCornerShape(50.dp),
                    color = MaterialTheme.colors.primary.copy(alpha = 0.3f)
                )
                .padding(4.dp)
        )
        Spacer(modifier.padding(4.dp))
        Box(contentAlignment = Alignment.CenterStart) {
            val keyboardController = LocalSoftwareKeyboardController.current
            val requester = FocusRequester()
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search,
                    keyboardType = KeyboardType.Uri
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        vm.onSearch()
                        keyboardController?.hide()
                    }
                ),
                textStyle = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onSurface),
                singleLine = true,
                cursorBrush = SolidColor(MaterialTheme.colors.onSurface),
                modifier = modifier.focusRequester(requester)
            )
            SideEffect {
                requester.requestFocus()
            }
            if (value.isEmpty()) {
                Text(
                    text = "検索語句またはウェブアドレスを入力",
                    color = Color.Gray,
                    fontSize = MaterialTheme.typography.body1.fontSize
                )
            }
        }
    }
}

/** Body -------------------------------------------------- */
@OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun BrowserBody(extended: Boolean) {
    Box {
        if (extended) BrowserWebView(Modifier.fillMaxSize())
        ExpandedView()
    }
}

/** Body main components -------------------------------------------------- */
@SuppressLint("SetJavaScriptEnabled")
@Composable
private fun BrowserWebView(
    modifier: Modifier = Modifier,
    vm: BrowserViewModel = getViewModel(),
    useDarkTheme: Boolean = isSystemInDarkTheme(),
) {
    val webView = vm.webView
    AndroidView(factory = {
        webView.also {
            it.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            it.webViewClient = BrowserWebViewClient(vm)
            if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                WebSettingsCompat.setForceDark(
                    it.settings,
                    if (useDarkTheme) WebSettingsCompat.FORCE_DARK_ON
                    else WebSettingsCompat.FORCE_DARK_OFF
                )
            }
            it.scrollBarStyle = WebView.SCROLLBARS_INSIDE_OVERLAY
            it.settings.javaScriptEnabled = true
            it.settings.builtInZoomControls = true
            it.settings.displayZoomControls = false
        }
    }, update = {
    }, modifier = modifier)

    BackHandler(
        enabled = vm.backEnabled,
        onBack = vm::onBack
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun ExpandedView(vm: BrowserViewModel = getViewModel()) {
    val webView = vm.webView
    AnimatedVisibility(
        visible = !vm.expanded,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Surface(
            Modifier.fillMaxSize()
        ) {
            Column {
                CurrentTab(
                    title = webView.title.toString(),
                    url = webView.url.toString()
                )
                Divider()
                Row(
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
                ) {
                    UrlTabRow(
                        modifier = Modifier.weight(1f),
                        onTabClick = vm::onSwitchTab,
                        titles = vm.titles,
                        urls = vm.urlHistory.map { it.last() }
                    )
                    Spacer(Modifier.padding(4.dp))
                    TabPlusIcon(vm::onAddTab)
                }
            }
        }
        BackHandler(
            enabled = true,
            onBack = {
                vm.expanded = !vm.expanded
            }
        )
    }
}

/** Body sub components ----------------------------------------------- */
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
    }
}

@Composable
fun UrlTabRow(
    modifier: Modifier = Modifier,
    onTabClick: (Int) -> Unit,
    titles: List<String>,
    urls: List<String>
) {
    LazyRow(modifier) {
        itemsIndexed(urls) { index, urlHistory ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .clickable { onTabClick(index) }
            ) {
                Favicon(
                    url = Urls.GoogleFavicon(urlHistory).url,
                    Modifier
                        .size(50.dp)
                        .background(
                            shape = RoundedCornerShape(114.dp),
                            color = MaterialTheme.colors.primary.copy(alpha = 0.1f)
                        )
                        .padding(12.dp)
                )
                Text(
                    text = titles[index],
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.overline,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(60.dp)
                )
            }
        }
    }
}

@Composable
fun TabPlusIcon(onSearchTabClick: () -> Unit) {
    IconButton(onClick = onSearchTabClick) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .background(
                    shape = RoundedCornerShape(114.dp),
                    color = MaterialTheme.colors.primary.copy(alpha = 0.1f)
                )
                .padding(12.dp)
        )
    }
}
