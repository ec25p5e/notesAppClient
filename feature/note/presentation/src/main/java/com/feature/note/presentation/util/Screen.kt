package com.feature.note.presentation.util

sealed class Screen(val route: String) {

    data object NotesScreen: Screen("notes_screen")
    data object CreateNoteScreen: Screen("create_note_screen")
}