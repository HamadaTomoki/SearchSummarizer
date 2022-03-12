package com.searchSummarizer.data.enumType

sealed class Screen(val route: String) {
    object Browser : Screen("browse")
    object StartUp : Screen("start-up")
}
