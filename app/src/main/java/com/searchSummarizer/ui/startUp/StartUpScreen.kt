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
            modifier = modifier.padding(top = 190.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.web_search),
                contentDescription = "web_search",
                modifier = modifier.background(
                    shape = RoundedCornerShape(50.dp),
                    color = MaterialTheme.colors.primary.copy(alpha = 0.3f)
                )
            )
            Spacer(modifier.padding(70.dp))
            Text(
                text = "再検索をスムーズに",
                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier.padding(12.dp))
            Text(
                text =
                """
                    Search Summarizerは
                    あなたの検索タブをまとめて
                    管理することで再検索を
                    スムーズにします
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
                moveTo(width.times(.3631f), height.times(.2148f))
                cubicTo(
                    width.times(.7343f), height.times(.1648f),
                    width.times(1.0672f), height.times(.4402f),
                    width.times(1.0672f), height.times(.7059f)
                )
                cubicTo(
                    width.times(1.0672f), height.times(1.0321f),
                    width.times(.8998f), height.times(1.0957f),
                    width.times(.5016f), height.times(1.0957f)
                )
                cubicTo(
                    width.times(.1177f), height.times(1.0746f),
                    width.times(.0566f), height.times(1.424f),
                    width.times(-.1562f), height.times(1.2901f)
                )
                cubicTo(
                    width.times(-.3766f), height.times(1.1194f),
                    width.times(-.074f), height.times(.2738f),
                    width.times(.3631f), height.times(.2148f)
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
