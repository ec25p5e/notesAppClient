package com.ec25p5e.notesapp.feature_chat.presentation.chat

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ec25p5e.notesapp.core.presentation.util.UiEvent
import com.ec25p5e.notesapp.core.util.Resource
import com.ec25p5e.notesapp.core.util.UiText
import com.ec25p5e.notesapp.feature_chat.domain.use_case.ChatUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatUseCases: ChatUseCases
): ViewModel() {

    private var _state = mutableStateOf(ChatState())
    val state: State<ChatState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        loadChats()
    }

    private fun loadChats() {
        viewModelScope.launch {
            _state.value = state.value.copy(isLoading = true)
            val result = chatUseCases.getChatsForUser()

            when(result) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        chats = result.data ?: emptyList(),
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.ShowSnackbar(
                        result.uiText ?: UiText.unknownError()
                    ))
                    _state.value = state.value.copy(isLoading = false)
                }
                else -> Unit
            }
        }
    }
}