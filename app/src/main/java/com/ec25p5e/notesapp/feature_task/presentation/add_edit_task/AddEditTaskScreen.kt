package com.ec25p5e.notesapp.feature_task.presentation.add_edit_task

import android.widget.Toast
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.presentation.components.StandardTextFieldState
import com.ec25p5e.notesapp.core.presentation.components.StandardToolbar
import com.ec25p5e.notesapp.core.presentation.ui.theme.SpaceMedium
import com.ec25p5e.notesapp.core.presentation.ui.theme.SpaceSmall
import com.ec25p5e.notesapp.core.presentation.util.asString
import com.ec25p5e.notesapp.core.util.Constants
import com.ec25p5e.notesapp.feature_note.presentation.util.UiEventNote
import com.ec25p5e.notesapp.feature_task.domain.models.Task
import com.ec25p5e.notesapp.feature_task.presentation.components.CheckableItem
import com.ec25p5e.notesapp.feature_task.presentation.components.TaskDateTime
import com.ec25p5e.notesapp.feature_task.presentation.util.AddEditTaskError
import com.ec25p5e.notesapp.feature_task.presentation.util.UiEventTask
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/* Box(
    modifier = Modifier
        .fillMaxWidth()
        .height(100.dp),
    contentAlignment = Alignment.Center
){
    Button(
        onClick = {

        },
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(stringResource(id = R.string.add_checkable))
    }
}

Spacer(modifier = Modifier.height(SpaceMedium)) */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskScreen(
    imageLoader: ImageLoader,
    scaffoldState: SnackbarHostState,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    taskId: Int,
    viewModel: AddEditTaskViewModel = hiltViewModel()
) {
    val titleState = viewModel.titleState.value
    val contentState = viewModel.contentState.value
    val state = viewModel.state.value
    val scope = rememberCoroutineScope()
    val taskColor = viewModel.colorState.value
    val scaffoldAddingBottomSheet = rememberBottomSheetScaffoldState()
    val scaffoldColorBottomSheet = rememberBottomSheetScaffoldState()
    var addingBottomSheet by rememberSaveable { mutableStateOf(false) }
    var colorBottomSheet by rememberSaveable { mutableStateOf(false) }
    val noteBackgroundAnimatable = remember {
        Animatable(
            Color(if (taskColor != -1) taskColor else viewModel.colorState.value)
        )
    }
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is UiEventTask.ShowSnackbar -> {
                    Toast.makeText(
                        context,
                        event.uiText!!.asString(context),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(taskColor))
    ) {
        StandardToolbar(
            onNavigateUp = {
                onNavigateUp()
            },
            title = {
                Text(
                    text = titleState.text,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            modifier = Modifier
                .fillMaxWidth(),
            showBackArrow = true,
            navActions = {

            }
        )

        Column(
            modifier = Modifier
                .padding(SpaceMedium)
        ) {
            StandardTextFieldState(
                modifier = Modifier
                    .fillMaxWidth(),
                text = titleState.text,
                label = stringResource(id = R.string.label_task_title_text),
                maxLength = Constants.MAX_TASK_TITLE_LENGTH,
                onValueChange = {
                    viewModel.onEvent(AddEditTaskEvent.EnteredTitle(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditTaskEvent.ChangeTitleFocus(it))
                },
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text,
                error = when (titleState.error) {
                    is AddEditTaskError.FieldEmpty -> stringResource(id = R.string.field_empty_text_error)
                    is AddEditTaskError.InputTooShort -> stringResource(
                        id = R.string.field_too_short_text_error,
                        Constants.MIN_TASK_TITLE_LENGTH
                    )
                    else -> ""
                }
            )

            Spacer(modifier = Modifier.height(SpaceMedium))

            StandardTextFieldState(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f)
                    .height(400.dp),
                text = contentState.text,
                label = stringResource(id = R.string.label_task_decription_text),
                maxLength = Constants.MAX_TASK_DESCRIPTION_LENGTH,
                maxLines = 20,
                onValueChange = {
                    viewModel.onEvent(AddEditTaskEvent.EnteredContent(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditTaskEvent.ChangeContentFocus(it))
                },
                singleLine = false,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
            )

            Spacer(modifier = Modifier.height(SpaceMedium))

            TaskDateTime()

            Spacer(modifier = Modifier.height(SpaceMedium))

            if(viewModel.checkables.size > 0) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    items(
                        items = viewModel.checkables,
                        key = { it.uid }
                    ) {
                        CheckableItem(it)
                    }
                }
            }

        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .padding(SpaceSmall)
        ) {
            IconButton(
                onClick = {
                    scope.launch {
                        scaffoldAddingBottomSheet.bottomSheetState.expand()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_options),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            IconButton(
                onClick = {
                    scope.launch {
                        scaffoldColorBottomSheet.bottomSheetState.expand()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ColorLens,
                    contentDescription = stringResource(id = R.string.settings_general_color),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            Button(
                onClick = {
                    viewModel.onEvent(AddEditTaskEvent.SaveTask)
                },
                enabled = (!state.isSaving && !state.isAutoSaveEnabled),
            ) {
                Text(
                    text = stringResource(
                        id =
                        if (!state.isAutoSaveEnabled) {
                            R.string.save_btn_text
                        } else {
                            R.string.auto_save_btn_text
                        }
                    ),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.width(SpaceSmall))
                if (state.isSaving) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.CenterVertically)
                    )
                } else {
                    Icon(imageVector = Icons.Default.Save, contentDescription = null)
                }
            }
        }
    }


    /**
     * Add element bottom sheet navigation
     */
    BottomSheetScaffold(
        scaffoldState = scaffoldAddingBottomSheet,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            addingBottomSheet = true

            Box(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                if (scaffoldAddingBottomSheet.bottomSheetState.hasExpandedState) {
                    IconButton(
                        onClick = {
                            scope.launch {
                                scaffoldAddingBottomSheet.bottomSheetState.partialExpand()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {

                    }
                }

                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = {
                                    viewModel.onEvent(AddEditTaskEvent.AddCheckable())
                                }
                            ) {
                                Icon(
                                    painterResource(id = R.drawable.ic_list),
                                    contentDescription = stringResource(id = R.string.add_checklist),
                                    modifier = Modifier
                                        .size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = stringResource(id = R.string.add_checklist),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.surfaceTint
                                ),
                                modifier = Modifier
                                    .padding(16.dp),
                                textAlign = TextAlign.Start,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                }
            }
        }
    ) {}



    /**
     * Pick color
     */
    BottomSheetScaffold(
        scaffoldState = scaffoldColorBottomSheet,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            colorBottomSheet = true

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp),
                contentAlignment = Alignment.Center
            ) {
                if (scaffoldColorBottomSheet.bottomSheetState.hasExpandedState) {
                    IconButton(
                        onClick = {
                            scope.launch {
                                scaffoldColorBottomSheet.bottomSheetState.partialExpand()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {}
                }

                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(SpaceSmall),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            items(Task.taskColors) { taskColor ->
                                val colorInt = taskColor.toArgb()

                                Box(
                                    modifier = Modifier
                                        .size(25.dp)
                                        .shadow(7.5.dp, CircleShape)
                                        .clip(CircleShape)
                                        .background(taskColor)
                                        .border(
                                            width = 1.5.dp,
                                            color = if (viewModel.colorState.value == colorInt) {
                                                Color.Black
                                            } else Color.Transparent,
                                            shape = CircleShape
                                        )
                                        .clickable {
                                            scope.launch {
                                                noteBackgroundAnimatable.animateTo(
                                                    targetValue = Color(colorInt),
                                                    animationSpec = tween(
                                                        durationMillis = 500
                                                    )
                                                )
                                            }
                                            viewModel.onEvent(AddEditTaskEvent.ChangeColor(colorInt))
                                        }
                                )

                                Spacer(modifier = Modifier.width(8.dp))
                            }
                        }
                    }
                }
            }
        }
    ) {}
}