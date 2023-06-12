package com.example.composepractice.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val line: Dp = 1.dp,
    val small: Dp = 2.dp,
    val small_x: Dp = 4.dp,
    val small_xx: Dp = 6.dp,
    val small_xxx: Dp = 8.dp,
    val medium: Dp = 12.dp,
    val medium_x: Dp = 16.dp,
    val medium_xx: Dp = 24.dp,
    val medium_xxx: Dp = 32.dp,
    val large: Dp = 40.dp,
    val large_x: Dp = 60.dp,
    val large_xx: Dp = 80.dp,
    val large_xxx: Dp = 100.dp,
)

val LocalSpacing = compositionLocalOf { Spacing() }

val MaterialTheme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current