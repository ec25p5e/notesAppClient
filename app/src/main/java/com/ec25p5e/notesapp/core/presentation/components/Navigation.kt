package com.ec25p5e.notesapp.core.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import coil.ImageLoader
import com.ec25p5e.notesapp.core.util.Screen
import com.ec25p5e.notesapp.feature_auth.presentation.login.LoginScreen
import com.ec25p5e.notesapp.feature_auth.presentation.register.RegisterScreen
import com.ec25p5e.notesapp.feature_auth.presentation.splash.SplashScreen
import com.ec25p5e.notesapp.feature_chat.presentation.chat.ChatScreen
import com.ec25p5e.notesapp.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.ec25p5e.notesapp.feature_note.presentation.archive.ArchiveScreen
import com.ec25p5e.notesapp.feature_note.presentation.categories.CategoriesScreen
import com.ec25p5e.notesapp.feature_note.presentation.notes.NotesScreen
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
        startDestination = Screen.NotesScreen.route,
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

        composable(Screen.ChatScreen.route) {
            ChatScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                imageLoader = imageLoader
            )
        }

        composable(Screen.NotesScreen.route) {
            NotesScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                imageLoader = imageLoader,
                scaffoldState = scaffoldState
            )
        }

        composable(Screen.CategoryScreen.route) {
            CategoriesScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                imageLoader = imageLoader,
                scaffoldState = scaffoldState
            )
        }

        composable(Screen.ArchiveScreen.route) {
            ArchiveScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                imageLoader = imageLoader,
                scaffoldState = scaffoldState
            )
        }

        composable(
            route = Screen.CreateNoteScreen.route + "?noteId={noteId}&noteColor={noteColor}",
            arguments = listOf(
                navArgument(
                    name = "noteId"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument(
                    name = "noteColor"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                },
            ),
        ) {
            val color = it.arguments?.getInt("noteColor") ?: -1

            AddEditNoteScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                imageLoader = imageLoader,
                scaffoldState = scaffoldState,
                noteColor = color
            )
        }
    }
}