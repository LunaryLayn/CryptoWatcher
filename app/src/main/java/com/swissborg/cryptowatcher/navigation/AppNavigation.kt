package com.swissborg.cryptowatcher.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.swissborg.cryptowatcher.ui.features.mainScreen.MainScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreens.MainScreen.route) {
        composable(AppScreens.MainScreen.route) {
            MainScreen()
        }
    }
}
