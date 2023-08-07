package com.ec25p5e.notesapp.core.data.local.permission

import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.domain.repository.PermissionTextProvider

class CameraPermissionTextProvider: PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): Int {
        return if(isPermanentlyDeclined) {
            R.string.camera_permission_permanently_declined
        } else {
            R.string.camera_permission_text
        }
    }

    override fun getImage(): Int {
        return R.raw.allow_authentication
    }
}