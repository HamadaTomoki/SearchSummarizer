package com.searchSummarizer.ui.browser

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.searchSummarizer.app.browser.BrowserViewModel
import com.searchSummarizer.ui.components.BrowserBody
import com.searchSummarizer.ui.components.BrowserHeader
import com.searchSummarizer.ui.theme.SearchSummarizerTheme
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BrowseScreen(vm: BrowserViewModel = getViewModel()) {
    Surface(
        color = MaterialTheme.colors.primary.copy(alpha = 0.5f),
        modifier = Modifier.fillMaxSize()
    ) {
        Column(Modifier.padding(top = 48.dp)) {
            BrowserHeader()
            BrowserBody(vm.extended)
        }
    }
}

@Preview
@Composable
fun BrowseScreenPreview() {
    SearchSummarizerTheme {
        BrowseScreen()
    }
}
