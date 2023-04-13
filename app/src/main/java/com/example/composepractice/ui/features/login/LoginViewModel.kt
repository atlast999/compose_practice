package com.example.composepractice.ui.features.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {
    val usernameState = mutableStateOf("")
}