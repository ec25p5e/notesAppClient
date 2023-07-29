package com.ec25p5e.notesapp.core.util

sealed class Screen(val route: String) {
    object SplashScreen: Screen("splash_screen")
    object LoginScreen: Screen("login_screen")
    object RegisterScreen: Screen("register_screen")
    object DashboardScreen: Screen("dashboard_screen")
}