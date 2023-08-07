package com.ec25p5e.notesapp.feature_settings.domain.use_case.sync_note

import android.util.Log
import com.ec25p5e.notesapp.core.data.local.datastore_pref.DataStorePreferenceImpl
import com.ec25p5e.notesapp.feature_note.domain.repository.NoteRepository
import com.ec25p5e.notesapp.feature_settings.domain.models.SyncOption

class SyncNotesUseCase(
    private val noteRepository: NoteRepository,
) {

    suspend operator fun invoke(options: SyncOption) {
        if(options.isSyncNotes) {
            val result = noteRepository.pushNotes()
            Log.d("test2", result.data.toString())
        }
    }
}