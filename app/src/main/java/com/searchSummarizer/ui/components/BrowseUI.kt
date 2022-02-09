package com.searchSummarizer.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.searchSummarizer.R

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BrowseHeader(
    extended: Boolean,
    onTabClick: () -> Unit,
    favIconUrls: List<String>,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawLine(
                    color = Color.LightGray,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 8f
                )
            }
            .padding(
                vertical = 8.dp,
                horizontal = 4.dp,
            )
            .height(36.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        AppIcon(extended)
        BrowseTab(
            modifier = Modifier.weight(1f),
            onTabClick = onTabClick,
            extended = extended,
            favIconUrls = favIconUrls
        )
        TabManagerIcon(extended)
        MoreOptionIcon()
    }
}

@ExperimentalAnimationApi
@Composable
private fun AppIcon(extended: Boolean) {
    AnimatedVisibility(visible = extended) {
        Image(
            painter = painterResource(id = R.drawable.ic_search_summarizer),
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight()
                .padding(4.dp)
        )
    }
}

@ExperimentalAnimationApi
@Composable
private fun TabManagerIcon(
    extended: Boolean,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    AnimatedVisibility(visible = extended) {
        Row {
            Spacer(Modifier.padding(4.dp))
            Icon(
                imageVector = Icons.Filled.Tab,
                contentDescription = null,
                tint = MaterialTheme.colors.onSurface,
                modifier = modifier
                    .fillMaxHeight()
                    .padding(horizontal = 4.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {}
            )
        }
    }
}

@Composable
private fun MoreOptionIcon() {
    val interactionSource = remember { MutableInteractionSource() }
    Icon(
        imageVector = Icons.Filled.MoreVert,
        contentDescription = null,
        tint = MaterialTheme.colors.onSurface,
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = 4.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {}
    )
}

@ExperimentalAnimationApi
@Composable
private fun BrowseTab(
    modifier: Modifier,
    onTabClick: () -> Unit,
    extended: Boolean,
    favIconUrls: List<String>,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Surface(
        shape = RoundedCornerShape(25.dp),
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onTabClick() }
    ) {
        Row(
            modifier = modifier.padding(
                horizontal = 12.dp,
                vertical = 8.dp
            )
        ) {
            AnimatedVisibility(visible = extended) {
                BrowseTabContent(favIconUrls, modifier.weight(1f))
            }
            AnimatedVisibility(visible = !extended) {
                BrowseTextField()
            }
        }
    }
}

@Composable
private fun BrowseTabContent(
    favIconUrls: List<String>,
    modifier: Modifier = Modifier,
) {
    LazyRow(modifier) {
        items(favIconUrls) { url ->
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
                modifier = modifier.size(32.dp)
            )
        }
    }
}



