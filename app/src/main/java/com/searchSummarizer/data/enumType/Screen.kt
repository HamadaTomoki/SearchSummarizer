package com.searchSummarizer.data.enumType

sealed class Screen(val route: String) {
    object Browse : Screen("browse")
}
