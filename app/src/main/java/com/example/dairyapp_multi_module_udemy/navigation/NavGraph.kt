package com.example.dairyapp_multi_module_udemy.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.dairyapp_multi_module_udemy.presentation.components.DisplayAlertDialog
import com.example.dairyapp_multi_module_udemy.presentation.screens.auth.AuthViewModel
import com.example.dairyapp_multi_module_udemy.presentation.screens.auth.AuthenticationScreen
import com.example.dairyapp_multi_module_udemy.presentation.screens.home.HomeScreen
import com.example.dairyapp_multi_module_udemy.utils.Constants.MONGO_APP_ID
import com.example.dairyapp_multi_module_udemy.utils.Constants.WRITE_SCREEN_ARGUMENT_KEY
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState
import io.realm.kotlin.mongodb.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SetupNavGraph(startDestination: String, navController: NavHostController) {

    NavHost(
        startDestination = startDestination, navController = navController
    ) {
        authenticationRoute(navigateToHome = {
            navController.popBackStack()
            navController.navigate(Screen.Home.route)
        })
        homeRoute(navigateToWrite = {
            navController.navigate(Screen.Write.route)
        }, navigateToAuth = {
            navController.popBackStack()
            navController.navigate(Screen.Authentication.route)
        })
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
                viewModel.setLoading(false)
            },
            onTokenIdReceived = { tokenId ->
                viewModel.signInWithMongoAtlas(tokenId, onSuccess = {
                    messageBarState.addSuccess("Login was successful")
                    viewModel.setLoading(false)
                }, onFailure = { exception ->
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

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.homeRoute(navigateToWrite: () -> Unit, navigateToAuth: () -> Unit) {
    composable(Screen.Home.route) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var showSignOut by remember { mutableStateOf(false) }
        HomeScreen(drawerState = drawerState, onSignOutClick = {
            scope.launch {
                drawerState.close()
                showSignOut = true
            }
        }, onMenuClicked = {
            scope.launch {
                drawerState.open()
            }
        }, navigateToWrite = navigateToWrite
        )

        DisplayAlertDialog(title = "Sign Out",
            message = "Are you sure, you want to logout?",
            dialogOpened = showSignOut,
            onDialogClosed = { showSignOut = false },
            onYesClicked = {
                scope.launch(Dispatchers.IO) {
                    val user = App.Companion.create(MONGO_APP_ID).currentUser
                    user?.let {
                        it.logOut()
                        withContext(Dispatchers.Main) {
                            navigateToAuth()
                        }
                    }
                }
            })
    }
}

fun NavGraphBuilder.writeRoute() {
    composable(
        Screen.Write.route, arguments = listOf(navArgument(name = WRITE_SCREEN_ARGUMENT_KEY) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {

    }
}