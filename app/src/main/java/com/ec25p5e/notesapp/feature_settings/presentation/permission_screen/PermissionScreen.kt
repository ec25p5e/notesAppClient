package com.ec25p5e.notesapp.feature_settings.presentation.permission_screen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.ec25p5e.notesapp.feature_settings.presentation.settings.SettingsViewModel

@Composable
fun PermissionScreen(
    scaffoldState: SnackbarHostState,
    imageLoader: ImageLoader,
    onNavigateUp: () -> Unit = {},
    vm: SettingsViewModel = hiltViewModel()
) {

}