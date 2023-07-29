package com.ec25p5e.notesapp.feature_nfc.presentation.dashboard

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun DashboardScreen(
    scaffoldState: SnackbarHostState,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {}
) {
    val context = LocalContext.current


    Text(text = "Dashboard")
}