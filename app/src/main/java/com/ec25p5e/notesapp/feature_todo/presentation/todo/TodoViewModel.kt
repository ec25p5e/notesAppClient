package com.ec25p5e.notesapp.feature_todo.presentation.todo

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ec25p5e.notesapp.core.domain.states.StandardTextFieldState
import com.ec25p5e.notesapp.feature_note.presentation.add_edit_note.AddEditNoteEvent
import com.ec25p5e.notesapp.feature_todo.presentation.add_edit_task.AddEditTaskEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {


}