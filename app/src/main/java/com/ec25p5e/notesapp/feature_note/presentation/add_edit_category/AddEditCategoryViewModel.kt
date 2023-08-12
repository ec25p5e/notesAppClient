package com.ec25p5e.notesapp.feature_note.presentation.add_edit_category

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.datastore.core.DataStore
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.domain.states.StandardTextFieldState
import com.ec25p5e.notesapp.core.util.UiText
import com.ec25p5e.notesapp.feature_note.domain.models.Category
import com.ec25p5e.notesapp.feature_note.domain.use_case.category.CategoryUseCases
import com.ec25p5e.notesapp.feature_note.presentation.util.UiEventNote
import com.ec25p5e.notesapp.feature_settings.domain.models.AppSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class AddEditCategoryViewModel @Inject constructor(
    private var categoryUseCases: CategoryUseCases,
    private var dataStore: DataStore<AppSettings>,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _titleState = mutableStateOf(StandardTextFieldState())
    val titleState: State<StandardTextFieldState> = _titleState

    private val _colorState = mutableStateOf(Category.categoryColor.random().toArgb())
    val colorState: State<Int> = _colorState

    private val _state = mutableStateOf(AddEditCategoryState(
        isAutoSaveEnabled = runBlocking { dataStore.data.first().isAutoSaveEnabled }
    ))
    val state: State<AddEditCategoryState> = _state

    private val _eventFlow = MutableSharedFlow<UiEventNote>()
    val eventFlow = _eventFlow.asSharedFlow()

    var currentCategoryId: Int? = null

    init {
        savedStateHandle.get<Int>("categoryId")?.let { catId ->
            if(catId != -1) {
                viewModelScope.launch {
                    categoryUseCases.getCategoryById(catId)?.also { category ->
                        currentCategoryId = category.id
                        _titleState.value = _titleState.value.copy(
                            text = category.name,
                            isHintVisible = false
                        )
                        _colorState.value = category.color
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditCategoryEvent) {
        when(event) {
            is AddEditCategoryEvent.EnteredName -> {
                _titleState.value = titleState.value.copy(
                    text = event.value
                )
            }
            is AddEditCategoryEvent.ChangeNameFocus -> {
                _titleState.value = titleState.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                        titleState.value.text.isBlank()
                )
            }
            is AddEditCategoryEvent.SaveCategory -> {
                saveCategory()
            }
            is AddEditCategoryEvent.IsSaveCategory -> {
                _state.value = _state.value.copy(
                    isSaving = !_state.value.isSaving
                )
            }
            is AddEditCategoryEvent.ChangeColor -> {
                _colorState.value = event.color
            }
        }
    }

    private fun saveCategory() {
        viewModelScope.launch {
            _eventFlow.emit(UiEventNote.ShowLoader)

            val categoryInsert = Category(
                name = _titleState.value.text,
                timestamp = System.currentTimeMillis(),
                color = colorState.value
            )

            val addingResult = categoryUseCases.addCategory(categoryInsert)

            if(addingResult.titleError != null) {
                _titleState.value = titleState.value.copy(
                    error = addingResult.titleError
                )

                _state.value = _state.value.copy(
                    isSaving = false
                )
            }

            if(addingResult.isCorrect()) {
                _eventFlow.emit(UiEventNote.ShowSnackbar(UiText.StringResource(id =
                if(currentCategoryId == null) {
                    R.string.category_created
                } else {
                    R.string.category_updated_text
                }
                )))

                _eventFlow.emit(UiEventNote.Save)
            }
        }
    }
}