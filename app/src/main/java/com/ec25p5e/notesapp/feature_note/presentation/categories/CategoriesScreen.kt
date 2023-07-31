package com.ec25p5e.notesapp.feature_note.presentation.categories

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader


@Composable
fun CategoriesScreen(
    imageLoader: ImageLoader,
    scaffoldState: SnackbarHostState,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
}