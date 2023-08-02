package com.ec25p5e.notesapp.feature_note.presentation.categories

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.domain.states.StandardTextFieldState
import com.ec25p5e.notesapp.core.util.UiText
import com.ec25p5e.notesapp.feature_note.domain.exceptions.InvalidCategoryException
import com.ec25p5e.notesapp.feature_note.domain.models.Category
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

    private val _categoryColor = mutableStateOf(Category.noteColors.random().toArgb())
    val categoryColor: State<Int> = _categoryColor

    private val _categoryId = mutableStateOf(0)
    val categoryId: State<Int> = _categoryId

    private val _isCreating = mutableStateOf(false)
    val isCreating: State<Boolean> = _isCreating

    private val _eventFlow = MutableSharedFlow<UiEventNote>()
    val eventFlow = _eventFlow.asSharedFlow()

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
            is CategoryEvent.ChangeCategorySelected -> {
                _categoryId.value = event.id
            }
            is CategoryEvent.DeleteCategory -> {
                viewModelScope.launch {
                    TODO("Implement delete of category")
                    recentlyDeletedCategory = event.category
                }
            }
            is CategoryEvent.RestoreCategory -> {

            }
            is CategoryEvent.ToggleCategoryCreation -> {
                _state.value = state.value.copy(
                    isCreationVisible = !state.value.isCreationVisible
                )
            }
            is CategoryEvent.FetchCategory -> {

            }
            is CategoryEvent.IsCreateCategory -> {
                _isCreating.value = !_isCreating.value
            }
            is CategoryEvent.SaveCategory -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEventNote.ShowLoader)

                    try {
                        val response = categoryUseCases.addCategory(
                            Category(
                                name = categoryTitle.value.text,
                                color = categoryColor.value,
                                timestamp = System.currentTimeMillis(),
                                id = currentCategoryId
                            )
                        )

                        if(response.uiText != null) {
                            _eventFlow.emit(UiEventNote.ShowSnackbar(response.uiText))
                        } else {
                            _eventFlow.emit(UiEventNote.ShowSnackbar(UiText.StringResource(R.string.category_created)))
                        }

                        _categoryTitle.value = _categoryTitle.value.copy(
                            text = "",
                            hint = "",
                            isHintVisible = false,
                            error = null
                        )

                        val newColor = Category.noteColors.random().toArgb()
                        _categoryColor.value = newColor
                    } catch(e: InvalidCategoryException) {
                        _eventFlow.emit(
                            UiEventNote.ShowSnackbar(
                                UiText.DynamicString(e.message ?: "Couldn't save the category")
                            )
                        )
                    }

                    _eventFlow.emit(UiEventNote.ShowLoader)
                }
            }
        }
    }

    fun loadCategories() {
        viewModelScope.launch {
            getCategoriesJob?.cancel()
            getCategoriesJob = categoryUseCases.getCategories(true)
                .onEach { category ->
                    _state.value = state.value.copy(
                        categories = category
                    )
                }
                .launchIn(viewModelScope)
        }
    }
}