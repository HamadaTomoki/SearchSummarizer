package com.searchSummarizer.di

import com.searchSummarizer.app.browser.BrowserViewModel
import com.searchSummarizer.data.repo.browser.BrowserRepositoryImpl
import com.searchSummarizer.data.repo.context.ContextRepositoryImpl
import org.koin.dsl.module

/**
 * ViewModelのdi module
 */
val viewModelModule = module {
    single {
        BrowserViewModel(
            browserRepository = get<BrowserRepositoryImpl>(),
            contextRepository = get<ContextRepositoryImpl>()
        )
    }
}
