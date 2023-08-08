package com.ec25p5e.notesapp.feature_note.presentation.add_edit_note

import android.Manifest
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.presentation.components.StandardLoadingAlert
import com.ec25p5e.notesapp.core.presentation.components.StandardOptionsMenu
import com.ec25p5e.notesapp.core.presentation.components.StandardTextFieldState
import com.ec25p5e.notesapp.core.presentation.components.StandardToolbar
import com.ec25p5e.notesapp.core.presentation.ui.theme.SpaceMedium
import com.ec25p5e.notesapp.core.presentation.ui.theme.SpaceSmall
import com.ec25p5e.notesapp.core.presentation.util.asString
import com.ec25p5e.notesapp.core.util.Constants
import com.ec25p5e.notesapp.core.util.Constants.MIN_NOTE_TITLE_LENGTH
import com.ec25p5e.notesapp.feature_note.domain.models.Background
import com.ec25p5e.notesapp.feature_note.domain.models.Note
import com.ec25p5e.notesapp.feature_note.presentation.categories.CategoryViewModel
import com.ec25p5e.notesapp.feature_note.presentation.notes.NotesEvent
import com.ec25p5e.notesapp.feature_note.presentation.notes.NotesViewModel
import com.ec25p5e.notesapp.feature_note.presentation.util.AddEditNoteError
import com.ec25p5e.notesapp.feature_note.presentation.util.UiEventNote
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import coil.compose.rememberAsyncImagePainter
import com.ec25p5e.notesapp.feature_note.presentation.archive.ArchiveEvent
import com.ec25p5e.notesapp.feature_note.presentation.archive.ArchiveViewModel
import com.ec25p5e.notesapp.feature_note.presentation.components.CategoryItem
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState


