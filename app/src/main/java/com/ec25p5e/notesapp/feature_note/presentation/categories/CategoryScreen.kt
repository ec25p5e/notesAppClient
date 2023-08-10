package com.ec25p5e.notesapp.feature_note.presentation.categories

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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetScaffold
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
import androidx.compose.material3.rememberBottomSheetScaffoldState
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.presentation.components.StandardDeleteItem
import com.ec25p5e.notesapp.core.presentation.components.StandardOptionsMenu
import com.ec25p5e.notesapp.core.presentation.components.StandardTextFieldState
import com.ec25p5e.notesapp.core.presentation.components.StandardToolbar
import com.ec25p5e.notesapp.core.presentation.ui.theme.SpaceSmall
import com.ec25p5e.notesapp.core.presentation.util.asString
import com.ec25p5e.notesapp.core.util.Constants
import com.ec25p5e.notesapp.core.util.Screen
import com.ec25p5e.notesapp.feature_auth.presentation.util.AuthError
import com.ec25p5e.notesapp.feature_note.domain.models.Category
import com.ec25p5e.notesapp.feature_note.presentation.components.CategoryItem
import com.ec25p5e.notesapp.feature_note.presentation.components.CategoryItemDetail
import com.ec25p5e.notesapp.feature_note.presentation.components.NoteItem
import com.ec25p5e.notesapp.feature_note.presentation.components.SwipeBackgroundNotes
import com.ec25p5e.notesapp.feature_note.presentation.notes.NotesEvent
import com.ec25p5e.notesapp.feature_note.presentation.util.UiEventNote
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    scaffoldState: SnackbarHostState,
    imageLoader: ImageLoader,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.state.value
    var text by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }
    val scaffoldBottomSheet = rememberBottomSheetScaffoldState()
    var bottomSheet by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val categoryTitleState = viewModel.categoryTitle.value
    val categoryBackgroundAnimatable = remember {
        Animatable(
            Color(viewModel.categoryColor.value)
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
                    text = stringResource(id = R.string.all_your_categories),
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
                                // viewModel.onEvent(CategoryEvent.ToggleOrderSection)
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
                                viewModel.onEvent(CategoryEvent.ToggleCategoryCreation)
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
                        items(state.categories) { category ->
                            if (category.name.lowercase(Locale.ROOT).contains(text) && text.isNotEmpty()) {
                                CategoryItemDetail(
                                    category = category,
                                )

                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)
                    .padding(bottom = 10.dp),
                contentPadding = PaddingValues(10.dp)
            ) {
                items(
                    items = state.categories,
                    key = { category -> category.id!! },
                    itemContent = { category ->
                        CategoryItemDetail(
                            category = category,
                            onDeleteClick = {
                                viewModel.onEvent(CategoryEvent.SetToDelete(category))
                            },
                            onEditClick = {
                                viewModel.onEvent(CategoryEvent.ToggleCategoryCreation)
                            }
                        )
                    }
                )
            }

            if(state.isDeleting) {
                StandardDeleteItem(
                    onDismissRequest = { viewModel.onEvent(CategoryEvent.ToggleCategoryDelete) },
                    icon = R.drawable.ic_delete,
                    title = R.string.dialog_delete_category_confirm,
                    text = R.string.delete_category_description,
                    confirmButtonClick = { viewModel.onEvent(CategoryEvent.DeleteCategory) },
                    dismissButton = { viewModel.onEvent(CategoryEvent.ToggleCategoryDelete) },
                )
            }
        }
    }


    /**
     * Edit category section
     */
    if (state.isCreationVisible) {
        AlertDialog(
            onDismissRequest = {
                viewModel.onEvent(CategoryEvent.ToggleCategoryCreation)
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
                                viewModel.onEvent(CategoryEvent.EnteredTitle(it))
                            },
                            onFocusChange = {
                                viewModel.onEvent(CategoryEvent.ChangeTitleFocus(it))
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
                                            color = if (viewModel.categoryColor.value == colorInt) {
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
                                            viewModel.onEvent(
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
                        viewModel.onEvent(CategoryEvent.SaveCategory)
                        viewModel.onEvent(CategoryEvent.ToggleCategoryCreation)
                    }
                ) {
                    Text(stringResource(id = R.string.save_btn_text))
                }
            },
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}