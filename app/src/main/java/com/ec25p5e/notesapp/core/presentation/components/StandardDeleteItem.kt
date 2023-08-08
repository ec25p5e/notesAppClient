package com.ec25p5e.notesapp.core.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.feature_note.presentation.notes.NotesEvent

@Composable
fun StandardDeleteItem(
    text: Int,
    title: Int,
    icon: Int,
    onDismissRequest: () -> Unit = {},
    confirmButtonClick: () -> Unit = {},
    dismissButton: () -> Unit = {},
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        icon = {
            Icon(painterResource(id = icon), contentDescription = null)
        },
        title = {
            Text(text = stringResource(id = title))
                },
        text = {
            Text(text = stringResource(id = text))
        },
        confirmButton = {
            TextButton(
                onClick = {
                    confirmButtonClick()
                }
            ) {
                Text(stringResource(id = R.string.confirm_btn_text))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    dismissButton()
                }
            ) {
                Text(stringResource(id = R.string.dismiss_btn_text))
            }
        },
        modifier = modifier
    )
}