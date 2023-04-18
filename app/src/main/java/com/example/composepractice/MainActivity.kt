package com.example.composepractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composepractice.ui.features.Board
import com.example.composepractice.ui.features.Cell
import com.example.composepractice.ui.features.CellState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                var board by remember {
                    mutableStateOf(Board())
                }
                Text(
                    text = if (board.currentTurn == Board.WhiteTurn) "White turn"
                    else "Black turn",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                )
                BoardComponent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .background(Color(0xE6FDD458)),
                    board = board,
                ) { row, col ->
                    board = board.selectCell(row, col)
                }

            }
        }
    }
}

@Composable
fun BoardSkeleton(
    modifier: Modifier = Modifier,
) {
    Canvas(
        modifier = modifier,
    ) {
        val cellWidth = this.size.width.div(5)
        val topLeft = this.center - Offset(cellWidth * 2, cellWidth * 2)
        val borderSize = Size(4 * cellWidth, 4 * cellWidth)
        drawRect(
            color = Color.Black,
            topLeft = topLeft,
            size = borderSize,
            style = Stroke(
                width = 1.dp.toPx(),
            )
        )
        val topRight = topLeft + Offset(borderSize.width, 0f)
        val bottomLeft = topLeft + Offset(0f, borderSize.width)
        val bottomRight = topLeft + Offset(borderSize.width, borderSize.height)
        drawLine(
            color = Color.Black,
            start = topLeft,
            end = bottomRight,
            strokeWidth = 1.dp.toPx(),
        )
        drawLine(
            color = Color.Black,
            start = topRight,
            end = bottomLeft,
            strokeWidth = 1.dp.toPx(),
        )
        val topMid = topLeft + Offset(borderSize.width.div(2), 0f)
        val bottomMid = bottomLeft + Offset(borderSize.width.div(2), 0f)
        val leftMid = topLeft + Offset(0f, borderSize.width.div(2))
        val rightMid = topRight + Offset(0f, borderSize.width.div(2))
        drawLine(
            color = Color.Black,
            start = topMid,
            end = bottomMid,
            strokeWidth = 1.dp.toPx(),
        )
        drawLine(
            color = Color.Black,
            start = leftMid,
            end = rightMid,
            strokeWidth = 1.dp.toPx(),
        )

        drawLine(
            color = Color.Black,
            start = topMid,
            end = leftMid,
            strokeWidth = 1.dp.toPx(),
        )
        drawLine(
            color = Color.Black,
            start = leftMid,
            end = bottomMid,
            strokeWidth = 1.dp.toPx(),
        )
        drawLine(
            color = Color.Black,
            start = bottomMid,
            end = rightMid,
            strokeWidth = 1.dp.toPx(),
        )
        drawLine(
            color = Color.Black,
            start = rightMid,
            end = topMid,
            strokeWidth = 1.dp.toPx(),
        )

        drawLine(
            color = Color.Black,
            start = topMid - Offset(cellWidth, 0f),
            end = bottomMid - Offset(cellWidth, 0f),
            strokeWidth = 1.dp.toPx(),
        )
        drawLine(
            color = Color.Black,
            start = topMid + Offset(cellWidth, 0f),
            end = bottomMid + Offset(cellWidth, 0f),
            strokeWidth = 1.dp.toPx(),
        )

        drawLine(
            color = Color.Black,
            start = leftMid - Offset(0f, cellWidth),
            end = rightMid - Offset(0f, cellWidth),
            strokeWidth = 1.dp.toPx(),
        )
        drawLine(
            color = Color.Black,
            start = leftMid + Offset(0f, cellWidth),
            end = rightMid + Offset(0f, cellWidth),
            strokeWidth = 1.dp.toPx(),
        )
    }
}

@Composable
fun BoardComponent(
    modifier: Modifier = Modifier,
    board: Board,
    onCellSelected: (Int, Int) -> Unit,
) {
    Box(modifier = modifier) {
        BoardSkeleton(
            modifier = Modifier
                .fillMaxSize(),
        )
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            board.cells.forEachIndexed { row, cells ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(1f.div(5 - row))
                ) {
                    cells.forEachIndexed { col, cell ->
                        CellComponent(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(1f.div(5 - col)),
                            cell = cell,
                            isSelected = board.selectedCell?.id == cell.id,
                        ) {
                            onCellSelected.invoke(row, col)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CellComponent(
    modifier: Modifier = Modifier,
    cell: Cell,
    isSelected: Boolean,
    onPieceSelected: () -> Unit
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Box(
        modifier = modifier
            .background(Color.Transparent)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onPieceSelected,
            ),
        contentAlignment = Alignment.Center,
    ) {
        @Composable
        fun drawHint(state: CellState) {
            val hintModifier = Modifier.alpha(0.5f)
            when (state) {
                CellState.WHITE_PAWN_OCCUPIED -> PawnPiece(
                    modifier = hintModifier,
                    color = Color.White,
                )
                CellState.WHITE_KING_OCCUPIED -> KingPiece(
                    modifier = hintModifier,
                    color = Color.White,
                )
                CellState.BLACK_PAWN_OCCUPIED -> PawnPiece(
                    modifier = hintModifier,
                    color = Color.Black,
                )
                CellState.BLACK_KING_OCCUPIED -> KingPiece(
                    modifier = hintModifier,
                    color = Color.Black,
                )
                else -> {}
            }
        }
        when (cell.state) {
            CellState.WHITE_PAWN_OCCUPIED -> PawnPiece(
                color = Color.White,
                selected = isSelected,
            )
            CellState.WHITE_KING_OCCUPIED -> KingPiece(
                color = Color.White,
                selected = isSelected,
            )
            CellState.BLACK_PAWN_OCCUPIED -> PawnPiece(
                color = Color.Black,
                selected = isSelected,
            )
            CellState.BLACK_KING_OCCUPIED -> KingPiece(
                color = Color.Black,
                selected = isSelected,
            )
            else -> {
                drawHint(state = cell.hintState)
            }
        }
    }
}

@Composable
fun PawnPiece(
    modifier: Modifier = Modifier,
    color: Color,
    selected: Boolean = false,
) {
    val size by remember {
        mutableStateOf(20.dp)
    }
    val animatedSize by animateDpAsState(
        targetValue = size + (if (selected) 10.dp else 0.dp),
        animationSpec = tween(
            durationMillis = 500,
        )
    )
    Canvas(
        modifier = modifier
            .shadow(
                elevation = if (selected) 5.dp else 0.dp,
                spotColor = Color.Red,
            )
            .size(animatedSize, animatedSize),
    ) {
        20.dp.toPx()
        drawCircle(
            color = color,
        )
    }
}

@Composable
fun KingPiece(
    modifier: Modifier = Modifier,
    color: Color,
    selected: Boolean = false,
) {
    val size by remember {
        mutableStateOf(40.dp)
    }
    val animatedSize by animateDpAsState(
        targetValue = size + (if (selected) 20.dp else 0.dp),
        animationSpec = tween(
            durationMillis = 500,
        )
    )
    Canvas(
        modifier = modifier
            .shadow(
                elevation = if (selected) 5.dp else 0.dp,
                spotColor = Color.Red,
            )
            .size(animatedSize, animatedSize),
    ) {
        drawCircle(
            color = color,
        )
    }
}