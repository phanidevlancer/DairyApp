package com.example.dairyapp_multi_module_udemy.navigation

import com.example.dairyapp_multi_module_udemy.utils.Constants.WRITE_SCREEN_ARGUMENT_KEY

sealed class Screen(val route: String) {
    object Authentication : Screen("authentication_screen")
    object Home : Screen("home_screen")
    object Write : Screen("write_screen?$WRITE_SCREEN_ARGUMENT_KEY={$WRITE_SCREEN_ARGUMENT_KEY}") {
        fun passDiaryID(id: String) = "write_screen?$WRITE_SCREEN_ARGUMENT_KEY${id}"
    }

}
