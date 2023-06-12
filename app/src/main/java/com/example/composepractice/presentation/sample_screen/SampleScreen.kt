package com.example.composepractice.presentation.sample_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composepractice.destinations.CreateWorkoutScreenDestination
import com.example.composepractice.ui.theme.spacing
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
@Composable
fun SampleScreen(
    navigator: DestinationsNavigator,
    viewModel: SampleViewModel = hiltViewModel()
) {
    val state = viewModel.screenState
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
        } else {
            state.sampleData?.let { sample ->
                Text(
                    text = "Loaded: ${sample.sampleField}"
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            OutlinedButton(onClick = {
                viewModel.dispatchEvent(SampleScreenEvent.Load)
            }) {
                Text(
                    text = "Load data",
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            OutlinedButton(onClick = {
                navigator.navigate(
//                    NavigatedScreenDestination(testArg = "Hi there"),
                    CreateWorkoutScreenDestination()
                )
            }) {
                Text(
                    text = "Navigate screen",
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        }
    }
}

@Destination
@Composable
fun NavigatedScreen(
    testArg: String = "Hello there",
    navigator: DestinationsNavigator,
    viewModel: SampleViewModel = hiltViewModel(),
) {
}