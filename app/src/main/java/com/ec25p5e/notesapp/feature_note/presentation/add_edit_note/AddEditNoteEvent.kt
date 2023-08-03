package com.ec25p5e.notesapp.feature_note.presentation.add_edit_note

import android.net.Uri
import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvent {
    data class EnteredTitle(val value: String): AddEditNoteEvent()
    data class ChangeTitleFocus(val focusState: FocusState): AddEditNoteEvent()
    data class EnteredContent(val value: String): AddEditNoteEvent()
    data class ChangeContentFocus(val focusState: FocusState): AddEditNoteEvent()
    data class ChangeColor(val color: Int): AddEditNoteEvent()
    data class ChangeCategoryColor(val categoryId: Int): AddEditNoteEvent()
    data class PickImage(val uri: Uri?): AddEditNoteEvent()
    object IsSaveNote: AddEditNoteEvent()

    object SaveNote: AddEditNoteEvent()

}
