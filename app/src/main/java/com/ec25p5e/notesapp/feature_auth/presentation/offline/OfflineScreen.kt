package com.ec25p5e.notesapp.feature_auth.presentation.offline

import androidx.compose.runtime.Composable
import com.ec25p5e.notesapp.core.data.local.connectivity.ConnectivityObserver

@Composable
fun OfflineScreen(
    onPopBackStack: () -> Unit = {},
    onNavigate: (String) -> Unit = {},
    networkStatus: ConnectivityObserver.Status
) {


}