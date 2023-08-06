package com.ec25p5e.notesapp.feature_task.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.feature_task.presentation.add_edit_task.AddEditTaskEvent
import com.ec25p5e.notesapp.feature_task.presentation.add_edit_task.AddEditTaskViewModel
import com.ec25p5e.notesapp.feature_task.presentation.components.datetime.composematerialdialogs.MaterialDialog
import com.ec25p5e.notesapp.feature_task.presentation.components.datetime.composematerialdialogs.rememberMaterialDialogState
import com.ec25p5e.notesapp.feature_task.presentation.components.datetime.date.datepicker
import com.ec25p5e.notesapp.feature_task.presentation.components.datetime.time.timepicker

@Composable
fun TaskDateTime(
    viewModel: AddEditTaskViewModel = hiltViewModel()
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ){
                IconButton(onClick = {
                    viewModel.onEvent(AddEditTaskEvent.DateClick)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_calendar),
                        contentDescription = "Date",
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Text(
                    text = if(viewModel.date.value.isEmpty()) stringResource(id = R.string.set_date) else viewModel.date.value,
                    modifier = Modifier
                        .clickable {
                            viewModel.onEvent(AddEditTaskEvent.DateClick)
                        }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = if(viewModel.time.value.isEmpty()) stringResource(id = R.string.set_time) else viewModel.time.value,
                    modifier = Modifier
                        .clickable {
                            viewModel.onEvent(AddEditTaskEvent.TimeClick)
                        }
                )
                IconButton(onClick = {
                    viewModel.onEvent(AddEditTaskEvent.TimeClick)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_calendar),
                        contentDescription = "Date",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
    val dateDialogState = rememberMaterialDialogState()
    LaunchedEffect(key1 = viewModel.dateDialogOpen.value){
        if(viewModel.dateDialogOpen.value&&!dateDialogState.showing){
            dateDialogState.show()
        }
        else{
            if(dateDialogState.showing){
                dateDialogState.hide()
            }
        }
    }

    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(stringResource(id = R.string.ok)){
                viewModel.onEvent(AddEditTaskEvent.DateDialogOk)
            }
            negativeButton(stringResource(id = R.string.cancel)){
                viewModel.onEvent(AddEditTaskEvent.DateDialogCancel)
            }
            negativeButton(stringResource(id = R.string.clear)){
                viewModel.onEvent(AddEditTaskEvent.DateDialogClear)
            }
        },
        onCloseRequest = {
            viewModel.onEvent(AddEditTaskEvent.DateDialogCancel)
        }
    ) {
        datepicker { date ->
            viewModel.onEvent(AddEditTaskEvent.DateDialogDate(date))
        }
    }

    val timeDialogState = rememberMaterialDialogState()
    LaunchedEffect(key1 = viewModel.timeDialogOpen.value){
        if(viewModel.timeDialogOpen.value&&!timeDialogState.showing){
            timeDialogState.show()
        } else{
            if(timeDialogState.showing){
                timeDialogState.hide()
            }
        }
    }

    MaterialDialog(
        dialogState = timeDialogState,
        buttons = {
            positiveButton(stringResource(id = R.string.ok)){
                viewModel.onEvent(AddEditTaskEvent.TimeDialogOk)
            }
            negativeButton(stringResource(id = R.string.cancel)){
                viewModel.onEvent(AddEditTaskEvent.TimeDialogCancel)
            }
            negativeButton(stringResource(id = R.string.clear)){
                viewModel.onEvent(AddEditTaskEvent.TimeDialogClear)
            }
        },
        onCloseRequest = {
            viewModel.onEvent(AddEditTaskEvent.TimeDialogCancel)
        }
    ) {
        timepicker { time ->
            viewModel.onEvent(AddEditTaskEvent.TimeDialogDate(time))
        }
    }
}