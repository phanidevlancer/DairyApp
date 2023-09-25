package com.example.dairyapp_multi_module_udemy.presentation.screens.auth

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AuthenticationScreen(loadingState: Boolean, onButtonClick: () -> Unit) {
    Scaffold(content = {
        AuthenticationContent(loadingState, onButtonClick)
    })
}