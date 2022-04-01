package com.searchSummarizer.app.browser

import android.content.Intent
import android.webkit.URLUtil
import android.webkit.WebView
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.searchSummarizer.data.enumType.Urls
import com.searchSummarizer.data.model.BrowserHistory
import com.searchSummarizer.data.model.SummarizedUrl
import com.searchSummarizer.data.repo.browser.BrowserRepository
import com.searchSummarizer.data.repo.context.ContextRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * BrowserViewModel
 * browser機能の状態を保持するViewModel
 *
 * @property browserRepository browser機能を提供するrepository
 * @property contextRepository android contextを生成するrepository
 * @constructor BrowserViewModelに必要なrepositoryを受け取ります。
 */
class BrowserViewModel(
    private val browserRepository: BrowserRepository,
    private val contextRepository: ContextRepository
) : ViewModel() {

    /**
     * WebView
     */
    var webView: WebView by mutableStateOf(WebView(contextRepository.createContext()))

    /**
     * 現在、閲覧中のtab index
     */
    var tabIndex: Int by mutableStateOf(0)

    /**
     * 検索URL履歴
     */
    var urlHistory: MutableList<MutableList<String>> =
        mutableStateListOf(mutableStateListOf(Urls.Default.url))

    /**
     * tabのtitle
     */
    var titles: MutableList<String> = mutableStateListOf(defaultTitle)

    /**
     * browser画面の表示mode
     */
    var expanded: Boolean by mutableStateOf(true)

    /**
     * drop down menuの表示mode
     */
    var menuExpanded: Boolean by mutableStateOf(false)

    /**
     * 検索keyword
     */
    var keyword: String by mutableStateOf("")

    /**
     * 戻るactionの可否
     */
    var backEnabled: Boolean by mutableStateOf(false)

    /**
     * 全てのtabを閉じるmethod
     * 現在の開かれているtabを１つ検索tabを残して全て閉じます。
     */
    fun onDismissTabAll() {
        tabIndex = 0
        webView.loadUrl(Urls.Default.url)
        urlHistory = mutableStateListOf(mutableStateListOf(Urls.Default.url))
    }

    /**
     * option menuを閉じます。
     */
    fun onMenuDismissRequest() {
        menuExpanded = !menuExpanded
    }

    /**
     * 1つ前のpageに戻ります。
     */
    fun onBack() {
        urlHistory[tabIndex].removeLast()
        webView.loadUrl(urlHistory[tabIndex].last())
    }

    /**
     * web検索を行います。
     */
    fun onSearch() {
        expanded = !expanded
        val url = when {
            keyword == "" -> return // empty
            URLUtil.isValidUrl(keyword) -> keyword // valid url
            else -> Urls.GoogleSearch(keyword).url // keyword
        }
        webView.loadUrl(url)
    }

    /**
     * tabを切り替えます。
     *
     * @param tabIndex 現在、閲覧中のtab index
     */
    fun onSwitchTab(tabIndex: Int) {
        webView.loadUrl(urlHistory[tabIndex].last())
        this.tabIndex = tabIndex
        expanded = !expanded
    }

    /**
     * tabを追加します。
     */
    fun onAddTab() {
        urlHistory.add(mutableListOf(Urls.Default.url))
        tabIndex = urlHistory.size - 1
        titles.add(defaultTitle)
        webView.loadUrl(Urls.Default.url)
        expanded = !expanded
    }

    /**
     * browser画面の表示modeを切り替えます。
     */
    fun onTabClick() {
        if (expanded) {
            keyword = ""
            expanded = !expanded
        }
    }

    /**
     * browser履歴をrestoreします。
     */
    fun restoreBrowserHistory() {
        viewModelScope.launch {
            browserRepository.browserHistoryFlow.collect { browserHistory ->
                if (browserHistory.urls.isNotEmpty()) {
                    tabIndex = browserHistory.selectedTabIndex
                    titles = browserHistory.titles.replace(" ", "").split(",").toMutableList()
                    urlHistory = browserHistory.urls.replace(" ", "").split("^").map {
                        it.split(",").toMutableList()
                    }.toMutableList()
                    webView.loadUrl(urlHistory[tabIndex].last())
                } else {
                    webView.loadUrl(urlHistory[0].last())
                }
            }
        }
    }

    /**
     * browser履歴を保存します。
     */
    fun saveBrowserHistory() {
        viewModelScope.launch {
            browserRepository.saveBrowserHistory(
                BrowserHistory(
                    selectedTabIndex = tabIndex,
                    titles = titles.joinToString(),
                    urls = urlHistory.joinToString("^") // one dimensional array separator is "^"
                    { it.joinToString(",") } // two dimensional array separator is ","
                )
            )
        }
    }

    /**
     * 共有URLを検索します。
     *
     * @param intent
     * @return 1つにまとめられたURLの情報
     */
    fun findSummarizedUrl(intent: Intent): SummarizedUrl? = runBlocking {
        val id = intent.data?.getQueryParameter("id") ?: return@runBlocking null
        browserRepository.findSummarizedUrl(id)
    }

    /**
     * 共有URLに含まれる複数のURLを展開します。
     *
     * @param titles tabのtitle
     * @param urls 1つにまとめられたURL
     */
    fun expandSummarizedUrl(titles: List<String>, urls: List<String>) {
        val expandedUrl = mutableListOf<MutableList<String>>()
        urls.forEach { url ->
            expandedUrl.add(mutableListOf(url))
        }
        this.titles = titles.toMutableList()
        tabIndex = 0
        urlHistory = expandedUrl
    }
}

// web pageのdefault title
private const val defaultTitle = "Google"
