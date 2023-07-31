package com.ec25p5e.notesapp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(
    val name: String,
    val color: Int,
    @PrimaryKey val id: Int? = null
)
