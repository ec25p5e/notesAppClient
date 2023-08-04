package com.ec25p5e.notesapp.feature_auth.presentation.login

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ec25p5e.notesapp.core.domain.states.PasswordTextFieldState
import com.ec25p5e.notesapp.core.domain.states.StandardTextFieldState
import com.ec25p5e.notesapp.core.presentation.util.UiEvent
import com.ec25p5e.notesapp.core.util.Resource
import com.ec25p5e.notesapp.core.util.UiText
import com.ec25p5e.notesapp.feature_auth.domain.use_case.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): ViewModel() {

    private val _emailState = mutableStateOf(StandardTextFieldState())
    val emailState: State<StandardTextFieldState> = _emailState

    private val _passwordState = mutableStateOf(PasswordTextFieldState())
    val passwordState: State<PasswordTextFieldState> = _passwordState

    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    @SuppressLint("LogNotTimber")
    fun onEvent(event: LoginEvent) {
        when(event) {
            is LoginEvent.EnteredEmail -> {
                _emailState.value = emailState.value.copy(
                    text = event.email
                )
            }
            is LoginEvent.EnteredPassword -> {
                _passwordState.value = passwordState.value.copy(
                    text = event.password
                )
            }
            is LoginEvent.TogglePasswordVisibility -> {
                _loginState.value = loginState.value.copy(
                    isPasswordVisible = loginState.value.isPasswordVisible.not()
                )
            }
            is LoginEvent.Login -> {
                viewModelScope.launch {
                    _loginState.value = loginState.value.copy(isLoading = true)

                    val loginResult = loginUseCase(
                        email = emailState.value.text,
                        password = passwordState.value.text
                    )

                    _loginState.value = loginState.value.copy(isLoading = false)
                    if(loginResult.emailError != null) {
                        _emailState.value = emailState.value.copy(
                            error = loginResult.emailError
                        )
                    }

                    if(loginResult.passwordError != null) {
                        _passwordState.value = _passwordState.value.copy(
                            error = loginResult.passwordError
                        )
                    }

                    when(loginResult.result) {
                        is Resource.Success -> {
                            _eventFlow.emit(UiEvent.OnLogin)
                        }
                        is Resource.Error -> {
                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(
                                    loginResult.result.uiText ?: UiText.unknownError()
                                )
                            )
                        }
                        else -> {
                        }
                    }
                }
            }
        }
    }
}