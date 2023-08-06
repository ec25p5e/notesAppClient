package com.ec25p5e.notesapp.feature_note.presentation.categories

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DismissValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.presentation.components.StandardToolbar
import com.ec25p5e.notesapp.core.presentation.util.asString
import com.ec25p5e.notesapp.core.util.Screen
import com.ec25p5e.notesapp.feature_note.presentation.components.CategoryItem
import com.ec25p5e.notesapp.feature_note.presentation.components.NoteItem
import com.ec25p5e.notesapp.feature_note.presentation.components.SwipeBackgroundNotes
import com.ec25p5e.notesapp.feature_note.presentation.notes.NotesEvent
import com.ec25p5e.notesapp.feature_note.presentation.util.UiEventNote
import kotlinx.coroutines.flow.collectLatest

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

            }
        )

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
                    CategoryItem(
                        category = category,
                        modifier = Modifier
                            .fillMaxWidth(),
                        clickable = {

                        },
                    )
                }
            )
        }
    }
}