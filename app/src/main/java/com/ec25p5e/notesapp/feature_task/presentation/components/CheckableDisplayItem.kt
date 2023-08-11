package com.ec25p5e.notesapp.feature_task.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.presentation.ui.theme.PantoneRed
import com.ec25p5e.notesapp.core.presentation.ui.theme.checkable_checked_style
import com.ec25p5e.notesapp.core.presentation.ui.theme.checkable_unchecked_style
import com.ec25p5e.notesapp.feature_task.domain.models.Checkable
import com.ec25p5e.notesapp.feature_task.domain.models.Task
import com.ec25p5e.notesapp.feature_task.presentation.task.TaskEvent
import com.ec25p5e.notesapp.feature_task.presentation.task.TaskViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CheckableDisplayItem(
    task: Task,
    checkable: Checkable,
    viewModel: TaskViewModel = hiltViewModel()
) {
    var editing by remember { mutableStateOf(false) }
    var editingText by remember { mutableStateOf("") }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        Checkbox(
            checked = checkable.checked,
            onCheckedChange = { checked->
                viewModel.onEvent(TaskEvent.CheckableCheck(checkable, checked))
            }
        )

        if(!editing){
            if(checkable.checked){
                Text(
                    checkable.value,
                    style = checkable_checked_style,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            } else{
                Text(
                    checkable.value,
                    style = checkable_unchecked_style,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
        } else {
            val (focusRequester) = FocusRequester.createRefs()

            LaunchedEffect(
                key1 = editing){
                if(editing){
                    focusRequester.requestFocus()
                }
            }

            BasicTextField(
                value = editingText,
                onValueChange = {
                    editingText = it
                },
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .focusRequester(focusRequester),
                cursorBrush = SolidColor(PantoneRed)
            )
        }

        if(!editing){
            IconButton(
                onClick = {
                    editingText = checkable.value
                    editing = true
                },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(id = R.string.edit)
                )
            }
        } else{
            IconButton(
                onClick = {
                    editing = false
                },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.edit),
                    tint = Color.Red
                )
            }

            IconButton(
                onClick = {
                    viewModel.onEvent(TaskEvent.OnCheckableValueChange(task,checkable,editingText))
                    editing = false
                },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = stringResource(id = R.string.edit),
                    tint = Color.Green
                )
            }
        }
    }
}