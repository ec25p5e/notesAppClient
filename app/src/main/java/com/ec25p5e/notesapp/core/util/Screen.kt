package com.ec25p5e.notesapp.core.util

sealed class Screen(val route: String) {

    object SplashScreen: Screen("splash_screen")
    object LoginScreen: Screen("login_screen")
    object RegisterScreen: Screen("register_screen")
    object OnBoardingScreen: Screen("onboarding_screen")
    object ProfileScreen: Screen("profile_screen")
    object NotesScreen: Screen("notes_screen")
    object CreatePostScreen: Screen("create_post")
    object CreateNoteScreen: Screen("create_note_screen")
    object TodoScreen: Screen("todo_screen")
    object ArchiveScreen: Screen("archive_screen")
    object CategoryScreen: Screen("category_screen")
    object SettingsScreen: Screen("settings_screen")
    object SelectThemeScreen: Screen("select_theme_screen")
    object UnlockMethodScreen: Screen("unlock_method_screen")
    object ImportDataScreen: Screen("import_data_screen")
    object PrivacyAdviceScreen: Screen("privacy_advice_screen")
    object InfoAppScreen: Screen("info_app_screen")
    object ContactMeScreen: Screen("contact_me_screen")
    object SelectColorScreen: Screen("select_color_screen")
}