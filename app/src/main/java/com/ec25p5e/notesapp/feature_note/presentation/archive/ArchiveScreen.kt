package com.ec25p5e.notesapp.feature_note.presentation.archive

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DismissValue
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
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.presentation.components.StandardOptionsMenu
import com.ec25p5e.notesapp.core.presentation.components.StandardToolbar
import com.ec25p5e.notesapp.core.presentation.util.LottieView
import com.ec25p5e.notesapp.core.presentation.util.asString
import com.ec25p5e.notesapp.core.util.Screen
import com.ec25p5e.notesapp.feature_note.presentation.components.NoteItem
import com.ec25p5e.notesapp.feature_note.presentation.components.OrderSection
import com.ec25p5e.notesapp.feature_note.presentation.components.OrderSectionArchive
import com.ec25p5e.notesapp.feature_note.presentation.components.SwipeBackgroundArchive
import com.ec25p5e.notesapp.feature_note.presentation.components.SwipeBackgroundNotes
import com.ec25p5e.notesapp.feature_note.presentation.notes.NotesEvent
import com.ec25p5e.notesapp.feature_note.presentation.util.UiEventNote
import kotlinx.coroutines.flow.collectLatest
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ArchiveScreen(
    imageLoader: ImageLoader,
    scaffoldState: SnackbarHostState,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: ArchiveViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    var text by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

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
                else -> {
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        StandardToolbar(
            onNavigateUp = onNavigateUp,
            title = {
                Text(
                    text = stringResource(id = R.string.all_your_notes_archived_title),
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
                                Text(stringResource(id = R.string.order_note_title)) },
                            onClick = {
                                viewModel.onEvent(ArchiveEvent.ToggleOrderSection)
                            },
                            leadingIcon = {
                                Icon(
                                    painterResource(id = R.drawable.ic_filter),
                                    contentDescription = stringResource(id = R.string.cont_descr_filter_menu)
                                )
                            }
                        )
                    }
                )
            }
        )

        if(state.isOrderSectionVisible) {
            AlertDialog(
                onDismissRequest = {
                    viewModel.onEvent(ArchiveEvent.ToggleOrderSection)
                },
                icon = {
                    Icon(painterResource(id = R.drawable.ic_filter), contentDescription = null) },
                title = {
                    Text(text = stringResource(id = R.string.dialog_filter_title))
                },
                text = {
                    OrderSectionArchive(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        noteOrder = state.noteOrder,
                        onOrderChange = {
                            viewModel.onEvent(ArchiveEvent.Order(it))
                        }
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.onEvent(ArchiveEvent.ToggleOrderSection)
                        }
                    ) {
                        Text(stringResource(id = R.string.apply_btn_text))
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
                    placeholder = { Text(stringResource(id = R.string.text_search_note_archive)) },
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

            Spacer(modifier = Modifier.height(16.dp))

            if(state.notes.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 10.dp)
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
                                            currentItem.id?.let { it1 ->
                                                ArchiveEvent.DeArchiveNote(
                                                    it1
                                                )
                                            }?.let { it2 -> viewModel.onEvent(it2) }
                                            true
                                        }
                                        DismissValue.DismissedToStart -> { false }
                                        else -> { false }
                                    }
                                }
                            )

                            SwipeToDismiss(
                                state = dismissState,
                                modifier = Modifier
                                    .padding(vertical = 1.dp)
                                    .animateItemPlacement(),
                                background = {
                                    SwipeBackgroundArchive(dismissState)
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
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    LottieView(
                        json = R.raw.empty_archive,
                        modifier = Modifier.fillMaxWidth()
                            .height(200.dp),
                        iterations = 3
                    )
                }
            }
        }
    }
}