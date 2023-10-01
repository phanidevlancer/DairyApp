package com.example.dairyapp_multi_module_udemy.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.dairyapp_multi_module_udemy.presentation.screens.auth.AuthViewModel
import com.example.dairyapp_multi_module_udemy.presentation.screens.auth.AuthenticationScreen
import com.example.dairyapp_multi_module_udemy.presentation.screens.home.HomeScreen
import com.example.dairyapp_multi_module_udemy.utils.Constants.MONGO_APP_ID
import com.example.dairyapp_multi_module_udemy.utils.Constants.WRITE_SCREEN_ARGUMENT_KEY
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState
import io.realm.kotlin.mongodb.App
import kotlinx.coroutines.launch

@Composable
fun SetupNavGraph(startDestination: String, navController: NavHostController) {

    NavHost(
        startDestination = startDestination, navController = navController
    ) {
        authenticationRoute(navigateToHome = {
            navController.popBackStack()
            navController.navigate(Screen.Home.route)
        })
        homeRoute()
        writeRoute()
    }
}

fun NavGraphBuilder.authenticationRoute(navigateToHome: () -> Unit) {

    composable(Screen.Authentication.route) {
        val viewModel: AuthViewModel = viewModel()
        val loadingState by viewModel.loadingState
        val authenticatedState by viewModel.authenticated

        val oneTapState = rememberOneTapSignInState()
        val messageBarState = rememberMessageBarState()

        println("My loading state is : " + loadingState)
        AuthenticationScreen(
            authenticated = authenticatedState,
            loadingState = loadingState,
            oneTapState = oneTapState,
            messageBarState = messageBarState,
            onErrorReceived = {
                messageBarState.addError(Exception(it))
            },
            onTokenIdReceived = { tokenId ->
                viewModel.signInWithMongoAtlas(tokenId,
                    onSuccess = {
                        messageBarState.addSuccess("Login was successful")
                        viewModel.setLoading(false)
                    },
                    onFailure = { exception ->
                        messageBarState.addError(exception)
                        viewModel.setLoading(false)
                    })
            },
            onButtonClick = {
                viewModel.setLoading(true)
                oneTapState.open()
            },
            navigateToHome = navigateToHome
        )
    }
}

fun NavGraphBuilder.homeRoute() {
    composable(Screen.Home.route) {
        HomeScreen()
    }
}

fun NavGraphBuilder.writeRoute() {
    composable(
        Screen.Write.route,
        arguments = listOf(navArgument(name = WRITE_SCREEN_ARGUMENT_KEY) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {

    }
}