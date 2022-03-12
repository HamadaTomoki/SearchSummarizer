package com.searchSummarizer.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.searchSummarizer.app.browser.BrowserViewModel
import com.searchSummarizer.data.enumType.Screen
import com.searchSummarizer.di.searchSummarizerAppModule
import com.searchSummarizer.di.viewModelModule
import com.searchSummarizer.ui.browser.BrowseScreen
import com.searchSummarizer.ui.startUp.StartUpScreen
import com.searchSummarizer.ui.theme.SearchSummarizerTheme
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

class SearchSummarizerActivity : ComponentActivity() {

    private val browserViewModel: BrowserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalContext.getOrNull() ?: startKoin {
            androidContext(this@SearchSummarizerActivity)
            modules(listOf(viewModelModule, searchSummarizerAppModule))
        }
        lifecycle.addObserver(browserViewModel)
        browserViewModel.restoreBrowserHistory()
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
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screen.Browser.route) {
        addStartUpGraph(navController)
        addBrowserGraph()
    }
}

private fun NavGraphBuilder.addStartUpGraph(navController: NavController) {
    composable(route = Screen.StartUp.route) {
        StartUpScreen(navController)
    }
}

private fun NavGraphBuilder.addBrowserGraph() {
    composable(route = Screen.Browser.route) {
        BrowseScreen()
    }
}
