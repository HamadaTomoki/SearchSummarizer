package com.searchSummarizer.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.searchSummarizer.R
import com.searchSummarizer.app.SearchSummarizerViewModel
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BrowseTextField(
    modifier: Modifier = Modifier,
    vm: SearchSummarizerViewModel = getViewModel()
) {

    val value = vm.keyword
    val onValueChange: (String) -> Unit = { vm.keyword = it }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_search_summarizer),
            contentDescription = null,
            modifier = modifier
                .background(
                    shape = RoundedCornerShape(50.dp),
                    color = MaterialTheme.colors.primary.copy(alpha = 0.3f)
                )
                .padding(4.dp)
        )
        Spacer(modifier.padding(4.dp))
        Box(
            contentAlignment = Alignment.CenterStart,
        ) {
            val keyboardController = LocalSoftwareKeyboardController.current
            val requester = FocusRequester()
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                keyboardOptions = KeyboardOptions.Default.copy(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Search,
                    keyboardType = KeyboardType.Uri
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        if (value.isNotEmpty()) vm.search()
                        vm.extended = !vm.extended
                        keyboardController?.hide()
                    }
                ),
                textStyle = TextStyle(
                    color = MaterialTheme.colors.onSurface,
                ),
                singleLine = true,
                cursorBrush = SolidColor(MaterialTheme.colors.onSurface),
                modifier = modifier
                    .focusRequester(requester)
                    .fillMaxWidth()
            )
            SideEffect {
                requester.requestFocus()
            }
            if (value.isEmpty()) {
                Text(
                    text = "検索語句またはウェブアドレスを入力",
                    color = Color.Gray,
                )
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
