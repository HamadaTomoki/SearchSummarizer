package com.searchSummarizer.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.searchSummarizer.di.searchSummarizerAppModule
import com.searchSummarizer.di.viewModelModule
import com.searchSummarizer.ui.browse.BrowseScreen
import com.searchSummarizer.ui.theme.SearchSummarizerTheme
import dev.burnoo.cokoin.Koin
import dev.burnoo.cokoin.navigation.KoinNavHost

class SearchSummarizerActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Koin(appDeclaration = { modules(listOf(viewModelModule, searchSummarizerAppModule)) }) {
                SearchSummarizerTheme {
                    WindowCompat.setDecorFitsSystemWindows(window, false)
                    SearchSummarizerApp()
                }
            }
        }
    }
}

@Composable
fun SearchSummarizerApp() {
    val navController = rememberNavController()
    KoinNavHost(navController, startDestination = Screen.Browse.route) {
        addBrowseGraph()
    }
}

private fun NavGraphBuilder.addBrowseGraph() {
    composable(route = Screen.Browse.route) {
        BrowseScreen()
    }
}
