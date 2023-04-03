package com.example.composepractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var color by remember {
                mutableStateOf(Color.Black)
            }
            Column {
                ColorBox(
                    modifier = Modifier
                        .fillMaxHeight(0.7f)
                        .fillMaxWidth(),
                    color = color,
                )
                Spacer(modifier = Modifier.height(50.dp))
                Box(
                    modifier = Modifier
                        .background(Color.Red)
                        .fillMaxSize()
                        .clickable {
                            color = Color(
                                Random.nextFloat(),
                                Random.nextFloat(),
                                Random.nextFloat(),
                                1f,
                            )
                        }
                )
            }

        }
    }
}

@Composable
fun ColorBox(
    modifier: Modifier = Modifier,
    color: Color,
) {
    Box(
        modifier = modifier
            .background(color = color),
    )
}