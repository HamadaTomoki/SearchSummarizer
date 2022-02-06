package com.searchSummarizer.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
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
                horizontal = 16.dp,
            )
            .height(40.dp)

    ) {
        AppIcon(extended, modifier)
        BrowseTab(
            modifier = modifier.weight(1f),
            onTabClick = onTabClick,
            extended = extended,
            favIconUrls = favIconUrls
        )
        MoreOption(extended, modifier)
    }
}

@ExperimentalAnimationApi
@Composable
private fun AppIcon(
    extended: Boolean,
    modifier: Modifier
) {
    AnimatedVisibility(visible = extended) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.ic_search_summarizer),
                contentDescription = null,
                modifier = modifier
                    .fillMaxHeight()
                    .padding(4.dp)
            )
            Spacer(modifier.padding(4.dp))
        }
    }
}

@ExperimentalAnimationApi
@Composable
private fun MoreOption(
    extended: Boolean,
    modifier: Modifier
) {
    AnimatedVisibility(visible = extended) {
        Row {
            Spacer(modifier.padding(4.dp))
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = null,
                tint = MaterialTheme.colors.onSurface,
                modifier = modifier.fillMaxHeight()
            )
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun BrowseTab(
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
                BrowseTabContent(favIconUrls)
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
    Row {
        LazyRow(modifier.weight(1f)) {
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
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(2.dp)
                .clip(RoundedCornerShape(114.dp))
                .background(MaterialTheme.colors.onSurface)
        )
        Spacer(modifier.padding(4.dp))
        Box(
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colors.onSurface,
                    shape = RoundedCornerShape(5.dp)
                )
                .padding(
                    top = 1.dp,
                    bottom = 2.dp,
                    start = 8.dp,
                    end = 8.dp
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = favIconUrls.size.toString(),
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}
