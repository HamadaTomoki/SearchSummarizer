package com.searchSummarizer.di

import com.searchSummarizer.data.repo.browser.BrowserRepositoryImpl
import com.searchSummarizer.data.repo.context.ContextRepositoryImpl
import com.searchSummarizer.data.serializer.dataStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val searchSummarizerAppModule = module {
    factory { ContextRepositoryImpl(androidContext()) }
    factory { BrowserRepositoryImpl(androidContext().dataStore) }
}
