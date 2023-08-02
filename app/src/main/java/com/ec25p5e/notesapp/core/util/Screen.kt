package com.ec25p5e.notesapp.core.util

sealed class Screen(val route: String) {

    object SplashScreen: Screen("splash_screen")
    object LoginScreen: Screen("login_screen")
    object RegisterScreen: Screen("register_screen")
    object MainFeedScreen: Screen("main_feed_screen")
    object ProfileScreen: Screen("profile_screen")
    object NotesScreen: Screen("notes_screen")
    object CreatePostScreen: Screen("create_post")
    object ChatScreen: Screen("chat_screen")
    object CreateNoteScreen: Screen("create_note_screen")
    object TodoScreen: Screen("todo_screen")
    object ArchiveScreen: Screen("archive_screen")
    object CategoryScreen: Screen("category_screen")
}