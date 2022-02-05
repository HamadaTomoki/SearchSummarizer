package com.searchSummarizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import com.searchSummarizer.di.viewModelModule
import com.searchSummarizer.ui.browse.BrowseScreen
import com.searchSummarizer.ui.theme.SearchSummarizerTheme
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

class SearchSummarizerActivity : ComponentActivity() {

    val searchSummarizerViewModel: SearchSummarizerViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Koin
        GlobalContext.getOrNull() ?: startKoin {
            androidContext(this@SearchSummarizerActivity)
            modules(listOf(viewModelModule))
        }

        setContent {
            SearchSummarizerTheme {
                WindowCompat.setDecorFitsSystemWindows(window, false)
                SearchSummarizerApp(searchSummarizerViewModel)
            }
        }
    }
}

@Composable
fun SearchSummarizerApp(searchSummarizerViewModel: SearchSummarizerViewModel) {
    BrowseScreen(
        favIconUrls = searchSummarizerViewModel.favIconUrls
    )
}
