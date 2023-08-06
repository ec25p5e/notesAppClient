package com.ec25p5e.notesapp.feature_task.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Checkable(
    @PrimaryKey val id: Int? = null,
    val value: String = "",
    val checked: Boolean = false,
    val created: Long,
    val updated: Long,
)