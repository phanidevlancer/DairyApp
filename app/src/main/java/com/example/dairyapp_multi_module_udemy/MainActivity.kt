package com.example.dairyapp_multi_module_udemy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.dairyapp_multi_module_udemy.navigation.Screen
import com.example.dairyapp_multi_module_udemy.navigation.SetupNavGraph
import com.example.dairyapp_multi_module_udemy.utils.Constants.MONGO_APP_ID
import io.realm.kotlin.mongodb.App

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            val navController = rememberNavController()
            SetupNavGraph(
                startDestination = getStartDestination(),
                navController = navController
            )
        }
    }


    fun getStartDestination(): String {
        val currentUser = App.create(MONGO_APP_ID).currentUser
        return if (currentUser != null && currentUser.loggedIn) Screen.Home.route else Screen.Authentication.route
    }
}