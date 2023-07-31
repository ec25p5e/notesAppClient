package com.ec25p5e.notesapp.feature_note.presentation.categories

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ec25p5e.notesapp.core.domain.states.StandardTextFieldState
import com.ec25p5e.notesapp.feature_note.domain.model.Category
import com.ec25p5e.notesapp.feature_note.domain.model.Note
import com.ec25p5e.notesapp.feature_note.domain.use_case.category.CategoryUseCases
import com.ec25p5e.notesapp.feature_note.presentation.util.UiEventNote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryUseCases: CategoryUseCases
): ViewModel() {

    private val _state = mutableStateOf(CategoriesState())
    val state: State<CategoriesState> = _state

    private val _categoryTitle = mutableStateOf(StandardTextFieldState())
    val categoryTitle: State<StandardTextFieldState> = _categoryTitle

    private val _categoryColor = mutableStateOf(Note.noteColors.random().toArgb())
    val categoryColor: State<Int> = _categoryColor

    private val _eventFlowCategory = MutableSharedFlow<UiEventNote>()
    val eventFlowCategory = _eventFlowCategory.asSharedFlow()

    private var currentCategoryId: Int? = null

    private var recentlyDeletedCategory: Category? = null
    private var getCategoriesJob: Job? = null

    init {
        loadCategories()
    }

    fun onEvent(event: CategoryEvent) {
        when(event) {
            is CategoryEvent.EnteredTitle -> {
                _categoryTitle.value = categoryTitle.value.copy(
                    text = event.value
                )
            }
            is CategoryEvent.ChangeTitleFocus -> {
                _categoryTitle.value = categoryTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            categoryTitle.value.text.isBlank()
                )
            }
            is CategoryEvent.ChangeColor -> {
                _categoryColor.value = event.color
            }

            is CategoryEvent.DeleteCategory -> {
                viewModelScope.launch {
                    TODO("Implement delete of category")
                    recentlyDeletedCategory = event.category
                }
            }
            is CategoryEvent.RestoreCategory -> {

            }
        }
    }

    fun loadCategories() {
        getCategoriesJob?.cancel()
        getCategoriesJob = categoryUseCases.getCategories()
            .onEach { category ->
                _state.value = state.value.copy(
                    categories = category
                )
            }
            .launchIn(viewModelScope)
    }
}