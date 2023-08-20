package com.feature.webcam.presentation.components.core

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coil.ImageLoader
import com.feature.webcam.presentation.util.Screen
import com.feature.webcam.presentation.webcam_map.WebcamMapScreen

@Composable
fun Navigation(
    navController: NavHostController,
    scaffoldState: SnackbarHostState,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.WebcamMapScreen.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(Screen.WebcamMapScreen.route) {
            WebcamMapScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                scaffoldState = scaffoldState
            )
        }
    }
}