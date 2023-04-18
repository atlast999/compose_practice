package com.example.composepractice

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composepractice.ui.features.Board
import com.example.composepractice.ui.features.Cell
import com.example.composepractice.ui.features.CellState
import com.example.composepractice.ui.features.login.LoginViewModel
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                var board by remember {
                    mutableStateOf(Board())
                }

                BoardComponent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(this.maxWidth)
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
    onPieceSelected: () -> Unit
) {
    Box(
        modifier = modifier
            .background(Color.Transparent)
            .clickable(onClick = onPieceSelected),
        contentAlignment = Alignment.Center,
    ) {
        when (cell.state) {
            CellState.WHITE_PAWN_OCCUPIED -> PawnPiece(
                color = Color.White,
                selected = cell.selected
            )
            CellState.WHITE_KING_OCCUPIED -> KingPiece(
                color = Color.White,
                selected = cell.selected
            )
            CellState.BLACK_PAWN_OCCUPIED -> PawnPiece(
                color = Color.Black,
                selected = cell.selected
            )
            CellState.BLACK_KING_OCCUPIED -> KingPiece(
                color = Color.Black,
                selected = cell.selected
            )
            else -> {}
        }
    }
}

@Composable
fun PawnPiece(
    modifier: Modifier = Modifier,
    color: Color,
    selected: Boolean,
) {
    val size = 20.dp + (if (selected) 10.dp else 0.dp)
    Canvas(
        modifier = modifier
            .shadow(
                elevation = if (selected) 5.dp else 0.dp,
                spotColor = Color.Red,
            )
            .size(size, size),
    ) {
        drawCircle(
            color = color,
        )
    }
}

@Composable
fun KingPiece(
    modifier: Modifier = Modifier,
    color: Color,
    selected: Boolean,
) {
    val size = 40.dp + (if (selected) 20.dp else 0.dp)
    Canvas(
        modifier = modifier
            .shadow(
                elevation = if (selected) 5.dp else 0.dp,
                spotColor = Color.Red,
            )
            .size(size, size),
    ) {
        drawCircle(
            color = color,
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalTextApi::class)
@Composable
fun GestureCheck() {
    var rect by remember {
        mutableStateOf(
            Rect(0f, 0f, 0f, 0f)
        )
    }
    var centerX by remember {
        mutableStateOf(0f)
    }
    var centerY by remember {
        mutableStateOf(0f)
    }
    var pointerX by remember {
        mutableStateOf(0f)
    }
    var pointerY by remember {
        mutableStateOf(0f)
    }
    var angle by remember {
        mutableStateOf(60f)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInteropFilter { event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                        pointerX = event.x
                        pointerY = event.y
                        angle =
                            atan2(centerY - pointerY, pointerX - centerX)
                                .div(PI)
                                .times(180)
                                .toFloat()
                        true
                    }
                    else -> false
                }
            },
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color.Green)
                .onGloballyPositioned {
                    rect = it.boundsInParent()
                    centerX = rect.center.x
                    centerY = rect.center.y
                }
                .rotate(angle)

        ) {

        }
        val textMeasurer = rememberTextMeasurer()
        Canvas(
            modifier = Modifier.fillMaxSize(),
        ) {
            rotate(-angle) {
                drawRect(
                    color = Color.Red,
                    topLeft = rect.topLeft,
                    size = rect.size.times(1 + angle / 360),
                    alpha = 0.75f,
                    style = Stroke(width = 3.dp.toPx()),
                )
            }
            drawCircle(
                color = Color.Black,
                radius = 5.dp.toPx(),
                center = Offset(centerX, centerY),
            )
            drawCircle(
                color = Color.Black,
                radius = 5.dp.toPx(),
                center = Offset(pointerX, pointerY),
            )
            drawLine(
                color = Color.Black,
                start = Offset(centerX, centerY),
                end = Offset(pointerX, pointerY),
            )
            drawText(
                textMeasurer = textMeasurer,
                text = angle.toString(),
                topLeft = Offset(pointerX + 10.dp.toPx(), pointerY + 10.dp.toPx()),
            )
        }
    }
}

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "constraint",
    ) {
        composable("constraint") {
            var selectedPhase by remember {
                mutableStateOf("")
            }
            ConstraintView(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxHeight(0.3f)
                    .fillMaxWidth(),
                username = selectedPhase,
                onUsernameChanged = {
                    selectedPhase = it
                },
                onConfirmed = {
                    if (selectedPhase.isEmpty()) return@ConstraintView
                    navController.navigate("drawing")
                }
            )
        }
        composable(
            route = "drawing?startPhase={startPhase}",
            arguments = listOf(
                navArgument("startPhase") {
                    defaultValue = "0"
                }
            ),
        ) { entry ->
            Column(
                modifier = Modifier
                    .fillMaxSize(),

                ) {
                var phase by remember {
                    mutableStateOf(
                        entry.arguments?.getString("startPhase")?.toIntOrNull() ?: -100
                    )
                }
                Drawing(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.8f)
                        .align(Alignment.CenterHorizontally),
                    phase = phase,
                )
                Slider(
                    value = phase.toFloat(),
                    valueRange = -100f..100f,
                    onValueChange = {
                        phase = it.toInt()
                    }
                )
            }
        }
    }
}

