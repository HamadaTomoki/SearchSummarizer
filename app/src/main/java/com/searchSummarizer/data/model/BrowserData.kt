package com.searchSummarizer.data.model

import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BrowserHistory(
    @SerialName("selected_tab_index")
    var selectedTabIndex: Int = 0,
    var titles: String = "",
    var urls: String = ""
)

@Serializable
data class SummarizedUrl(
    var id: String = "",
    var name: String = "",
    var author: String = "",
    var titles: List<String> = listOf(),
    var urls: List<String> = listOf()
)


data class BrowserOption(
    val name: String,
    val icon: ImageVector,
    val onOptionSelected: () -> Unit
)
