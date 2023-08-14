package com.ec25p5e.notesapp.core.util

object Constants {

    const val MAX_NOTE_DESCRIPTION_LENGTH: Int = 8000
    const val MAX_TASK_DESCRIPTION_LENGTH: Int = 1000
    const val MIN_NOTE_TITLE_LENGTH: Int = 3
    const val MIN_TASK_TITLE_LENGTH: Int = 3
    const val MAX_NOTE_TITLE_LENGTH: Int = 20
    const val MAX_PIN_LENGTH = 8
    const val MAX_CATEGORY_TITLE_LENGTH = 16
    const val MIN_CATEGORY_TITLE_LENGTH = 3
    const val MAX_TASK_TITLE_LENGTH = 16
    const val SPLASH_SCREEN_DURATION = 2000L

    const val MAX_POST_DESCRIPTION_LINES = 3

    const val MAX_EMAIL_LENGTH = 128
    const val MIN_USERNAME_LENGTH = 3
    const val MAX_USERNAME_LENGTH = 16
    const val MIN_PASSWORD_LENGTH = 3
    const val MAX_PASSWORD_LENGTH = 48

    const val DEFAULT_PAGE_SIZE = 20

    const val DATA_STORE_FILE_NAME = "app-settings.json"
    const val BASE_URL_SERVER = "http://192.168.63.107:8080/"

    const val RECONNECT_INTERVAL = 5000L
    const val MAX_IMAGE_PAR_NOTE = 3
}