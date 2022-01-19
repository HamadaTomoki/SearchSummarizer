package com.searchSummarizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.searchSummarizer.ui.startUp.StartUpScreen
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
    StartUpScreen()
}

