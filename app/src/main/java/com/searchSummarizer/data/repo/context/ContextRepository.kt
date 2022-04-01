package com.searchSummarizer.data.repo.context

import android.content.Context

/**
 * Context repository
 *
 * @constructor Create empty Context repository
 */
interface ContextRepository {
    /**
     * android contextを作成します。
     *
     * @return android context
     */
    fun createContext(): Context
}
