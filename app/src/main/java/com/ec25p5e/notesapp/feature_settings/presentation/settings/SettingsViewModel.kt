package com.ec25p5e.notesapp.feature_settings.presentation.settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.util.UiText
import com.ec25p5e.notesapp.feature_settings.domain.models.AppSettings
import com.ec25p5e.notesapp.feature_settings.presentation.util.UiEventSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStore: DataStore<AppSettings>
) : ViewModel() {

    private val _state = mutableStateOf(SettingsState(
        settings = dataStore,
        settingsLive = runBlocking { dataStore.data.first() }
    ))
    var state: State<SettingsState> = _state

    private val _eventFlow = MutableSharedFlow<UiEventSettings>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: SettingsEvent) {
        when(event) {
            is SettingsEvent.ToggleAutoSave -> {
                viewModelScope.launch {
                    val oldValue = _state.value.settings.data.first().isAutoSaveEnabled

                    dataStore.updateData {
                        it.copy(isAutoSaveEnabled = !it.isAutoSaveEnabled)
                    }

                    _eventFlow.emit(UiEventSettings.ShowSnackbar(
                        UiText.StringResource(id =
                            if(oldValue) {
                                R.string.auto_save_disabled
                            } else {
                                R.string.auto_save_enabled
                            }
                        )
                    ))
                }
            }
            is SettingsEvent.ToggleSharingMode -> {
                viewModelScope.launch {
                    val oldValue = _state.value.settings!!.data.first().isSharingEnabled

                    dataStore.updateData {
                        it.copy(isSharingEnabled = !it.isSharingEnabled)
                    }

                    _eventFlow.emit(UiEventSettings.ShowSnackbar(
                        UiText.StringResource(id =
                        if(oldValue) {
                            R.string.sharing_mode_disabled
                        } else {
                            R.string.sharing_mode_enabled
                        }
                        )
                    ))
                }
            }
        }
    }

    fun getSettings(): AppSettings {
        var result = AppSettings()

        viewModelScope.launch {
            result = _state.value.settings.data.first()
        }

        return result
    }
}