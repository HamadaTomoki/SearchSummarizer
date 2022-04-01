package com.searchSummarizer.data.model

import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * browser履歴
 *
 * @property selectedTabIndex
 * @property titles
 * @property urls
 * @constructor Create empty Browser history
 */
@Serializable
data class BrowserHistory(
    @SerialName("selected_tab_index")
    var selectedTabIndex: Int = 0,
    var titles: String = "",
    var urls: String = ""
)

/**
 * まとめれたURLの情報
 *
 * @property id urlのid
 * @property name url名
 * @property author 作成者
 * @property titles tabのtitle
 * @property urls tabのurl
 * @constructor SummarizedUrlを作成します。
 */
@Serializable
data class SummarizedUrl(
    var id: String = "",
    var name: String = "",
    var author: String = "",
    var titles: List<String> = listOf(),
    var urls: List<String> = listOf()
)

/**
 * browserのoption
 *
 * @property name option名
 * @property icon icon
 * @property onOptionSelected 選択されたときのevent
 * @constructor BrowserOptionを作成します。
 */
data class BrowserOption(
    val name: String,
    val icon: ImageVector,
    val onOptionSelected: () -> Unit
)
