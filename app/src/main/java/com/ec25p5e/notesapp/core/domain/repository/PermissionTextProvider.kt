package com.ec25p5e.notesapp.core.domain.repository

interface PermissionTextProvider {
    fun getDescription(isPermanentlyDeclined: Boolean): Int

    fun getImage(): Int
}