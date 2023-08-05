package com.ec25p5e.notesapp.feature_settings.presentation.unlock_method

sealed class UnlockMethodEvent {
    data object OnPinCorrect: UnlockMethodEvent()
}
