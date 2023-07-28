package com.ec25p5e.notesapp.core.util

sealed class Screen(val route: String) {
    object SplashScreen: Screen("splash_screen")
    object MainFeedScreen: Screen("main_feed_screen")
}