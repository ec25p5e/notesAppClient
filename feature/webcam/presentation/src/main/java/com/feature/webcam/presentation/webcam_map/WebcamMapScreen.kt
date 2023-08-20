package com.feature.webcam.presentation.webcam_map

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import coil.ImageLoader

@Composable
fun WebcamMapScreen(
    scaffoldState: SnackbarHostState,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
) {
    Text("Webcam Map Screen: Hello World!")
}