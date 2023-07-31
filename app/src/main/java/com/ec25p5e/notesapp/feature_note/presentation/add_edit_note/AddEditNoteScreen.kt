package com.ec25p5e.notesapp.feature_note.presentation.add_edit_note

import android.widget.Toast
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Divider
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ch.qos.logback.core.util.Loader.getResources
import coil.ImageLoader
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.presentation.components.StandardTextFieldState
import com.ec25p5e.notesapp.core.presentation.components.StandardToolbar
import com.ec25p5e.notesapp.core.presentation.ui.theme.SpaceLarge
import com.ec25p5e.notesapp.core.presentation.ui.theme.SpaceMedium
import com.ec25p5e.notesapp.core.presentation.ui.theme.SpaceSmall
import com.ec25p5e.notesapp.core.util.Constants
import com.ec25p5e.notesapp.feature_note.domain.model.Category
import com.ec25p5e.notesapp.feature_note.domain.model.Note
import com.ec25p5e.notesapp.feature_note.presentation.categories.CategoryEvent
import com.ec25p5e.notesapp.feature_note.presentation.categories.CategoryViewModel
import com.ec25p5e.notesapp.feature_note.presentation.components.CategoryItem
import com.ec25p5e.notesapp.feature_note.presentation.util.UiEventNote
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    imageLoader: ImageLoader,
    scaffoldState: SnackbarHostState,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    noteColor: Int,
    viewModel: AddEditNoteViewModel = hiltViewModel(),
    viewModelCategory: CategoryViewModel = hiltViewModel()
) {
    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value
    val categoryState = viewModelCategory.state.value
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val scaffoldStateBottomSheet = rememberBottomSheetScaffoldState()
    val noteBackgroundAnimatable = remember {
        Animatable(
            Color(if (noteColor != -1) noteColor else viewModel.noteColor.value)
        )
    }
    val categoryBackgroundAnimatable = remember {
        Animatable(
            Color(viewModelCategory.categoryColor.value)
        )
    }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is UiEventNote.ShowSnackbar -> {
                    Toast.makeText(
                        context,
                        context.resources.getText(event.uiText.toString().toInt()),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is UiEventNote.SaveNote -> {
                    onNavigateUp()
                }
                else -> {
                }
            }
        }
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
                IconButton(onClick = {
                    viewModel.onEvent(AddEditNoteEvent.SaveNote)
                }) {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = stringResource(id = R.string.cont_descr_save_note),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
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
            )

            Spacer(modifier = Modifier.height(SpaceMedium))

            StandardTextFieldState(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f),
                text = contentState.text,
                label = stringResource(id = R.string.label_note_description_input),
                maxLength = Constants.MAX_NOTE_DESCRIPTION_LENGTH,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.EnteredContent(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeContentFocus(it))
                },
                singleLine = false,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
            )
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
                                    width = 3.dp,
                                    color = if (viewModel.noteColor.value == colorInt) {
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
        }
    ) {}
}