package com.searchSummarizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import com.searchSummarizer.ui.browse.BrowseScreen
import com.searchSummarizer.ui.theme.SearchSummarizerTheme

class SearchSummarizerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchSummarizerTheme {
                WindowCompat.setDecorFitsSystemWindows(window, false)
                SearchSummarizerApp()
            }
        }
    }
}

@Composable
fun SearchSummarizerApp() {
    BrowseScreen()
}
