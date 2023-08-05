package com.ec25p5e.notesapp.feature_settings.presentation.unlock_method

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import com.ec25p5e.notesapp.feature_note.presentation.util.UiEventNote
import com.ec25p5e.notesapp.feature_settings.domain.models.AppSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class UnlockMethodViewModel @Inject constructor(
    private var dataStore: DataStore<AppSettings>
): ViewModel() {

    private val _state = mutableStateOf(UnlockMethodState())
    val state: State<UnlockMethodState> = _state

    private val _eventFlow = MutableSharedFlow<UiEventNote>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: UnlockMethodEvent) {
        when(event) {
            is UnlockMethodEvent.OnPinCorrect -> {
                _state.value = _state.value.copy(
                    isLoggedIn = !_state.value.isLoggedIn
                )
            }
        }
    }
}