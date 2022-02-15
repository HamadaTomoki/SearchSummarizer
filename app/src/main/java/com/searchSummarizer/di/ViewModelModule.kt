package com.searchSummarizer.di

import com.searchSummarizer.app.SearchSummarizerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        SearchSummarizerViewModel(get())
    }
}
