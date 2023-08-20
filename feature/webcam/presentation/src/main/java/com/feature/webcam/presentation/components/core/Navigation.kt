package com.feature.webcam.presentation.components.core

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import com.feature.webcam.presentation.util.Screen
import com.feature.webcam.presentation.components.webcam_map.WebcamMapScreen

@ExperimentalComposeUiApi
@ExperimentalCoilApi
@ExperimentalMaterial3Api
@Composable
fun Navigation(
    navController: NavHostController,
    scaffoldState: SnackbarHostState,
    imageLoader: ImageLoader,
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
                scaffoldState = scaffoldState,
                imageLoader = imageLoader
            )
        }
    }
}