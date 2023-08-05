package com.ec25p5e.notesapp.core.presentation.components

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import coil.ImageLoader
import com.ec25p5e.notesapp.feature_settings.domain.models.AppSettings
import com.ec25p5e.notesapp.core.util.Screen
import com.ec25p5e.notesapp.feature_auth.presentation.login.LoginScreen
import com.ec25p5e.notesapp.feature_auth.presentation.onboarding.OnBoardingScreen
import com.ec25p5e.notesapp.feature_auth.presentation.register.RegisterScreen
import com.ec25p5e.notesapp.feature_auth.presentation.splash.SplashScreen
import com.ec25p5e.notesapp.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.ec25p5e.notesapp.feature_note.presentation.archive.ArchiveScreen
import com.ec25p5e.notesapp.feature_note.presentation.notes.NotesScreen
import com.ec25p5e.notesapp.feature_profile.presentation.profile.ProfileScreen
import com.ec25p5e.notesapp.feature_settings.presentation.choose_theme.ChooseThemeScreen
import com.ec25p5e.notesapp.feature_settings.presentation.contact_me.ContactMeScreen
import com.ec25p5e.notesapp.feature_settings.presentation.import_data.ImportDataScreen
import com.ec25p5e.notesapp.feature_settings.presentation.info_app.InfoAppScreen
import com.ec25p5e.notesapp.feature_settings.presentation.permission_screen.PermissionScreen
import com.ec25p5e.notesapp.feature_settings.presentation.privacy_advice.PrivacyAdviceScreen
import com.ec25p5e.notesapp.feature_settings.presentation.settings.SettingsScreen
import com.ec25p5e.notesapp.feature_settings.presentation.unlock_method.UnlockMethodScreen

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
                    navController.navigate(route = Screen.NotesScreen.route)
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

        composable(Screen.NotesScreen.route) {
            NotesScreen(
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
            route = Screen.ProfileScreen.route/*,
            arguments = listOf(
                navArgument(name = "userId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        */) {
            ProfileScreen(
                userId = null /* it.arguments?.getString("userId") */,
                onLogout = {
                    navController.navigate(route = Screen.LoginScreen.route)
                },
                onNavigate = navController::navigate,
                imageLoader = imageLoader,
                scaffoldState = scaffoldState
            )
        }

        composable(Screen.SettingsScreen.route) {
            SettingsScreen(
                scaffoldState = scaffoldState,
                imageLoader = imageLoader,
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
            )
        }

        composable(
            route = Screen.CreateNoteScreen.route + "?noteId={noteId}",
            arguments = listOf(
                navArgument(
                    name = "noteId"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            ),
            deepLinks = listOf(
                navDeepLink {
                    action = Intent.ACTION_SEND
                    mimeType = "text/*"
                },
                navDeepLink {
                    action = Intent.ACTION_SEND
                    mimeType = "image/*"
                }
            )
        ) {
            val noteId = it.arguments?.getInt("noteId") ?: -1

            AddEditNoteScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                imageLoader = imageLoader,
                scaffoldState = scaffoldState,
                noteId = noteId
            )
        }

        composable(Screen.SelectThemeScreen.route) {
            ChooseThemeScreen(
                scaffoldState = scaffoldState,
                imageLoader = imageLoader,
                onNavigateUp = navController::navigateUp,
            )
        }

        composable(Screen.UnlockMethodScreen.route) {
            UnlockMethodScreen(
                scaffoldState = scaffoldState,
                imageLoader = imageLoader,
                onNavigateUp = navController::navigateUp,
            )
        }

        composable(Screen.ImportDataScreen.route) {
            ImportDataScreen(
                scaffoldState = scaffoldState,
                imageLoader = imageLoader,
                onNavigateUp = navController::navigateUp,
            )
        }

        composable(Screen.PrivacyAdviceScreen.route) {
            PrivacyAdviceScreen(
                scaffoldState = scaffoldState,
                imageLoader = imageLoader,
                onNavigateUp = navController::navigateUp,
            )
        }

        composable(Screen.InfoAppScreen.route) {
            InfoAppScreen(
                scaffoldState = scaffoldState,
                imageLoader = imageLoader,
                onNavigateUp = navController::navigateUp,
            )
        }

        composable(Screen.ContactMeScreen.route) {
            ContactMeScreen(
                scaffoldState = scaffoldState,
                imageLoader = imageLoader,
                onNavigateUp = navController::navigateUp,
            )
        }

        composable(Screen.OnBoardingScreen.route) {
            OnBoardingScreen(
                scaffoldState = scaffoldState,
                imageLoader = imageLoader,
                onNavigate = navController::navigate,
            )
        }

        composable(Screen.PermissionScreen.route) {
            PermissionScreen(
                scaffoldState = scaffoldState,
                imageLoader = imageLoader,
                onNavigateUp = navController::navigateUp,
            )
        }
    }
}