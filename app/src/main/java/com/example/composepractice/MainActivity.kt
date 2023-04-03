package com.example.composepractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var username by remember {
                mutableStateOf("")
            }
            ConstraintView(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxSize(),
                username = username
            ) {
                username = it
            }
        }
    }
}

@Composable
fun ConstraintView(
    modifier: Modifier = Modifier,
    username: String,
    onUsernameChanged: (String) -> Unit,
) {
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
            value = username,
            onValueChange = onUsernameChanged,
            modifier = Modifier.layoutId("tfUsername")
        )
        OutlinedButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.layoutId("btnConfirm")
        ) {
            Text(text = "Confirm")
        }
    }
}