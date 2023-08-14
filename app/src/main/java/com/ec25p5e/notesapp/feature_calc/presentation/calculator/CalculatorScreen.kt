package com.ec25p5e.notesapp.feature_calc.presentation.calculator

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.ec25p5e.notesapp.core.presentation.util.UiEvent
import com.ec25p5e.notesapp.core.presentation.util.asString
import com.ec25p5e.notesapp.feature_calc.presentation.components.CalculatorButtonGrid
import com.ec25p5e.notesapp.feature_calc.presentation.components.CalculatorDisplay
import com.ec25p5e.notesapp.feature_calc.presentation.components.calculatorActions
import com.ec25p5e.notesapp.feature_note.presentation.categories.CategoryViewModel
import com.ec25p5e.notesapp.feature_note.presentation.notes.NotesEvent
import com.ec25p5e.notesapp.feature_note.presentation.notes.NotesViewModel
import com.ec25p5e.notesapp.feature_note.presentation.util.UiEventNote
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CalculatorScreen(
    imageLoader: ImageLoader,
    scaffoldState: SnackbarHostState,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: CalculatorViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is UiEvent.ShowSnackbar -> {
                    Toast.makeText(
                        context,
                        event.uiText!!.asString(context),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is UiEvent.Navigate -> {
                    onNavigate(event.route)
                }
                else -> {
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        CalculatorDisplay(
            expression = viewModel.expression,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(
                    RoundedCornerShape(
                        bottomStart = 25.dp,
                        bottomEnd = 25.dp
                    )
                )
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(
                    vertical = 64.dp,
                    horizontal = 16.dp
                )
        )
        Spacer(modifier = Modifier.height(8.dp))
        CalculatorButtonGrid(
            actions = calculatorActions,
            onAction = viewModel::onAction,
            modifier = Modifier.padding(8.dp)
        )
    }
}