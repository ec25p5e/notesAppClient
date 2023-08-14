package com.ec25p5e.notesapp.feature_chat.presentation.message

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi

@ExperimentalComposeUiApi
@ExperimentalCoilApi
@Composable
fun MessageScreen(
    remoteUserId: String,
    remoteUsername: String,
    encodedRemoteUserProfilePictureUrl: String,
    imageLoader: ImageLoader,
    onNavigateUp: () -> Unit = {},
    onNavigate: (String) -> Unit = {},
) {

}