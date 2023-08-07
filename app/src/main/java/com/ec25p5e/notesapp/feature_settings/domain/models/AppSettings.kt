package com.ec25p5e.notesapp.feature_settings.domain.models

import androidx.datastore.core.DataStore
import com.ec25p5e.notesapp.feature_settings.domain.models.Language
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    val language: Language = Language.ENGLISH,
    var isAutoSaveEnabled: Boolean = false,
    val unlock: Unlock = Unlock(
        unlockMethod = UnlockMethod.PIN,
        valueToUnlock = "2005"
    ),
    val appTheme: AppTheme = AppTheme.NATURE,
    val isSharingEnabled: Boolean = true,
    val syncOptions: SyncOption = SyncOption(),
    val isCompleteOnboarding: Boolean = false,
)