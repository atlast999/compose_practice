package com.example.composepractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val painter = painterResource(id = R.drawable.me)
            val description = "This is me for a while"
            val title = "This is me for a while"
            ImageCard(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxHeight(0.5f)
                    .fillMaxWidth(),
                painter = painter,
                title = title,
                contentDescription = description,
            )
        }
    }
}

@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    painter: Painter,
    title: String,
    contentDescription: String,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(5.dp),
    ) {
        Box {
            Image(
                modifier = Modifier
                    .fillMaxSize(),
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.FillBounds,
            )
            Box(
                modifier = Modifier
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black,
                            ),
                        )
                    )
                    .padding(20.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomStart,
            ) {
                Text(
                    text = buildAnnotatedString {
                        append(title)
                        withStyle(
                            style = SpanStyle(
                                color = Color.Cyan,
                                fontStyle = FontStyle.Normal,
                                fontFamily = FontFamily.Default,
                                textDecoration = TextDecoration.None,
                            )
                        ) {
                            append(" =))")
                        }
                    },
                    style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 40.sp,
                        fontStyle = FontStyle.Italic,
                        fontFamily = FontFamily.Cursive,
                        textDecoration = TextDecoration.Underline,
                    )
                )
            }
        }
    }
}