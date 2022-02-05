package com.searchSummarizer.di

import com.searchSummarizer.SearchSummarizerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        SearchSummarizerViewModel()
    }
}
