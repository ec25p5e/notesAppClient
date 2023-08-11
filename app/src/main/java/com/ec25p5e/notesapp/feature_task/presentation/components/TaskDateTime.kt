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

}