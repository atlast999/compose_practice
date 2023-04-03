package com.example.composepractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var username by remember {
                mutableStateOf("")
            }
            val snackbarHostState = remember {
                SnackbarHostState()
            }
            var confirmedState by remember {
                mutableStateOf(false)
            }
            LoginForm(
                modifier = Modifier.fillMaxSize(),
                snackbarHostState = snackbarHostState,
                username = username,
                onUsernameChanged = {
                    username = it
                    confirmedState = false
                },
                onConfirmed = {
                    confirmedState = true
                },
                isConfirmClicked = confirmedState,
            )
        }
    }
}

@Composable
fun LoginForm(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    username: String,
    onUsernameChanged: (String) -> Unit,
    onConfirmed: () -> Unit,
    isConfirmClicked: Boolean
) {
    LaunchedEffect(isConfirmClicked, username) {
        if (isConfirmClicked && username.isNotEmpty()) {
            snackbarHostState.showSnackbar(
                "Hello $username"
            )
        }
    }
    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
                .fillMaxSize()
                .wrapContentSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End,
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = onUsernameChanged,
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Enter username")
                },
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedButton(onClick = onConfirmed) {
                Text(text = "Confirm")
            }
        }
    }
}