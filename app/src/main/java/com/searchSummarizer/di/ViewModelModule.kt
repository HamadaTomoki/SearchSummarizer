package com.searchSummarizer.di

import com.searchSummarizer.app.SearchSummarizerViewModel
import com.searchSummarizer.data.repo.browser.BrowserRepositoryImpl
import com.searchSummarizer.data.repo.context.ContextRepositoryImpl
import org.koin.dsl.module

val viewModelModule = module {
    single {
        SearchSummarizerViewModel(
            browserRepository = get<BrowserRepositoryImpl>(),
            contextRepository = get<ContextRepositoryImpl>()
        )
    }
}
