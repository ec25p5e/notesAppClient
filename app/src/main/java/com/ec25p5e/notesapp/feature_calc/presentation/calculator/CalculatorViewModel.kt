package com.ec25p5e.notesapp.feature_calc.presentation.calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ec25p5e.notesapp.core.presentation.util.UiEvent
import com.ec25p5e.notesapp.core.util.Screen
import com.ec25p5e.notesapp.feature_calc.domain.repository.CalculatorAction
import com.ec25p5e.notesapp.feature_calc.domain.util.ExpressionWriter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val writer: ExpressionWriter,
): ViewModel() {

    var expression by mutableStateOf("")
        private set

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onAction(action: CalculatorAction) {
        writer.processAction(action)
        this.expression = writer.expression

        val APP_SEQUENCE = "5854.0"

        if(this.expression == APP_SEQUENCE) {
            viewModelScope.launch {
                _eventFlow.emit(UiEvent.Navigate(Screen.SplashScreen.route))
            }
        }
    }
}