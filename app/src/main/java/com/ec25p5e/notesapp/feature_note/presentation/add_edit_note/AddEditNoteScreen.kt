package com.ec25p5e.notesapp.feature_note.presentation.add_edit_note

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.Alignment.Companion.CenterVertically
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.presentation.components.StandardLoadingAlert
import com.ec25p5e.notesapp.core.presentation.components.StandardOptionsMenu
import com.ec25p5e.notesapp.core.presentation.components.StandardTextFieldState
import com.ec25p5e.notesapp.core.presentation.components.StandardToolbar
import com.ec25p5e.notesapp.core.presentation.ui.theme.SpaceLarge
import com.ec25p5e.notesapp.core.presentation.ui.theme.SpaceMedium
import com.ec25p5e.notesapp.core.presentation.ui.theme.SpaceSmall
import com.ec25p5e.notesapp.core.presentation.util.asString
import com.ec25p5e.notesapp.core.util.Constants
import com.ec25p5e.notesapp.core.util.Constants.MIN_NOTE_TITLE_LENGTH
import com.ec25p5e.notesapp.feature_note.domain.models.Category
import com.ec25p5e.notesapp.feature_note.presentation.categories.CategoryEvent
import com.ec25p5e.notesapp.feature_note.presentation.categories.CategoryViewModel
import com.ec25p5e.notesapp.feature_note.presentation.components.CategoryItem
import com.ec25p5e.notesapp.feature_note.presentation.notes.NotesEvent
import com.ec25p5e.notesapp.feature_note.presentation.notes.NotesViewModel
import com.ec25p5e.notesapp.feature_note.presentation.util.AddEditNoteError
import com.ec25p5e.notesapp.feature_note.presentation.util.UiEventNote
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoilApi::class)
@Composable
fun AddEditNoteScreen(
    imageLoader: ImageLoader,
    scaffoldState: SnackbarHostState,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    noteColor: Int,
    viewModel: AddEditNoteViewModel = hiltViewModel(),
    viewModelCategory: CategoryViewModel = hiltViewModel(),
    viewModelNotes: NotesViewModel = hiltViewModel()
) {
    val titleState = viewModel.titleState.value
    val contentState = viewModel.contentState.value
    val categoryState = viewModelCategory.state.value
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val isSavingState = viewModel.isSaving.value
    val scaffoldStateBottomSheet = rememberBottomSheetScaffoldState()
    val noteBackgroundAnimatable = remember {
        Animatable(
            Color(if (noteColor != -1) noteColor else viewModel.colorState.value)
        )
    }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val categoryBackgroundAnimatable = remember {
        Animatable(
            Color(viewModelCategory.categoryColor.value)
        )
    }
    val imageUri = viewModel.chosenImageUri.value
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        viewModel.onEvent(AddEditNoteEvent.PickImage(uri))
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

    if(isSavingState) {
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

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardToolbar(
            onNavigateUp = onNavigateUp,
            title = {
                Text(
                    text = titleState.text,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = true,
            navActions = {
                StandardOptionsMenu(
                    menuItem = {
                        DropdownMenuItem(
                            text = {
                                Text(stringResource(id = R.string.convert_in_audio_text)) },
                            onClick = {
                                viewModelNotes.onEvent(NotesEvent.ConvertInAudio(viewModel.currentNoteId!!))
                            },
                            leadingIcon = {
                                Icon(
                                    painterResource(id = R.drawable.ic_audio),
                                    contentDescription = stringResource(id = R.string.cont_descr_to_audio)
                                )
                            }
                        )
                    }
                )
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(SpaceLarge)
        ) {
            StandardTextFieldState(
                modifier = Modifier.fillMaxWidth(),
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
                error = when(titleState.error) {
                    is AddEditNoteError.FieldEmpty -> stringResource(id = R.string.field_empty_text_error)
                    is AddEditNoteError.InputTooShort -> stringResource(id = R.string.field_too_short_text_error, MIN_NOTE_TITLE_LENGTH)
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
                error = when(contentState.error) {
                    is AddEditNoteError.FieldEmpty -> stringResource(id = R.string.field_empty_text_error)
                    is AddEditNoteError.InputTooShort -> stringResource(id = R.string.field_too_short_text_error, MIN_NOTE_TITLE_LENGTH)
                    else -> ""
                }
            )

            Spacer(modifier = Modifier.height(SpaceMedium))

            Button(
                onClick = {
                    viewModel.onEvent(AddEditNoteEvent.SaveNote)
                },
                enabled = !viewModel.isSaving.value,
                modifier = Modifier.align(Alignment.End),
            ) {
                Text(
                    text = stringResource(id = R.string.save_btn_text),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.width(SpaceSmall))
                if (viewModel.isSaving.value) {
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

    BottomSheetScaffold(
        scaffoldState = scaffoldStateBottomSheet,
        sheetPeekHeight = 48.dp,
        sheetContent = {
            openBottomSheet = true

            Box(
                Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                contentAlignment = Alignment.Center
            ) {
                if(scaffoldStateBottomSheet.bottomSheetState.hasExpandedState) {
                    IconButton(
                        onClick = {
                            scope.launch {
                                scaffoldStateBottomSheet.bottomSheetState.partialExpand()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(stringResource(id = R.string.note_hide_advanced_options))
                    }
                } else {
                    Text(stringResource(id = R.string.note_advanced_options))
                }
            }

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(Color.Black)
            )

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(SpaceSmall),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Category.noteColors.forEach { color ->
                        val colorInt = color.toArgb()
                        Box(
                            modifier = Modifier
                                .size(25.dp)
                                .shadow(7.5.dp, CircleShape)
                                .clip(CircleShape)
                                .background(color)
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
                                    viewModel.onEvent(AddEditNoteEvent.ChangeColor(colorInt))
                                }
                        )
                    }
                }
            }

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(Color.Black)
            )

            Spacer(modifier = Modifier.height(4.dp))

            LazyRow(
                modifier = Modifier
                    .padding(bottom = 10.dp)) {
                items(categoryState.categories) { category ->
                    CategoryItem(
                        category = category,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 3.dp,
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

                    Spacer(modifier = Modifier.width(8.dp))
                }
            }

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(Color.Black)
            )

            Box(
                modifier = Modifier
                    .aspectRatio(16f / 9f)
                    .fillMaxWidth()
                    .padding(15.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onBackground,
                        shape = MaterialTheme.shapes.medium
                    )
                    .clickable {
                        galleryLauncher.launch(
                            PickVisualMediaRequest(
                                mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.choose_image),
                    tint = MaterialTheme.colorScheme.onBackground
                )
                imageUri?.let { uri ->
                    Image(
                        painter = rememberImagePainter(
                            data = uri,
                            imageLoader = imageLoader
                        ),
                        contentDescription = stringResource(id = R.string.post_image),
                        modifier = Modifier.matchParentSize()
                    )
                }
            }
        }
    ) {}
}
