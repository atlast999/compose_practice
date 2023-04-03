package com.example.composepractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.Red)
                        .fillMaxHeight(0.5f)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Text(
                        modifier = Modifier,
                        text = "Hello",
                        fontSize = 30.sp,
                        color = Color.White,
                    )
                    Text(
                        text = "World",
                        fontSize = 30.sp,
                        color = Color.White,
                    )
                }
                Row (
                    modifier = Modifier
                        .background(Color.Green)
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    Text(
                        modifier = Modifier,
                        text = "Hello",
                        fontSize = 30.sp,
                        color = Color.White,
                    )
                    Text(
                        text = "World",
                        fontSize = 30.sp,
                        color = Color.White,
                    )
                }
            }
        }
    }
}