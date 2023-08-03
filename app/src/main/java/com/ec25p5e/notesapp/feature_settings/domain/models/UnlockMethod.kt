package com.ec25p5e.notesapp.feature_settings.domain.models

data class UnlockMethod(
    val isBiometric: Boolean? = false,
    val isPin: Boolean? = false,
    val isPassword: Boolean? = false
)
