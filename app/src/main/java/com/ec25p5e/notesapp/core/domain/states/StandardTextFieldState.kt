package com.ec25p5e.notesapp.core.domain.states

data class StandardTextFieldState(
    val text: String = "",
    val error: Error? = null
)