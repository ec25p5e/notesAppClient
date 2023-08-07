package com.ec25p5e.notesapp.feature_auth.presentation.onboarding

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ec25p5e.notesapp.core.presentation.util.UiEvent
import com.ec25p5e.notesapp.core.util.Screen
import com.ec25p5e.notesapp.feature_settings.domain.models.AppSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val appSettings: DataStore<AppSettings>
): ViewModel(){

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    fun onEvent(event: OnBoardingEvent) {
        when(event) {
            is OnBoardingEvent.PermissionResult -> {
                onPermissionResult(event.permission, event.isGranted)
            }
            is OnBoardingEvent.SaveOnBoarding -> {
                saveOnBoardingState(event.isCompleted)
            }
            is OnBoardingEvent.DismissDialog -> {
                dismissDialog()
            }
        }
    }

    private fun dismissDialog() {
        visiblePermissionDialogQueue.removeFirst()
    }

    private fun onPermissionResult(permission: String, isGranted: Boolean) {
        if(!isGranted && !visiblePermissionDialogQueue.contains(permission)) {
            visiblePermissionDialogQueue.add(permission)
        }
    }

    private fun saveOnBoardingState(completed: Boolean) {
        viewModelScope.launch {
            appSettings.updateData {
                it.copy(isCompleteOnboarding = completed)
            }
            _eventFlow.emit(UiEvent.Navigate(Screen.LoginScreen.route))
        }
    }
}

