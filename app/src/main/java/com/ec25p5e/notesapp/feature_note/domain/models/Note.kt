package com.ec25p5e.notesapp.feature_note.domain.models

import androidx.compose.ui.graphics.Path
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ec25p5e.notesapp.core.presentation.ui.theme.At_Primary
import com.ec25p5e.notesapp.core.presentation.ui.theme.At_Secondary
import com.ec25p5e.notesapp.core.presentation.ui.theme.At_Third
import com.ec25p5e.notesapp.core.presentation.ui.theme.BabyBlue
import com.ec25p5e.notesapp.core.presentation.ui.theme.LightGreen
import com.ec25p5e.notesapp.core.presentation.ui.theme.Nt_Primary
import com.ec25p5e.notesapp.core.presentation.ui.theme.Nt_Secondary
import com.ec25p5e.notesapp.core.presentation.ui.theme.Nt_Third
import com.ec25p5e.notesapp.core.presentation.ui.theme.RedOrange
import com.ec25p5e.notesapp.core.presentation.ui.theme.RedPink
import com.ec25p5e.notesapp.core.presentation.ui.theme.Violet

@Entity
data class Note(
    var title: String,
    var content: String,
    val timestamp: Long,
    val color: Int,
    val isArchived: Boolean = false,
    val categoryId: Int = 1,
    val remoteId: String = "",
    var image: ArrayList<String> = ArrayList(),
    val background: Int,
    val isCopied: Int = 0,
    val isLocked: Boolean = false,
    val imagePath: List<Pair<Path, PathProperties>>? = null,
    @PrimaryKey val id: Int? = null
) {

    companion object {
        val noteColors = listOf(
            RedOrange,
            LightGreen,
            Violet,
            BabyBlue,
            RedPink,
            At_Primary,
            At_Secondary,
            At_Third,
            Nt_Primary,
            Nt_Secondary,
            Nt_Third
        )
    }
}

