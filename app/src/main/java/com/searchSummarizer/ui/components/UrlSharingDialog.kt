package com.searchSummarizer.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.searchSummarizer.app.browser.BrowserViewModel
import com.searchSummarizer.data.model.SummarizedUrl
import com.searchSummarizer.ui.theme.PreviewTheme

@Composable
fun UrlSharingDialog(
    summarizedUrl: SummarizedUrl,
    vm: BrowserViewModel
) {

    var openDialog: Boolean by remember { mutableStateOf(true) }

    Log.i("hoge", summarizedUrl.toString())
    val close = { openDialog = false }
    val submit = {
        vm.expandSummarizedUrl(summarizedUrl.titles, summarizedUrl.urls)
        close()
        vm.expanded = false
    }
    if (openDialog) {
        Dialog(
            onDismissRequest = { openDialog = false },
            properties = DialogProperties(dismissOnClickOutside = false)
        ) {
            UrlSharingDialogContent(summarizedUrl, submit, close)
        }
    }
}

@Composable
private fun UrlSharingDialogContent(
    summarizedUrl: SummarizedUrl,
    submit: () -> Unit,
    close: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(25.dp)
    ) {
        Column(
            modifier = Modifier.padding(
                vertical = 16.dp,
                horizontal = 30.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Filled.Done,
                contentDescription = null,
                tint = MaterialTheme.colors.primary,
                modifier = Modifier
                    .then(Modifier.size(80.dp))
                    .background(
                        color = MaterialTheme.colors.primary.copy(alpha = 0.1F),
                        shape = CircleShape
                    )
                    .padding(16.dp)
            )
            Text(
                text = "URLを受け取りました!",
                style = MaterialTheme.typography.h6
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp)
            ) {
                Text("タイトル:", fontWeight = FontWeight.Bold)
                Text(summarizedUrl.name)
                Spacer(Modifier.padding(2.dp))
                Text("作成者: ", fontWeight = FontWeight.Bold)
                Text(summarizedUrl.author)
            }
            Button(
                onClick = submit,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = "現在のタブで開く",
                    style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(4.dp)
                )
            }
            OutlinedButton(
                onClick = close,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = "後で",
                    style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun UrlSharingDialogPreview() {
    PreviewTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .padding(20.dp),
            contentAlignment = Alignment.Center,
        ) {
            UrlSharingDialogContent(
                SummarizedUrl(name = "Jetpack ComposeでDialogのPreviewを表示する", author = "はまちゃん"),
                {},
                {}
            )
        }
    }
}
