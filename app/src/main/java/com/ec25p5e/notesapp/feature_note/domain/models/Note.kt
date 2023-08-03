package com.ec25p5e.notesapp.feature_note.domain.models

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.ec25p5e.notesapp.core.presentation.ui.theme.BabyBlue
import com.ec25p5e.notesapp.core.presentation.ui.theme.LightGreen
import com.ec25p5e.notesapp.core.presentation.ui.theme.RedOrange
import com.ec25p5e.notesapp.core.presentation.ui.theme.RedPink
import com.ec25p5e.notesapp.core.presentation.ui.theme.Violet

@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    val isArchived: Boolean = false,
    val categoryId: Int = 1,
    val remoteId: String = "",
    val image: String? = "",
    @PrimaryKey val id: Int? = null
) {

    companion object {
        val noteColors = listOf(
            RedOrange,
            LightGreen,
            Violet,
            BabyBlue,
            RedPink
        )
    }
}

