package com.ec25p5e.notesapp.feature_note.presentation.add_edit_note

import android.content.Context
import android.net.Uri
import androidx.compose.ui.focus.FocusState
import java.io.File

sealed class AddEditNoteEvent {
    data class EnteredTitle(val value: String): AddEditNoteEvent()
    data class ChangeTitleFocus(val focusState: FocusState): AddEditNoteEvent()
    data class EnteredContent(val value: String): AddEditNoteEvent()
    data class ChangeContentFocus(val focusState: FocusState): AddEditNoteEvent()
    data class EnteredPin(val value: String): AddEditNoteEvent()
    data class ChangePinFocus(val focusState: FocusState): AddEditNoteEvent()
    data class ChangeColor(val color: Int): AddEditNoteEvent()
    data class ChangeCategoryColor(val categoryId: Int): AddEditNoteEvent()
    data class PickImage(val uri: List<Uri?>): AddEditNoteEvent()
    data class ChangeBgImage(val bgImage: Int): AddEditNoteEvent()
    data class DeleteImage(val uri: Uri): AddEditNoteEvent()
    data class ConvertInAudio(val noteId: Int, val context: Context) : AddEditNoteEvent()
    data class ReadNote(val context: Context): AddEditNoteEvent()
    data class SaveNote(val file: File): AddEditNoteEvent()
    data object UnlockNote: AddEditNoteEvent()

    data object IsSaveNote: AddEditNoteEvent()

    data object ToggleArchived: AddEditNoteEvent()
    data object ToggleCategoryModal: AddEditNoteEvent()
    data object ToggleLockMode: AddEditNoteEvent()
    data object TogglePinError: AddEditNoteEvent()
}
