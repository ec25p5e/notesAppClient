package com.ec25p5e.notesapp.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun StandardLoadingAlert(
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit),
    title: @Composable (() -> Unit),
    confirmButton: () -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            onDismissRequest()
        },
        icon = icon,
        title = title,
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LoadingAnimation()
            }
        },
        confirmButton = {
            confirmButton()
        },
        modifier = modifier
    )
}