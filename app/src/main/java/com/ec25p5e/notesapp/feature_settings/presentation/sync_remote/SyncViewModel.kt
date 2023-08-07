package com.ec25p5e.notesapp.feature_settings.presentation.sync_remote

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ec25p5e.notesapp.feature_settings.domain.models.AppSettings
import com.ec25p5e.notesapp.feature_settings.domain.models.SyncOption
import com.ec25p5e.notesapp.feature_settings.domain.use_case.sync_note.SyncNoteUseCases
import com.ec25p5e.notesapp.feature_settings.presentation.settings.SettingsState
import com.ec25p5e.notesapp.feature_settings.presentation.util.UiEventSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SyncViewModel @Inject constructor(
    private val dataStore: DataStore<AppSettings>,
    private val syncNoteUseCases: SyncNoteUseCases,
): ViewModel() {

    private val _state = mutableStateOf(
        SettingsState(
            settings = dataStore,
            settingsLive = runBlocking { dataStore.data.first() }
        )
    )
    var state: State<SettingsState> = _state

    private val _viewModelState = mutableStateOf(SyncState())
    val viewModelState: State<SyncState> = _viewModelState

    private val _eventFlow = MutableSharedFlow<UiEventSettings>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: SyncEvent) {
        when(event) {
            is SyncEvent.ToggleNoteSync -> {
                updateLiveData(
                    isSyncNotes = !_state.value.settingsLive.syncOptions.isSyncNotes,
                    isSyncCategories = _state.value.settingsLive.syncOptions.isSyncCategories,
                    isSyncTasks = _state.value.settingsLive.syncOptions.isSyncTasks
                )
                saveSettings()
            }
            is SyncEvent.ToggleCategoriesSync -> {
                updateLiveData(
                    isSyncNotes = _state.value.settingsLive.syncOptions.isSyncNotes,
                    isSyncCategories = !_state.value.settingsLive.syncOptions.isSyncCategories,
                    isSyncTasks = _state.value.settingsLive.syncOptions.isSyncTasks
                )
                saveSettings()
            }
            is SyncEvent.ToggleTasksSync -> {
                updateLiveData(
                    isSyncNotes = _state.value.settingsLive.syncOptions.isSyncNotes,
                    isSyncCategories = _state.value.settingsLive.syncOptions.isSyncCategories,
                    isSyncTasks = !_state.value.settingsLive.syncOptions.isSyncTasks
                )
                saveSettings()
            }
            is SyncEvent.StartSync -> {
                startSync()
            }
        }
    }

    private fun saveSettings() {
        viewModelScope.launch {
            dataStore.updateData {
                it.copy(
                    syncOptions = SyncOption(
                        isSyncNotes = _state.value.settingsLive.syncOptions.isSyncNotes,
                        isSyncCategories = _state.value.settingsLive.syncOptions.isSyncCategories,
                        isSyncTasks = _state.value.settingsLive.syncOptions.isSyncTasks
                    )
                )
            }
        }
    }

    private fun updateLiveData(
        isSyncNotes: Boolean,
        isSyncCategories: Boolean,
        isSyncTasks: Boolean
    ) {
        _state.value = _state.value.copy(
            settingsLive = _state.value.settingsLive.copy(
                syncOptions = _state.value.settingsLive.syncOptions.copy(
                    isSyncNotes = isSyncNotes,
                    isSyncCategories = isSyncCategories,
                    isSyncTasks = isSyncTasks
                )
            )
        )
    }

    private fun startSync() {
        viewModelScope.launch {
            _viewModelState.value = _viewModelState.value.copy(
                isSyncing = true
            )
            delay(1500)

            syncNoteUseCases.syncNote(_state.value.settings.data.first().syncOptions)
            syncNoteUseCases.syncCategories(_state.value.settings.data.first().syncOptions)
            syncNoteUseCases.syncTasks(_state.value.settings.data.first().syncOptions)

            delay(2500)
            _viewModelState.value = _viewModelState.value.copy(
                isSyncing = false
            )
        }
    }
}