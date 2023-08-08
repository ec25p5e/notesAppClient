package com.ec25p5e.notesapp.core.util

sealed class Screen(val route: String) {

    data object SplashScreen: Screen("splash_screen")
    data object LoginScreen: Screen("login_screen")
    data object RegisterScreen: Screen("register_screen")
    data object OnBoardingScreen: Screen("onboarding_screen")
    data object ProfileScreen: Screen("profile_screen")
    data object NotesScreen: Screen("notes_screen")
    data object CreateNoteScreen: Screen("create_note_screen")
    data object TodoScreen: Screen("todo_screen")
    data object ArchiveScreen: Screen("archive_screen")
    data object CategoryScreen: Screen("category_screen")
    data object SettingsScreen: Screen("settings_screen")
    data object SelectThemeScreen: Screen("select_theme_screen")
    data object UnlockMethodScreen: Screen("unlock_method_screen")
    data object SyncToServer: Screen("import_data_screen")
    data object PrivacyAdviceScreen: Screen("privacy_advice_screen")
    data object InfoAppScreen: Screen("info_app_screen")
    data object ContactMeScreen: Screen("contact_me_screen")
    data object AddEditTaskScreen: Screen("add_edit_task_screen")
    data object OfflineScreen: Screen("offline_screen")
    data object EditProfileScreen: Screen("edit_profile_screen")
}