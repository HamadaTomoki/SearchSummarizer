package com.searchSummarizer.ui.startUp

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.searchSummarizer.R
import com.searchSummarizer.ui.theme.SearchSummarizerTheme

@Composable
fun StartUpScreen(
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = modifier.fillMaxSize()
    ) {
        StartUpCanvas(
            color = MaterialTheme.colors.onPrimary.copy(alpha = 0.5f)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.padding(top = 140.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.web_search),
                contentDescription = "web_search",
                modifier = modifier.background(
                    shape = RoundedCornerShape(50.dp),
                    color = MaterialTheme.colors.primary.copy(alpha = 0.3f)
                )
            )
            Spacer(modifier.padding(80.dp))
            Text(
                text = "再検索をスムーズに",
                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier.padding(4.dp))
            Text(
                text =
                """
                    Search Summarizerは
                    あなたの検索タブをまとめて
                    管理することで再検索を
                    スムーズに行います
                """.trimIndent(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier.padding(16.dp))
            OutlinedButton(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(25.dp),
                modifier = Modifier
                    .height(48.dp)
                    .width(200.dp)
            ) {
                Text(
                    text = "はじめる",
                    style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

@Preview
@Composable
fun StartUpScreenPreview() {
    SearchSummarizerTheme {
        StartUpScreen()
    }
}

@Composable
fun StartUpCanvas(
    color: Color
) {
    Column {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(384.dp)
        ) {
            val width = size.width
            val height = size.height
            val path = Path().apply {
                moveTo(width.times(.3644f), height.times(.0848f))
                cubicTo(
                    width.times(.7356f), height.times(.0348f),
                    width.times(1.0685f), height.times(.3102f),
                    width.times(1.0685f), height.times(.5759f)
                )
                cubicTo(
                    width.times(1.0685f), height.times(.9021f),
                    width.times(.9011f), height.times(.9657f),
                    width.times(.5029f), height.times(.9657f)
                )
                cubicTo(
                    width.times(.119f), height.times(.9446f),
                    width.times(.0579f), height.times(1.294f),
                    width.times(-.1549f), height.times(1.1601f)
                )
                cubicTo(
                    width.times(-.3753f), height.times(.9894f),
                    width.times(-.0727f), height.times(.1438f),
                    width.times(.3644f), height.times(.0848f)
                )
                close()
            }
            drawPath(
                path = path,
                color = color
            )
        }
    }
}
