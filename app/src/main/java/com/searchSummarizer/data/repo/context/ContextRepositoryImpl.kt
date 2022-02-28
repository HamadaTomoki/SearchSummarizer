package com.searchSummarizer.data.repo.context

import android.content.Context

class ContextRepositoryImpl(
    private val context: Context
) : ContextRepository {
    override fun createContext(): Context = context
}
