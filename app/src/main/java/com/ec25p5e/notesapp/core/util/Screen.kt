package com.ec25p5e.notesapp.core.util

sealed class Screen(val route: String) {
    object SplashScreen: Screen("splash_screen")
    object LoginScreen: Screen("login_screen")
    object RegisterScreen: Screen("register_screen")
    object MainFeedScreen: Screen("main_feed_screen")
    object SearchScreen: Screen("search_screen")
    object ProfileScreen: Screen("profile_screen")
    object PostDetailScreen: Screen("post_detail_screen")
}