package com.ec25p5e.notesapp.feature_settings.domain.models

import com.ec25p5e.notesapp.feature_settings.domain.models.Language
import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    val language: Language = Language.ENGLISH
)