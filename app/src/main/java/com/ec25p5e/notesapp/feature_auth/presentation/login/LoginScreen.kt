package com.ec25p5e.notesapp.feature_auth.presentation.login

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoginScreen(
    onNavigate: (String) -> Unit = {},
    onLogin: () -> Unit = {}
) {
    Text("Ciao")
}