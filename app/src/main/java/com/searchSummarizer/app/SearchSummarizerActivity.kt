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
import com.searchSummarizer.data.enumType.Screen
import com.searchSummarizer.di.searchSummarizerAppModule
import com.searchSummarizer.di.viewModelModule
import com.searchSummarizer.ui.browse.BrowseScreen
import com.searchSummarizer.ui.theme.SearchSummarizerTheme
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin

class SearchSummarizerActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            androidContext(this@SearchSummarizerActivity)
            modules(listOf(viewModelModule, searchSummarizerAppModule))
        }
        val vm: SearchSummarizerViewModel by viewModel()
        lifecycle.addObserver(vm)
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
    NavHost(navController, startDestination = Screen.Browse.route) {
        addBrowseGraph()
    }
}

private fun NavGraphBuilder.addBrowseGraph() {
    composable(route = Screen.Browse.route) {
        BrowseScreen()
    }
}
