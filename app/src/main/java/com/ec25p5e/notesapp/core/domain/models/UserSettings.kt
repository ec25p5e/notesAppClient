package com.ec25p5e.notesapp.core.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class UserSettings(
    val token: String? = null,
    val username: String? = null,
)