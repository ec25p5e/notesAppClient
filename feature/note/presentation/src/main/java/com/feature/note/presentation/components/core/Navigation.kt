package com.feature.note.presentation.components.core

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coil.ImageLoader
import com.feature.note.presentation.notes.NotesScreen
import com.feature.note.presentation.util.Screen

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Navigation(
    navController: NavHostController,
    scaffoldState: SnackbarHostState,
    imageLoader: ImageLoader,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.NotesScreen.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(Screen.NotesScreen.route) {
            NotesScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                imageLoader = imageLoader,
                scaffoldState = scaffoldState
            )
        }
    }
}