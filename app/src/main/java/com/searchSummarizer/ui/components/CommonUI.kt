package com.searchSummarizer.ui.components

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.searchSummarizer.R
import com.searchSummarizer.app.browser.BrowserViewModel
import com.searchSummarizer.data.model.BrowserHistory
import com.searchSummarizer.data.model.SummarizedUrl

@Composable
fun Favicon(
    url: String,
    modifier: Modifier = Modifier
) {
    Image(
        painter = rememberImagePainter(
            data = url,
            builder = {
                error(R.drawable.baseline_public)
                crossfade(true)
                placeholder(R.drawable.ic_launcher_foreground)
                transformations(CircleCropTransformation())
            }
        ),
        contentDescription = null,
        modifier = modifier
    )
}

@Composable
fun ConfirmAlertDialog(
    summarizedUrl: SummarizedUrl,
    expandSummarizedUrl: (List<String>, List<String>) -> Unit
) {

    var openDialog: Boolean by remember { mutableStateOf(true) }
    if (openDialog) {
        Dialog(
            onDismissRequest = { openDialog = false },
        ) {
            Surface(
                shape = RoundedCornerShape(25.dp),
                modifier = Modifier
            ) {
                Column {
                    Text("URLを受け取りました。")
                    Text("title: ${summarizedUrl.name}")
                    Text("author: ${summarizedUrl.author}")
                    Text("共有されたタブを即座に開くことができます。")
                    OutlinedButton(onClick = {
                        expandSummarizedUrl(summarizedUrl.titles, summarizedUrl.urls)
                        openDialog = false
                    }) {
                        Text("現在のタブで開く")
                    }
                    OutlinedButton(onClick = { openDialog = false }) {
                        Text("後で")
                    }
                }
            }
        }
    }
}

@Composable
fun InputText(
    text: String,
    onTextChange: (String) -> Unit,
    keyboardType: KeyboardType,
    label: String,
    icon: ImageVector,
    @StringRes iconContentDescription: Int,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = text,
        onValueChange = onTextChange,
        singleLine = true,
        label = {
            Text(text = label)
        },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = stringResource(id = iconContentDescription)
            )
        },

        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next,
            keyboardType = keyboardType
        ),
        keyboardActions = KeyboardActions(onNext = {
            focusManager.moveFocus(FocusDirection.Down)
        }),
        modifier = modifier
    )
}

@Composable
fun MultiLineInputText(
    text: String,
    onTextChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = text,
        onValueChange = onTextChange,
        label = {
            Text(text = label)
        },
        modifier = modifier,
    )
}
