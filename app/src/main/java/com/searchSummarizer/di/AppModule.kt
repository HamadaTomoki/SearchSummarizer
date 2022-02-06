package com.searchSummarizer.di

import org.koin.dsl.module

val searchSummarizerAppModule = module {
}

data class BrowseTextFieldData(
    val value: String,
    val onValueChange: (String) -> Unit,
    val onSearch: () -> Unit
)
