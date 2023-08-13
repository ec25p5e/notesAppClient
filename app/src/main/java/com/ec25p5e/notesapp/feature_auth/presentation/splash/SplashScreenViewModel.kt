package com.ec25p5e.notesapp.feature_auth.presentation.splash

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ec25p5e.notesapp.core.data.local.connectivity.ConnectivityObserver
import com.ec25p5e.notesapp.core.data.local.connectivity.NetworkConnectivityObserver
import com.ec25p5e.notesapp.core.presentation.ui.theme.LightGreen
import com.ec25p5e.notesapp.core.presentation.ui.theme.Nt_Primary
import com.ec25p5e.notesapp.core.presentation.util.UiEvent
import com.ec25p5e.notesapp.core.util.Resource
import com.ec25p5e.notesapp.core.util.Screen
import com.ec25p5e.notesapp.feature_auth.domain.use_case.AuthenticateUseCase
import com.ec25p5e.notesapp.feature_note.domain.models.Note
import com.ec25p5e.notesapp.feature_note.domain.repository.NoteRepository
import com.ec25p5e.notesapp.feature_note.domain.use_case.note.NoteUseCases
import com.ec25p5e.notesapp.feature_settings.domain.models.AppSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val authenticateUseCase: AuthenticateUseCase,
    private val appSettings: DataStore<AppSettings>,
    private val networkConnectivityObserver: NetworkConnectivityObserver,
): ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            /* when(authenticateUseCase()) {
                is Resource.Success -> {
                    _eventFlow.emit(UiEvent.Navigate(Screen.NotesScreen.route))
                }
                is Resource.Error -> {
                    if(appSettings.data.first().isCompleteOnboarding) {
                        _eventFlow.emit(UiEvent.Navigate(Screen.LoginScreen.route))
                    } else {
                        _eventFlow.emit(UiEvent.Navigate(Screen.OnBoardingScreen.route))
                    }
                }
                else -> {
                }
            } */

            // if(appSettings.data.first().isCompleteOnboarding) {
                networkConnectivityObserver.observe().collectLatest {
                    Log.i("test456", it.toString())
                }
            /* } else {
                _eventFlow.emit(UiEvent.Navigate(Screen.OnBoardingScreen.route))
            } */
        }
    }
}