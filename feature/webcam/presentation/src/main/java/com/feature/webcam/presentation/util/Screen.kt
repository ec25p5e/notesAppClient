package com.feature.webcam.presentation.util

sealed class Screen(val route: String) {

    data object WebcamMapScreen: Screen("webcam_map_screen")
}