@Composable
fun Drawing(
    modifier: Modifier = Modifier,
    phase: Int,
) {
    val path = remember {
        Path()
    }
    Canvas(
        modifier = modifier.background(Color.Black)
    ) {
        path.reset()
        val radius = 500f
        drawCircle(
            color = Color.White,
            radius = radius,
        )

        val oWidth = radius.times(2).div(100).times(abs(phase))
        val oHeight = radius.times(2)
        val x = size.width.div(2) - oWidth.div(2)
        val y = size.height.div(2) - radius
        path.arcTo(
            rect = Rect(
                offset = Offset(
                    size.width.div(2) - radius, y
                ),
                size = Size(width = oHeight, height = oHeight)
            ),
            startAngleDegrees = 90f,
            sweepAngleDegrees = 180f,
            forceMoveTo = true,
        )
        val startAngle = if (phase <= 0) 90f else 270f
        path.arcTo(
            rect = Rect(
                offset = Offset(
                    x = x,
                    y = y,
                ),
                size = Size(oWidth, oHeight),
            ),
            startAngleDegrees = startAngle,
            sweepAngleDegrees = 180f,
            forceMoveTo = true
        )
        path.fillType = PathFillType.EvenOdd
        drawPath(
            path = path,
            color = Color.Black,
            alpha = 0.25f,
        )

    }
}

@Composable
fun ConstraintView(
    modifier: Modifier = Modifier,
    username: String,
    onUsernameChanged: (String) -> Unit,
    onConfirmed: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val (a, setA) = remember {
        mutableStateOf("")
    }
    var userName by viewModel.usernameState
    val constraints = ConstraintSet {
        val tfUsername = createRefFor("tfUsername")
        val btnConfirm = createRefFor("btnConfirm")
        constrain(tfUsername) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(btnConfirm.start, 10.dp)
            width = Dimension.fillToConstraints
            height = Dimension.wrapContent
        }
        constrain(btnConfirm) {
            top.linkTo(tfUsername.top)
            bottom.linkTo(tfUsername.bottom)
            end.linkTo(parent.end)
            width = Dimension.wrapContent
            height = Dimension.wrapContent
        }
    }
    ConstraintLayout(
        constraintSet = constraints,
        modifier = modifier,
    ) {
        OutlinedTextField(
            value = userName,
//            onValueChange = onUsernameChanged,
            onValueChange = { userName = it },
            modifier = Modifier.layoutId("tfUsername"),
            label = {
                Text(
                    fontSize = 12.sp,
                    text = "Enter a number between -100 and 100"
                )
            }
        )
        OutlinedButton(
            onClick = {
                Log.d("TAG", "ConstraintView: ${viewModel.usernameState.value}")
            },
            modifier = Modifier.layoutId("btnConfirm")
        ) {
            Text(text = "Confirm")
        }
    }
}