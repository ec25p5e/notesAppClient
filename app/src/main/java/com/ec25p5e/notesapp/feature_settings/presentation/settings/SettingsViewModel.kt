package com.ec25p5e.notesapp.feature_settings.presentation.settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.util.Constants
import com.ec25p5e.notesapp.core.util.UiText
import com.ec25p5e.notesapp.feature_settings.domain.models.Settings
import com.ec25p5e.notesapp.feature_settings.domain.use_case.SettingsUseCases
import com.ec25p5e.notesapp.feature_settings.presentation.util.UiEventSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.DecimalFormatSymbols
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsUseCases: SettingsUseCases
) : ViewModel() {

    private val _textPreference: MutableStateFlow<String> = MutableStateFlow("")
    var textPreference = _textPreference.asStateFlow()

    private val _intPreference: MutableStateFlow<Int> = MutableStateFlow(0)
    var intPreference = _intPreference.asStateFlow()

    // to get separator for the locale
    private val separatorChar = DecimalFormatSymbols.getInstance(Locale.ENGLISH).decimalSeparator

    private val _state = mutableStateOf(SettingsState(
        settings = settingsUseCases.getSharedPreferences(Constants.KEY_SETTINGS)
    ))
    val state: State<SettingsState> = _state

    private val _eventFlow = MutableSharedFlow<UiEventSettings>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: SettingsEvent) {
        when(event) {
            is SettingsEvent.ToggleAutoSave -> {
                viewModelScope.launch {
                    val oldValue = _state.value.settings!!.isAutoSaveEnabled

                    _state.value = _state.value.copy(
                        settings = Settings(
                            isAutoSaveEnabled = _state.value.settings!!.isAutoSaveEnabled.not(),
                            isScreenshotEnabled = _state.value.settings!!.isScreenshotEnabled
                        )
                    )

                    settingsUseCases.editSharedPreferences(_state.value.settings!!)

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
            is SettingsEvent.ToggleScreenShotMode -> {
                viewModelScope.launch {
                    val oldValue = _state.value.settings!!.isScreenshotEnabled

                    _state.value = _state.value.copy(
                        settings = Settings(
                            isAutoSaveEnabled = _state.value.settings!!.isAutoSaveEnabled,
                            isScreenshotEnabled = _state.value.settings!!.isScreenshotEnabled.not()
                        )
                    )

                    settingsUseCases.editSharedPreferences(_state.value.settings!!)

                    _eventFlow.emit(UiEventSettings.ShowSnackbar(
                        UiText.StringResource(id =
                        if(oldValue) {
                            R.string.screenshot_mode_enabled
                        } else {
                            R.string.screenshot_mode_disabled
                        }
                        )
                    ))
                }
            }
        }
    }

    fun saveText(finalText: String) {
        _textPreference.value = finalText
        // place to store text
    }

    // just checking, if it is not empty - but you can check anything
    fun checkTextInput(text: String) = text.isNotEmpty()

    // filtering only numbers and decimal separator
    fun filterNumbers(text: String): String = text.filter { it.isDigit() || it == separatorChar}

    // someone can still put more decimal points into the textfield
    // we should always try to convert text to number
    fun checkNumber(text: String): Boolean {
        val value = text.toDoubleOrNull() ?: return false
        return value < 0
    }

    // saving the number / show error if something goes wrong
    fun saveNumber(text: String) {
        val value = text.toDoubleOrNull()
            ?: 0 // default value / handle the error in some way - show toast or something
    }

}