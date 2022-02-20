package com.searchSummarizer.ui.browse

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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.searchSummarizer.app.SearchSummarizerViewModel
import com.searchSummarizer.di.viewModelModule
import com.searchSummarizer.ui.components.BrowseBody
import com.searchSummarizer.ui.components.BrowseHeader
import com.searchSummarizer.ui.theme.SearchSummarizerTheme
import dev.burnoo.cokoin.Koin

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BrowseScreen(vm: SearchSummarizerViewModel = viewModel()) {
    Surface(
        color = MaterialTheme.colors.primary.copy(alpha = 0.5f),
        modifier = Modifier.fillMaxSize()
    ) {
        Column(Modifier.padding(top = 48.dp)) {
            BrowseHeader()
            BrowseBody(vm.extended)
        }
    }
}

@Preview
@Composable
fun BrowseScreenPreview() {
    Koin(appDeclaration = { modules(viewModelModule) }) {
        SearchSummarizerTheme {
            BrowseScreen()
        }
    }
}
