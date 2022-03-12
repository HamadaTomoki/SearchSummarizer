package com.searchSummarizer.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BrowserHistory(
    @SerialName("selected_tab_index")
    var selectedTabIndex: Int = 0,
    var titles: String = "",
    var urls: String = ""
)
