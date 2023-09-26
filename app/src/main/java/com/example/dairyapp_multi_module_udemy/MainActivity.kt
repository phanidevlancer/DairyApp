package com.example.dairyapp_multi_module_udemy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.dairyapp_multi_module_udemy.navigation.Screen
import com.example.dairyapp_multi_module_udemy.navigation.SetupNavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            val navController = rememberNavController()
            SetupNavGraph(
                startDestination = Screen.Authentication.route,
                navController = navController
            )


        }
    }
}