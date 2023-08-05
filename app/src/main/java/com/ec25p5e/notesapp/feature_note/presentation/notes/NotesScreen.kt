package com.ec25p5e.notesapp.feature_note.presentation.notes

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DismissValue
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.presentation.components.LoadingAnimation
import com.ec25p5e.notesapp.core.presentation.components.StandardLoadingAlert
import com.ec25p5e.notesapp.core.presentation.components.StandardToolbar
import com.ec25p5e.notesapp.core.util.Screen
import com.ec25p5e.notesapp.feature_note.presentation.components.CategoryItem
import com.ec25p5e.notesapp.feature_note.presentation.components.NoteItem
import com.ec25p5e.notesapp.core.presentation.components.StandardOptionsMenu
import com.ec25p5e.notesapp.core.presentation.components.StandardTextFieldState
import com.ec25p5e.notesapp.core.presentation.ui.theme.SpaceSmall
import com.ec25p5e.notesapp.core.presentation.util.asString
import com.ec25p5e.notesapp.core.util.Constants
import com.ec25p5e.notesapp.feature_auth.presentation.util.AuthError
import com.ec25p5e.notesapp.feature_note.domain.models.Category
import com.ec25p5e.notesapp.feature_note.presentation.add_edit_note.AddEditNoteEvent
import com.ec25p5e.notesapp.feature_note.presentation.categories.CategoryEvent
import com.ec25p5e.notesapp.feature_note.presentation.categories.CategoryViewModel
import com.ec25p5e.notesapp.feature_note.presentation.components.OrderSection
import com.ec25p5e.notesapp.feature_note.presentation.components.SwipeBackgroundNotes
import com.ec25p5e.notesapp.feature_note.presentation.util.UiEventNote
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NotesScreen(
    imageLoader: ImageLoader,
    scaffoldState: SnackbarHostState,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: NotesViewModel = hiltViewModel(),
    viewModelCategory: CategoryViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val categoryState = viewModelCategory.state.value
    val isCreatingCategory = viewModelCategory.isCreating.value
    val categoryTitleState = viewModelCategory.categoryTitle.value

    var text by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val categoryBackgroundAnimatable = remember {
        Animatable(
            Color(viewModelCategory.categoryColor.value)
        )
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
                is UiEventNote.IsLoadingPage -> {
                    viewModel.onEvent(NotesEvent.IsLoadingPage)
                }
                else -> {
                }
            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModelCategory.eventFlow.collectLatest { event ->
            when(event) {
                is UiEventNote.ShowSnackbar -> {
                    Toast.makeText(
                        context,
                        event.uiText!!.asString(context),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is UiEventNote.ShowLoader -> {
                    viewModelCategory.onEvent(CategoryEvent.IsCreateCategory)
                }
                else -> {
                }
            }
        }
    }

    if(isCreatingCategory) {
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
        modifier = Modifier
            .fillMaxSize()
    ) {
        StandardToolbar(
            onNavigateUp = onNavigateUp,
            title = {
                Text(
                    text = stringResource(id = R.string.all_your_notes_title),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = false,
            navActions = {
                StandardOptionsMenu(
                    menuItem = {
                        DropdownMenuItem(
                            text = {
                                Text(stringResource(id = R.string.order_note_title)) },
                            onClick = {
                                viewModel.onEvent(NotesEvent.ToggleOrderSection)
                            },
                            leadingIcon = {
                                Icon(
                                    painterResource(id = R.drawable.ic_filter),
                                    contentDescription = stringResource(id = R.string.cont_descr_filter_menu)
                                )
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(stringResource(id = R.string.new_category_title)) },
                            onClick = {
                                viewModelCategory.onEvent(CategoryEvent.ToggleCategoryCreation)
                            },
                            leadingIcon = {
                                Icon(
                                    painterResource(id = R.drawable.ic_category),
                                    contentDescription = stringResource(id = R.string.cont_descr_filter_menu)
                                )
                            }
                        )
                    }
                )
            }
        )

        if (state.isOrderSectionVisible) {
            AlertDialog(
                onDismissRequest = {
                    viewModel.onEvent(NotesEvent.ToggleOrderSection)
                },
                icon = {
                    Icon(painterResource(id = R.drawable.ic_filter), contentDescription = null) },
                title = {
                    Text(text = stringResource(id = R.string.dialog_filter_title))
                },
                text = {
                    OrderSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        noteOrder = state.noteOrder,
                        onOrderChange = {
                            viewModel.onEvent(NotesEvent.Order(it))
                        }
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.onEvent(NotesEvent.ToggleOrderSection)
                        }
                    ) {
                        Text(stringResource(id = R.string.apply_btn_text))
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        if (categoryState.isCreationVisible) {
            AlertDialog(
                onDismissRequest = {
                    viewModelCategory.onEvent(CategoryEvent.ToggleCategoryCreation)
                },
                icon = {
                    Icon(painterResource(id = R.drawable.ic_category), contentDescription = null) },
                title = {
                    Text(text = stringResource(id = R.string.dialog_new_category_title))
                },
                text = {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(SpaceSmall),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                        ) {
                            StandardTextFieldState(
                                modifier = Modifier.fillMaxWidth(),
                                text = categoryTitleState.text,
                                label = stringResource(id = R.string.label_category_title_input),
                                maxLength = Constants.MAX_CATEGORY_TITLE_LENGTH,
                                onValueChange = {
                                    viewModelCategory.onEvent(CategoryEvent.EnteredTitle(it))
                                },
                                onFocusChange = {
                                    viewModelCategory.onEvent(CategoryEvent.ChangeTitleFocus(it))
                                },
                                imeAction = ImeAction.Done,
                                keyboardType = KeyboardType.Text,
                                error = when(categoryTitleState.error) {
                                    is AuthError.FieldEmpty -> stringResource(id = R.string.field_empty_text_error)
                                    is AuthError.InputTooShort -> stringResource(id = R.string.input_too_short_text)
                                    else -> ""
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .background(Color.Black)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                items(Category.categoryColor) { noteColor ->
                                    val colorInt = noteColor.toArgb()

                                    Box(
                                        modifier = Modifier
                                            .size(25.dp)
                                            .shadow(7.5.dp, CircleShape)
                                            .clip(CircleShape)
                                            .background(noteColor)
                                            .border(
                                                width = 1.5.dp,
                                                color = if (viewModelCategory.categoryColor.value == colorInt) {
                                                    Color.Black
                                                } else Color.Transparent,
                                                shape = CircleShape
                                            )
                                            .clickable {
                                                scope.launch {
                                                    categoryBackgroundAnimatable.animateTo(
                                                        targetValue = Color(colorInt),
                                                        animationSpec = tween(
                                                            durationMillis = 500
                                                        )
                                                    )
                                                }
                                                viewModelCategory.onEvent(
                                                    CategoryEvent.ChangeColor(
                                                        colorInt
                                                    )
                                                )
                                            }
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModelCategory.onEvent(CategoryEvent.SaveCategory)
                            viewModelCategory.onEvent(CategoryEvent.ToggleCategoryCreation)
                        }
                    ) {
                        Text(stringResource(id = R.string.save_btn_text))
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp)
        ) {

            Box(
                modifier = Modifier
                    .semantics {
                        isContainer = true
                    }
                    .zIndex(1f)
                    .fillMaxWidth()
            ) {
                SearchBar(
                    modifier = Modifier.align(Alignment.TopCenter),
                    query = text,
                    onQueryChange = { text = it },
                    onSearch = { active = true },
                    active = active,
                    onActiveChange = {
                        active = it
                    },
                    placeholder = { Text(stringResource(id = R.string.text_search_note)) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                text = ""
                            },
                        ) {
                            Icon(Icons.Default.Close, contentDescription = null)
                        }
                    }
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 10.dp)
                    ) {
                        items(state.notes) { note ->
                            if((note.title.lowercase(Locale.ROOT).contains(text) || note.content.lowercase(Locale.ROOT).contains(text)) && text.isNotEmpty()) {
                                NoteItem(
                                    note = note,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            onNavigate(
                                                Screen.CreateNoteScreen.route +
                                                        "?noteId=${note.id}&noteColor=${note.color}"
                                            )
                                        },
                                )

                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if(state.isLoading) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row {
                        LoadingAnimation()
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row {
                        Text(
                            text = stringResource(id = R.string.loading),
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            } else {
                LazyRow(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                ) {
                    items(categoryState.categories) { category ->
                        val catId = category.id
                        val colorInt = category.color

                        CategoryItem(
                            category = category,
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    width = 1.5.dp,
                                    color = if ((viewModelCategory.categoryId.value == catId) ||
                                        (catId == 1 && viewModelCategory.categoryId.value == 0)
                                    ) {
                                        Color.Black
                                    } else Color.Transparent,
                                    shape = RoundedCornerShape(10.dp)
                                ),
                            clickable = {
                                scope.launch {
                                    categoryBackgroundAnimatable.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                catId?.let {
                                    CategoryEvent.ChangeCategorySelected(it)
                                }?.let { viewModelCategory.onEvent(it) }

                                catId?.let {
                                    NotesEvent.FilterNotesByCategory(it)
                                }?.let { viewModel.onEvent(it) }
                            }
                        )

                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }

                if (state.notes.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.8f)
                            .padding(bottom = 10.dp),
                        contentPadding = PaddingValues(10.dp)
                    ) {
                        items(
                            items = state.notes,
                            key = { note -> note.id!! },
                            itemContent = { note ->
                                val currentItem by rememberUpdatedState(note)
                                val dismissState = rememberDismissState(
                                    confirmValueChange = {
                                        when (it) {
                                            DismissValue.DismissedToEnd -> {
                                                viewModel.onEvent(NotesEvent.ArchiveNote(currentItem.id!!))
                                                true
                                            }

                                            DismissValue.DismissedToStart -> {
                                                viewModel.onEvent(NotesEvent.SetNoteToDelete(currentItem))
                                                state.isDeleting
                                            }

                                            else -> {
                                                false
                                            }
                                        }
                                    }
                                )

                                SwipeToDismiss(
                                    state = dismissState,
                                    modifier = Modifier
                                        .padding(vertical = 1.dp)
                                        .animateItemPlacement(),
                                    background = {
                                        SwipeBackgroundNotes(dismissState)
                                    },
                                    dismissContent = {
                                        NoteItem(
                                            note = note,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable {
                                                    onNavigate(
                                                        Screen.CreateNoteScreen.route +
                                                                "?noteId=${currentItem.id}&noteColor=${currentItem.color}"
                                                    )
                                                },
                                        )
                                    }
                                )
                            }
                        )
                    }
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.all_task_done),
                        contentDescription = stringResource(id = R.string.cont_descr_image_all_task_done),
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.CenterHorizontally)
                            .scale(0.5f)
                    )
                }
            }

            if(state.isDeleting) {
                AlertDialog(
                    onDismissRequest = {
                        viewModel.onEvent(NotesEvent.IsDeletingNote)
                    },
                    icon = {
                        Icon(painterResource(id = R.drawable.ic_delete), contentDescription = null) },
                    title = {
                        Text(text = stringResource(id = R.string.dialog_delete_note_confirm))
                    },
                    text = {
                        Text(text = stringResource(id = R.string.delete_note_description))
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                viewModel.onEvent(NotesEvent.DeleteNote)
                            }
                        ) {
                            Text(stringResource(id = R.string.confirm_btn_text))
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                viewModel.onEvent(NotesEvent.IsDeletingNote)
                            }
                        ) {
                            Text(stringResource(id = R.string.dismiss_btn_text))
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}