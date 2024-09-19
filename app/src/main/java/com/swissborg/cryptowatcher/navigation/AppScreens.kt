package com.swissborg.cryptowatcher.navigation

sealed class AppScreens(val route: String) {
    object MainScreen : AppScreens("main_screen")
}
