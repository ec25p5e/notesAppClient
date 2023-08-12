package com.ec25p5e.notesapp.feature_note.presentation.add_edit_category

import android.widget.Toast
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Save
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
import com.ec25p5e.notesapp.feature_note.domain.models.Category
import com.ec25p5e.notesapp.feature_note.domain.models.Note
import com.ec25p5e.notesapp.feature_note.presentation.add_edit_note.AddEditNoteEvent
import com.ec25p5e.notesapp.feature_note.presentation.util.AddEditCategoryError
import com.ec25p5e.notesapp.feature_note.presentation.util.AddEditNoteError
import com.ec25p5e.notesapp.feature_note.presentation.util.UiEventNote
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditCategoryScreen(
    imageLoader: ImageLoader,
    scaffoldState: SnackbarHostState,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    categoryId: Int,
    viewModel: AddEditCategoryViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value
    val titleState = viewModel.titleState.value
    val categoryColor = viewModel.colorState.value
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val categoryBackgroundAnimatable = remember {
        Animatable(
            Color(viewModel.colorState.value)
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
                is UiEventNote.Save -> {
                    onNavigateUp()
                }
                is UiEventNote.ShowLoader -> {
                    viewModel.onEvent(AddEditCategoryEvent.IsSaveCategory)
                }
                else -> {
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(categoryColor))
    ) {
        StandardToolbar(
            onNavigateUp = {
                if (state.isAutoSaveEnabled) {
                    viewModel.onEvent(AddEditCategoryEvent.SaveCategory)
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
                .background(Color(categoryColor)),
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
                label = stringResource(id = R.string.label_category_title_input),
                maxLength = Constants.MAX_CATEGORY_TITLE_LENGTH,
                onValueChange = {
                    viewModel.onEvent(AddEditCategoryEvent.EnteredName(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditCategoryEvent.ChangeNameFocus(it))
                },
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                error = when (titleState.error) {
                    is AddEditCategoryError.FieldEmpty -> stringResource(id = R.string.field_empty_text_error)
                    is AddEditCategoryError.InputTooShort -> stringResource(
                        id = R.string.field_too_short_text_error,
                        Constants.MIN_CATEGORY_TITLE_LENGTH
                    )

                    else -> ""
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    items(Category.categoryColor) { categoryColor ->
                        val colorInt = categoryColor.toArgb()

                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .shadow(15.dp, CircleShape)
                                .clip(CircleShape)
                                .background(categoryColor)
                                .border(
                                    width = 3.dp,
                                    color = if (viewModel.colorState.value == colorInt) {
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
                                        AddEditCategoryEvent.ChangeColor(
                                            colorInt
                                        )
                                    )
                                }
                        )

                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .padding(SpaceSmall),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        viewModel.onEvent(AddEditCategoryEvent.SaveCategory)
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
    }
}