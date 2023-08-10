package com.ec25p5e.notesapp.feature_profile.presentation.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ec25p5e.notesapp.core.presentation.util.UiEvent
import com.ec25p5e.notesapp.core.util.Event
import com.ec25p5e.notesapp.core.util.Resource
import com.ec25p5e.notesapp.core.util.UiText
import com.ec25p5e.notesapp.feature_profile.domain.use_case.GetOwnUserIdCase
import com.ec25p5e.notesapp.feature_profile.domain.use_case.GetProfileUseCase
import com.ec25p5e.notesapp.feature_profile.domain.use_case.ProfileUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCases,
    private val getOwnUserId: GetOwnUserIdCase,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _toolbarState = mutableStateOf(ProfileToolbarState())
    val toolbarState: State<ProfileToolbarState> = _toolbarState

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: ProfileEvent) {
        when(event) {
            is ProfileEvent.ShowLogoutDialog -> {
                _state.value = _state.value.copy(
                    isLogoutDialogVisible = true
                )
            }
        }
    }

    fun getProfile(userId: String?) {
       viewModelScope.launch {
           _state.value = _state.value.copy(
               isLoading = true
           )

           val result = profileUseCase.getProfile(
               userId ?: getOwnUserId()
           )

           when(result) {
               is Resource.Success -> {
                   _state.value = _state.value.copy(
                       profile = result.data,
                       isLoading = false
                   )
               }
               is Resource.Error -> {
                   _state.value = _state.value.copy(
                       isLoading = false
                   )

                   _eventFlow.emit(
                       UiEvent.ShowSnackbar(
                           uiText = result.uiText ?: UiText.unknownError()
                       )
                   )
               }
               else -> Unit
           }
       }
    }
}