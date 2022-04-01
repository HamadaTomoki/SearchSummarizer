package com.searchSummarizer.data.repo.context

import android.content.Context

/**
 * ContextRepositoryの実装
 *
 * @property context
 * @constructor Create empty Context repository impl
 */
class ContextRepositoryImpl(
    private val context: Context
) : ContextRepository {
    override fun createContext(): Context = context
}
