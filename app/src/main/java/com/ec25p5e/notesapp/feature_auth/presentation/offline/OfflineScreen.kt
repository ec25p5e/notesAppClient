package com.ec25p5e.notesapp.feature_auth.presentation.offline

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.ec25p5e.notesapp.core.data.local.connectivity.ConnectivityObserver
import com.ec25p5e.notesapp.core.util.Screen

@Composable
fun OfflineScreen(
    onPopBackStack: () -> Unit = {},
    onNavigate: (String) -> Unit = {},
    networkStatus: ConnectivityObserver.Status
) {

}