package com.ec25p5e.notesapp.feature_settings.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Unlock(
    val unlockMethod: UnlockMethod = UnlockMethod.NONE,
    val valueToUnlock: String = ""
)
