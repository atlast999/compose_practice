package com.example.composepractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composepractice.ui.theme.BlueButton
import com.example.composepractice.ui.theme.DisableText
import com.example.composepractice.ui.theme.spacing
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

class SecondActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
        }
    }


}

@Destination
@Composable
fun CreateWorkoutScreen(
    navigator: DestinationsNavigator,
    tabs: List<String> = listOf("Workout builder", "Set a goal"),
) {
    TopAppBarScreen(
        title = "Create workout",
        navigationAction = {
            navigator.navigateUp()
        }
    ) {
        var selectedTabIndex by remember {
            mutableStateOf(0)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.spacing.medium),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TabRow(
                modifier = Modifier.fillMaxWidth(0.25f),
                selectedTabIndex = selectedTabIndex,
                containerColor = MaterialTheme.colorScheme.background,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        height = 2.dp,
                        color = BlueButton,
                    )
                },
                divider = {},
            ) {
                tabs.forEachIndexed { index, tabTitle ->
                    Tab(
                        selected = index == selectedTabIndex,
                        onClick = {
                            selectedTabIndex = index
                        }
                    ) {
                        Text(
                            text = tabTitle,
                            color = if (index == selectedTabIndex) {
                                MaterialTheme.colorScheme.onPrimary
                            } else {
                                DisableText
                            },
                            fontSize = 17.sp,
                        )
                        Spacer(
                            modifier = Modifier.height(MaterialTheme.spacing.medium_xx)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ManualWorkoutScreen() {
    TopAppBarScreen(
        title = "Manual Workout",
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
            )
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .fillMaxHeight(0.4f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            listOf("Create your workout", "Quick start").forEach {
                ManualOption(title = it)
            }
        }
    }
}

@Composable
fun ManualOption(
    modifier: Modifier = Modifier,
    title: String,
) {
    Column(
        modifier = modifier.padding(MaterialTheme.spacing.medium_xx)
    ) {
        Text(
            modifier = Modifier.padding(
                start = MaterialTheme.spacing.medium, bottom = MaterialTheme.spacing.medium
            ),
            text = title,
            fontSize = 28.sp,
            color = MaterialTheme.colorScheme.onPrimary,
        )
        Card(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1.5f, true),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondary,
            ),
            shape = RoundedCornerShape(MaterialTheme.spacing.medium_xx)
        ) {

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarScreen(
    title: String,
    navigationAction: () -> Unit = {},
    navigationIcon: @Composable () -> Unit = {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
        )
    },
    content: @Composable () -> Unit,
) {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        fontSize = 28.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = navigationAction
                    ) {
                        navigationIcon.invoke()
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                ),

                )
        }
    ) { paddingValue ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue),
            contentAlignment = Alignment.Center,
        ) {
            content()
        }
    }
}

@Composable
fun ChartView(
    components: List<ChartComponent>
) {

}

data class ChartComponent(
    val yAxis: YaxisPosition = YaxisPosition.NONE,
    val points: List<Pair<Float, Float>>,
    val color: Color,
) {
    enum class YaxisPosition {
        LEFT, RIGHT, NONE,
    }
}