package com.searchSummarizer.app

sealed class Screen(val route: String) {
    object Browse : Screen("browse")
}
