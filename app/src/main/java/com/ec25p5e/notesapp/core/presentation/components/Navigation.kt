package com.ec25p5e.notesapp.core.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coil.ImageLoader
import com.ec25p5e.notesapp.core.util.Screen
import com.ec25p5e.notesapp.feature_auth.presentation.login.LoginScreen
import com.ec25p5e.notesapp.feature_auth.presentation.register.RegisterScreen
import com.ec25p5e.notesapp.feature_auth.presentation.splash.SplashScreen
import com.ec25p5e.notesapp.feature_post.presentation.create_post.CreatePostScreen
import com.ec25p5e.notesapp.feature_post.presentation.main_feed.MainFeedScreen

@Composable
fun Navigation(
    navController: NavHostController,
    scaffoldState: SnackbarHostState,
    imageLoader: ImageLoader
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(
                onPopBackStack = navController::popBackStack,
                onNavigate = navController::navigate
            )
        }

        composable(Screen.LoginScreen.route) {
            LoginScreen(
                onNavigate = navController::navigate,
                onLogin = {
                    navController.popBackStack(
                        route = Screen.LoginScreen.route,
                        inclusive = true
                    )
                    navController.navigate(route = Screen.MainFeedScreen.route)
                },
                scaffoldState = scaffoldState
            )
        }

        composable(Screen.RegisterScreen.route) {
            RegisterScreen(
                navController = navController,
                scaffoldState = scaffoldState,
                onPopBackStack = navController::popBackStack
            )
        }

        composable(Screen.MainFeedScreen.route) {
            MainFeedScreen(
                scaffoldState = scaffoldState,
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                imageLoader = imageLoader
            )
        }

        composable(Screen.CreatePostScreen.route) {
            CreatePostScreen(
                scaffoldState = scaffoldState,
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                imageLoader = imageLoader
            )
        }
    }
}