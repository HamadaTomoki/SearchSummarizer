package com.searchSummarizer.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.AddToHomeScreen
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FileDownloadDone
import androidx.compose.material.icons.filled.GTranslate
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.searchSummarizer.app.browser.BrowserViewModel
import com.searchSummarizer.data.model.BrowserOption
import com.searchSummarizer.ui.theme.PreviewTheme

@Composable
fun BrowserOptionMenu(vm: BrowserViewModel) {

    val browserOptions = listOf(
        BrowserOption("新しいタブ", Icons.Filled.AddBox, {}),
        BrowserOption("全てのタブを閉じる", Icons.Filled.Close, vm::onDismissTabAll),
        BrowserOption("履歴", Icons.Filled.History, {}),
        BrowserOption("ダウンロード", Icons.Filled.FileDownloadDone, {}),
        BrowserOption("ブックマーク", Icons.Filled.Star, {}),
        BrowserOption("最近使ったタブ", Icons.Filled.GTranslate, {}),
        BrowserOption("共有", Icons.Filled.Share, {}),
        BrowserOption("ホーム画面に追加", Icons.Filled.AddToHomeScreen, {}),
        BrowserOption("設定", Icons.Filled.Settings, {}),
        BrowserOption("ヘルプとフィード・バック", Icons.Filled.Help, {}),
    )

    DropdownMenu(
        expanded = vm.menuExpanded,
        offset = DpOffset((-1).dp, 20.dp),
        onDismissRequest = vm::onMenuDismissRequest,
    ) {
        browserOptions.forEachIndexed { index, browserOption ->
            BrowserOptionMenuItem(
                optionName = browserOption.name,
                icon = browserOption.icon,
                onClick = {
                    browserOption.onOptionSelected()
                    vm.onMenuDismissRequest()
                }
            )
        }
    }
}

@Composable
private fun BrowserOptionMenuItem(
    icon: ImageVector,
    optionName: String,
    onClick: () -> Unit
) {
    DropdownMenuItem(onClick = onClick) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(icon, null)
            Spacer(Modifier.padding(8.dp))
            Text(
                text = optionName,
                style = MaterialTheme.typography.subtitle2,
                textAlign = TextAlign.Justify,
            )
        }
    }
}

@Preview
@Composable
fun BrowserOptionMenuPreview() {
    PreviewTheme {
        BrowserOptionMenuItem(Icons.Filled.Share, "共有", {})
    }
}

