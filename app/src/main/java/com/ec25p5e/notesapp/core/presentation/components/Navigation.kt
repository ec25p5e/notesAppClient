package com.ec25p5e.notesapp.core.presentation.components

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import com.ec25p5e.notesapp.core.util.Screen
import com.ec25p5e.notesapp.feature_auth.presentation.splash.SplashScreen

@Composable
fun Navigation(
    navController: NavHostController
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
    }
}