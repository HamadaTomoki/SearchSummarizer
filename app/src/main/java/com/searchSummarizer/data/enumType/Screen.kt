package com.searchSummarizer.data.enumType

/**
 * navigation route
 *
 * @property route navigation route
 * @constructor Screenを作成します。
 */
sealed class Screen(val route: String) {
    /**
     * Browser画面のnavigation route
     *
     * @constructor Browserを作成します。
     */
    object Browser : Screen("browse")

    /**
     * StartUp画面のnavigation route
     *
     * @constructor StartUpを作製します。
     */
    object StartUp : Screen("start-up")
}
