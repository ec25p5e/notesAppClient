package com.ec25p5e.notesapp.core.domain.states

data class StandardTextFieldState(
    val text: String = "",
    val number: Int? = -1,
    val hint: String = "",
    val isHintVisible: Boolean = false,
    val error: Error? = null
)