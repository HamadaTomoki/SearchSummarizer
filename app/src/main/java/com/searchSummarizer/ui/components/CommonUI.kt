package com.searchSummarizer.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import coil.transform.CircleCropTransformation
import com.searchSummarizer.R
import com.searchSummarizer.app.browser.BrowserViewModel
import org.koin.androidx.compose.getViewModel

/**
 * Favicon
 *
 * @param url faviconの画像URL
 * @param modifier Modifier
 */
@Composable
fun Favicon(
    url: String,
    modifier: Modifier = Modifier
) {
    Image(
        painter = rememberImagePainter(
            data = url,
            builder = {
                // coil画像が0であると見なされるため、表示する必要がないため、読み込みが開始されない場合があります。
                size(OriginalSize)
                error(R.drawable.baseline_public)
                crossfade(true)
                placeholder(R.drawable.ic_launcher_foreground)
                transformations(CircleCropTransformation())
            }
        ),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = modifier.fillMaxHeight()
    )
}

/**
 * Search text field
 *
 * @param modifier Modifier
 * @param vm BrowserViewModel
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    vm: BrowserViewModel = getViewModel()
) {

    val value = vm.keyword
    val onValueChange: (String) -> Unit = { vm.keyword = it }

    Row {
        Image(
            painter = painterResource(id = R.drawable.ic_search_summarizer),
            contentDescription = null,
            modifier = modifier
                .background(
                    shape = CircleShape,
                    color = MaterialTheme.colors.primary.copy(alpha = 0.3f)
                )
                .padding(4.dp)
        )
        Spacer(modifier.padding(4.dp))
        Box(contentAlignment = Alignment.CenterStart) {
            val keyboardController = LocalSoftwareKeyboardController.current
            val requester = FocusRequester()
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search,
                    keyboardType = KeyboardType.Uri
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        vm.onSearch()
                        keyboardController?.hide()
                    }
                ),
                textStyle = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onSurface),
                singleLine = true,
                cursorBrush = SolidColor(MaterialTheme.colors.onSurface),
                modifier = modifier.focusRequester(requester)
            )
            SideEffect {
                requester.requestFocus()
            }
            if (value.isEmpty()) {
                Text(
                    text = "検索語句またはウェブアドレスを入力",
                    color = Color.Gray,
                    fontSize = MaterialTheme.typography.body1.fontSize
                )
            }
        }
    }
}