@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun AddEditNoteScreen(
    imageLoader: ImageLoader,
    scaffoldState: SnackbarHostState,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    noteId: Int,
    viewModel: AddEditNoteViewModel = hiltViewModel(),
    viewModelCategory: CategoryViewModel = hiltViewModel(),
    viewModelNotes: NotesViewModel = hiltViewModel(),
    viewModelArchive: ArchiveViewModel = hiltViewModel()
) {
    val scaffoldAddingBottomSheet = rememberBottomSheetScaffoldState()
    val scaffoldColorBottomSheet = rememberBottomSheetScaffoldState()
    var addingBottomSheet by rememberSaveable { mutableStateOf(false) }
    var colorBottomSheet by rememberSaveable { mutableStateOf(false) }
    val titleState = viewModel.titleState.value
    val stateCategories = viewModelCategory.state.value
    val contentState = viewModel.contentState.value
    val pinState = viewModel.pinState.value
    val isLockedNote = viewModel.isLockedNote.value
    val unlockState = viewModel.unlockPinState.value
    val backgroundImage = viewModel.noteBackground.value
    val noteColor = viewModel.colorState.value
    val state = viewModel.state.value
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val noteBackgroundAnimatable = remember {
        Animatable(
            Color(if (noteColor != -1) noteColor else viewModel.colorState.value)
        )
    }
    val isArchived = viewModel.isArchivedState.value
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val categoryBackgroundAnimatable = remember {
        Animatable(
            Color(viewModelCategory.categoryColor.value)
        )
    }
    val imageUri = viewModel.chosenImageUri.value
    val selectedImage = remember { mutableStateListOf<Uri?>(null) }
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) {
        selectedImage.apply {
            clear()
            addAll(it)
            viewModel.onEvent(AddEditNoteEvent.PickImage(it.toList()))
        }
    }
    val permissionState = rememberPermissionState(permission = Manifest.permission.READ_EXTERNAL_STORAGE)

    SideEffect {
        permissionState.launchPermissionRequest()
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is UiEventNote.ShowSnackbar -> {
                    Toast.makeText(
                        context,
                        event.uiText!!.asString(context),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is UiEventNote.SaveNote -> {
                    onNavigateUp()
                }
                is UiEventNote.ShowLoader -> {
                    viewModel.onEvent(AddEditNoteEvent.IsSaveNote)
                }
                else -> {
                }
            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModelNotes.eventFlow.collectLatest { event ->
            when(event) {
                is UiEventNote.ShowSnackbar -> {
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

    LaunchedEffect(key1 = true) {
        viewModelArchive.eventFlow.collectLatest { event ->
            when(event) {
                is UiEventNote.ShowSnackbar -> {
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

    if(state.isSaving) {
        StandardLoadingAlert(
            modifier = Modifier.fillMaxWidth(),
            icon = {
                Icon(painterResource(id = R.drawable.ic_save), contentDescription = null) },
            confirmButton = {
            },
            onDismissRequest = {
            },
            title = {
            }
        )
    }

    if (state.isCategoryModalOpen) {
        AlertDialog(
            onDismissRequest = {
                viewModel.onEvent(AddEditNoteEvent.ToggleCategoryModal)
            },
            icon = {
                Icon(painterResource(id = R.drawable.ic_category), contentDescription = null) },
            title = {
                Text(text = stringResource(id = R.string.choose_category))
            },
            text = {
                Column(
                    Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth(),
                        ) {
                            items(
                                items = stateCategories.categories,
                                key = { category -> category.id!! },
                                itemContent = { category ->
                                    CategoryItem(
                                        category = category,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .border(
                                                width = 1.5.dp,
                                                color = if (category.id == viewModel.noteCategory.value) {
                                                    Color.Black
                                                } else Color.Transparent,
                                                shape = RoundedCornerShape(10.dp)
                                            ),
                                        clickable = {
                                            scope.launch {
                                                categoryBackgroundAnimatable.animateTo(
                                                    targetValue = Color(category.color),
                                                    animationSpec = tween(
                                                        durationMillis = 500
                                                    )
                                                )
                                            }

                                            category.id?.let {
                                                AddEditNoteEvent.ChangeCategoryColor(it)
                                            }?.let { viewModel.onEvent(it) }
                                        }
                                    )
                                }
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.onEvent(AddEditNoteEvent.ToggleCategoryModal)
                    }
                ) {
                    Text(stringResource(id = R.string.apply_btn_text))
                }
            },
            modifier = Modifier
                .fillMaxWidth()
        )
    }

    val baseColumnModifier: Modifier = if(backgroundImage != R.drawable.ic_no_image_note) {
        Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = backgroundImage),
                contentScale = ContentScale.FillBounds
            )
    } else {
        Modifier
            .fillMaxSize()
            .background(Color(noteColor))
    }

    if(isLockedNote && !unlockState.isNoteUnlocked) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            StandardToolbar(
                onNavigateUp = {
                    onNavigateUp()
                },
                title = {
                    Text(
                        text = stringResource(id = R.string.protected_note_text),
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


            Row {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.protected_content_image),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth(1.1f)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                StandardTextFieldState(
                    modifier = Modifier
                        .fillMaxWidth(0.5f),
                    text = pinState.text,
                    label = stringResource(id = R.string.label_note_pin),
                    maxLength = Constants.MAX_PIN_LENGTH,
                    onValueChange = {
                        viewModel.onEvent(AddEditNoteEvent.EnteredPin(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(AddEditNoteEvent.ChangePinFocus(it))
                    },
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number,
                    error = when (titleState.error) {
                        is AddEditNoteError.FieldEmpty -> stringResource(id = R.string.field_empty_text_error)
                        is AddEditNoteError.InputTooShort -> stringResource(
                            id = R.string.field_too_short_text_error,
                            MIN_NOTE_TITLE_LENGTH
                        )
                        else -> ""
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        viewModel.onEvent(AddEditNoteEvent.UnlockNote)
                    },
                ) {
                    Text(
                        text = stringResource(id = R.string.unlock_note_text),
                        color = MaterialTheme.colorScheme.onPrimary
                    )

                    Spacer(modifier = Modifier.width(SpaceSmall))
                    Icon(imageVector = Icons.Default.LockOpen, contentDescription = null)
                }
            }

            if(viewModel.unlockPinState.value.isPinError) {
                AlertDialog(
                    onDismissRequest = {
                        viewModel.onEvent(AddEditNoteEvent.TogglePinError)
                    },
                    icon = {
                        Icon(painterResource(id = R.drawable.ic_error), contentDescription = null) },
                    title = {
                        Text(text = stringResource(id = R.string.pin_error))
                    },
                    text = {

                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                viewModel.onEvent(AddEditNoteEvent.TogglePinError)
                            }
                        ) {
                            Text(stringResource(id = R.string.retry_insert_pin))
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    } else if(unlockState.isNoteUnlocked || !isLockedNote) {
        Column(
            modifier = baseColumnModifier
        ) {
            StandardToolbar(
                onNavigateUp = {
                    if (state.isAutoSaveEnabled) {
                        viewModel.onEvent(AddEditNoteEvent.SaveNote(context.filesDir))
                    }

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
                    .fillMaxWidth()
                    .background(Color(noteColor)),
                showBackArrow = true,
                navActions = {
                    StandardOptionsMenu(
                        menuItem = {
                            DropdownMenuItem(
                                text = {
                                    Text(stringResource(id = R.string.convert_in_audio_text))
                                },
                                onClick = {
                                    viewModel.onEvent(
                                        AddEditNoteEvent.ConvertInAudio(
                                            noteId,
                                            context
                                        )
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        painterResource(id = R.drawable.ic_audio),
                                        contentDescription = stringResource(id = R.string.convert_in_audio_text)
                                    )
                                }
                            )

                            DropdownMenuItem(
                                text = {
                                    Text(stringResource(id = R.string.read_note_text))
                                },
                                onClick = {
                                    viewModel.onEvent(AddEditNoteEvent.ReadNote(context))
                                },
                                leadingIcon = {
                                    Icon(
                                        painterResource(id = R.drawable.ic_speak),
                                        contentDescription = stringResource(id = R.string.convert_in_audio_text)
                                    )
                                }
                            )

                            if (isArchived && noteId != -1) {
                                DropdownMenuItem(
                                    text = {
                                        Text(stringResource(id = R.string.dearchive_note))
                                    },
                                    onClick = {
                                        viewModel.onEvent(AddEditNoteEvent.ToggleArchived)
                                        viewModelArchive.onEvent(ArchiveEvent.DeArchiveNote(noteId))
                                    },
                                    leadingIcon = {
                                        Icon(
                                            painterResource(id = R.drawable.ic_unarchive),
                                            contentDescription = stringResource(id = R.string.dearchive_note)
                                        )
                                    }
                                )
                            } else if(!isArchived && noteId != -1) {
                                DropdownMenuItem(
                                    text = {
                                        Text(stringResource(id = R.string.archive_note))
                                    },
                                    onClick = {
                                        viewModel.onEvent(AddEditNoteEvent.ToggleArchived)
                                        viewModelNotes.onEvent(NotesEvent.ArchiveNote(noteId))
                                    },
                                    leadingIcon = {
                                        Icon(
                                            painterResource(id = R.drawable.ic_baseline_archive_24),
                                            contentDescription = stringResource(id = R.string.archive_note)
                                        )
                                    }
                                )
                            }


                            DropdownMenuItem(
                                text = {
                                    Text(stringResource(id = R.string.create_note_copy))
                                },
                                onClick = {
                                    viewModelNotes.onEvent(NotesEvent.CopyNote(viewModel.currentNoteId!!))
                                },
                                leadingIcon = {
                                    Icon(
                                        painterResource(id = R.drawable.ic_copy),
                                        contentDescription = stringResource(id = R.string.create_note_copy)
                                    )
                                }
                            )

                            DropdownMenuItem(
                                text = {
                                    Text(stringResource(id = R.string.categorize_note))
                                },
                                onClick = {
                                    viewModel.onEvent(AddEditNoteEvent.ToggleCategoryModal)
                                },
                                leadingIcon = {
                                    Icon(
                                        painterResource(id = R.drawable.ic_category),
                                        contentDescription = stringResource(id = R.string.categorize_note)
                                    )
                                }
                            )

                            if (isLockedNote) {
                                DropdownMenuItem(
                                    text = {
                                        Text(stringResource(id = R.string.unlock_note_text))
                                    },
                                    onClick = {
                                        viewModelNotes.onEvent(NotesEvent.UnLockNote(noteId))
                                        viewModel.onEvent(AddEditNoteEvent.ToggleLockMode)
                                    },
                                    leadingIcon = {
                                        Icon(
                                            painterResource(id = R.drawable.ic_lock_open),
                                            contentDescription = stringResource(id = R.string.unlock_note_text)
                                        )
                                    }
                                )
                            } else {
                                DropdownMenuItem(
                                    text = {
                                        Text(stringResource(id = R.string.lock_note_text))
                                    },
                                    onClick = {
                                        viewModelNotes.onEvent(NotesEvent.LockNote(noteId))
                                        viewModel.onEvent(AddEditNoteEvent.ToggleLockMode)
                                    },
                                    leadingIcon = {
                                        Icon(
                                            painterResource(id = R.drawable.ic_lock_close),
                                            contentDescription = stringResource(id = R.string.lock_note_text)
                                        )
                                    }
                                )
                            }

                            if (state.isSharing) {
                                DropdownMenuItem(
                                    text = {
                                        Text(stringResource(id = R.string.share_note_text))
                                    },
                                    onClick = {
                                        TODO("Not yet implemented")
                                    },
                                    leadingIcon = {
                                        Icon(
                                            painterResource(id = R.drawable.ic_sharing),
                                            contentDescription = stringResource(id = R.string.share_note_text)
                                        )
                                    }
                                )
                            }
                        }
                    )
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
                    label = stringResource(id = R.string.label_note_title_input),
                    maxLength = Constants.MAX_NOTE_TITLE_LENGTH,
                    onValueChange = {
                        viewModel.onEvent(AddEditNoteEvent.EnteredTitle(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(AddEditNoteEvent.ChangeTitleFocus(it))
                    },
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text,
                    error = when (titleState.error) {
                        is AddEditNoteError.FieldEmpty -> stringResource(id = R.string.field_empty_text_error)
                        is AddEditNoteError.InputTooShort -> stringResource(
                            id = R.string.field_too_short_text_error,
                            MIN_NOTE_TITLE_LENGTH
                        )

                        else -> ""
                    }
                )

                Spacer(modifier = Modifier.height(SpaceMedium))

                StandardTextFieldState(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)
                        .height(400.dp),
                    text = contentState.text,
                    label = stringResource(id = R.string.label_note_description_input),
                    maxLength = Constants.MAX_NOTE_DESCRIPTION_LENGTH,
                    maxLines = 20,
                    onValueChange = {
                        viewModel.onEvent(AddEditNoteEvent.EnteredContent(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(AddEditNoteEvent.ChangeContentFocus(it))
                    },
                    singleLine = false,
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text,
                    error = when (contentState.error) {
                        is AddEditNoteError.FieldEmpty -> stringResource(id = R.string.field_empty_text_error)
                        is AddEditNoteError.InputTooShort -> stringResource(
                            id = R.string.field_too_short_text_error,
                            MIN_NOTE_TITLE_LENGTH
                        )

                        else -> ""
                    }
                )

                Spacer(modifier = Modifier.height(SpaceMedium))

                if (imageUri.isNotEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.6f)
                        ) {
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.Center)
                            ) {
                                itemsIndexed(imageUri) { index, uri ->
                                    ImagePreviewItem(uri = uri!!,
                                        height = screenHeight * 0.5f,
                                        width = screenWidth * 0.6f,
                                        onClick = {
                                            viewModel.onEvent(AddEditNoteEvent.DeleteImage(uri))
                                        }
                                    )
                                    Spacer(modifier = Modifier.width(5.dp))
                                }
                            }
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
                        viewModel.onEvent(AddEditNoteEvent.SaveNote(context.filesDir))
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
                                .align(CenterVertically)
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

                                    }
                                ) {
                                    Icon(
                                        painterResource(id = R.drawable.ic_take_photo),
                                        contentDescription = stringResource(id = R.string.take_photo),
                                        modifier = Modifier
                                            .size(24.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = stringResource(id = R.string.take_photo),
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

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(
                                    onClick = {
                                        galleryLauncher.launch("image/jpeg")
                                    }
                                ) {
                                    Icon(
                                        painterResource(id = R.drawable.ic_image),
                                        contentDescription = stringResource(id = R.string.add_image),
                                        modifier = Modifier
                                            .size(24.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = stringResource(id = R.string.add_image),
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

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(
                                    onClick = {
                                        galleryLauncher.launch("image/jpeg")
                                    }
                                ) {
                                    Icon(
                                        painterResource(id = R.drawable.ic_draw),
                                        contentDescription = stringResource(id = R.string.drawing),
                                        modifier = Modifier
                                            .size(24.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = stringResource(id = R.string.drawing),
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

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(
                                    onClick = {

                                    }
                                ) {
                                    Icon(
                                        painterResource(id = R.drawable.ic_rec),
                                        contentDescription = stringResource(id = R.string.start_rec),
                                        modifier = Modifier
                                            .size(24.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = stringResource(id = R.string.start_rec),
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
         * Pick color and image section
         */
        BottomSheetScaffold(
            scaffoldState = scaffoldColorBottomSheet,
            sheetPeekHeight = 0.dp,
            sheetContent = {
                colorBottomSheet = true

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(175.dp),
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
                                items(Note.noteColors) { noteColor ->
                                    val colorInt = noteColor.toArgb()

                                    Box(
                                        modifier = Modifier
                                            .size(50.dp)
                                            .shadow(15.dp, CircleShape)
                                            .clip(CircleShape)
                                            .background(noteColor)
                                            .border(
                                                width = 3.dp,
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
                                                viewModel.onEvent(
                                                    AddEditNoteEvent.ChangeColor(
                                                        colorInt
                                                    )
                                                )
                                            }
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row {
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                items(Background.background) { bg ->
                                    Box(
                                        modifier = Modifier
                                            .size(50.dp)
                                            .shadow(15.dp, CircleShape)
                                            .clip(CircleShape)
                                            .border(
                                                width = 1.5.dp,
                                                color = if (bg == backgroundImage) {
                                                    Color.Black
                                                } else Color.Transparent,
                                                shape = CircleShape
                                            )
                                            .paint(
                                                painterResource(id = bg),
                                                contentScale = ContentScale.FillBounds
                                            )
                                            .clickable {
                                                scope.launch {
                                                    viewModel.onEvent(
                                                        AddEditNoteEvent.ChangeBgImage(bg)
                                                    )
                                                }
                                            }
                                    )

                                    Spacer(modifier = Modifier.width(16.dp))
                                }
                            }
                        }
                    }
                }
            }
        ) {}
    }
}

@Composable
fun ImagePreviewItem(
    uri: Uri,
    height: Dp,
    width: Dp,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            painter = rememberAsyncImagePainter(uri),
            contentDescription = "",
            modifier = Modifier
                .width(width)
                .height(height),
            contentScale = ContentScale.Crop
        )

        IconButton(onClick = { onClick() }) {
            Icon(imageVector = Icons.Default.Delete,
                contentDescription = "",
                modifier = Modifier
                    .size(45.dp),
                tint = Color.Red
            )
        }
    }
}
