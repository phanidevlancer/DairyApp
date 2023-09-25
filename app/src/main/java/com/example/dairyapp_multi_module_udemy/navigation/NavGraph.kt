package com.example.dairyapp_multi_module_udemy.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.dairyapp_multi_module_udemy.presentation.screens.auth.AuthenticationScreen
import com.example.dairyapp_multi_module_udemy.utils.Constants.WRITE_SCREEN_ARGUMENT_KEY

@Composable
fun SetupNavGraph(startDestination: String, navController: NavHostController) {

    NavHost(
        startDestination = startDestination, navController = navController
    ) {
        authenticationRoute()
        homeRoute()
        writeRoute()
    }
}

fun NavGraphBuilder.authenticationRoute() {
    composable(Screen.Authentication.route) {
        AuthenticationScreen(false, onButtonClick = {

        })
    }
}

fun NavGraphBuilder.homeRoute() {
    composable(Screen.Home.route, arguments = listOf(navArgument(name = WRITE_SCREEN_ARGUMENT_KEY) {
        type = NavType.StringType
        nullable = true
        defaultValue = null
    })) {

    }
}

fun NavGraphBuilder.writeRoute() {
    composable(Screen.Write.route) {

    }
}