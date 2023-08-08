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
import com.ec25p5e.notesapp.feature_note.domain.util.CategoryOrder
import com.ec25p5e.notesapp.feature_note.domain.util.OrderType
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

    private val _categoryColor = mutableStateOf(Category.categoryColor.random().toArgb())
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
                    val resultDelete = categoryUseCases.deleteCategory(_state.value.categoryToDelete!!)

                    if(resultDelete) {
                        _eventFlow.emit(UiEventNote.ShowSnackbar(UiText.StringResource(id = R.string.category_deleted)))
                    } else {
                        _eventFlow.emit(UiEventNote.ShowSnackbar(UiText.StringResource(id = R.string.locked_note_found)))
                    }
                }
            }
            is CategoryEvent.RestoreCategory -> {

            }
            is CategoryEvent.SetToDelete -> {
                _state.value = _state.value.copy(
                    categoryToDelete = event.category,
                    isDeleting = !_state.value.isDeleting
                )
            }
            is CategoryEvent.ToggleCategoryDelete -> {
                _state.value = _state.value.copy(
                    isDeleting = !_state.value.isDeleting
                )
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

                    val result = categoryUseCases.addCategory(
                        Category(
                            name = categoryTitle.value.text,
                            color = categoryColor.value,
                            timestamp = System.currentTimeMillis(),
                            id = currentCategoryId
                        )
                    )

                    if(result.titleError != null) {
                        _categoryTitle.value = categoryTitle.value.copy(
                            error = result.titleError
                        )
                    }

                    if(result.isCorrect()) {
                        _categoryTitle.value = _categoryTitle.value.copy(
                            text = "",
                            hint = "",
                            isHintVisible = false,
                            error = null
                        )

                        val newColor = Category.categoryColor.random().toArgb()
                        _categoryColor.value = newColor
                    }

                    _eventFlow.emit(UiEventNote.ShowLoader)
                }
            }
        }
    }

    fun loadCategories() {
        viewModelScope.launch {
            getCategoriesJob?.cancel()
            getCategoriesJob = categoryUseCases.getCategories(CategoryOrder.Date(OrderType.Ascending), false)
                .onEach { category ->
                    _state.value = state.value.copy(
                        categories = category
                    )
                }
                .launchIn(viewModelScope)
        }
    }
}