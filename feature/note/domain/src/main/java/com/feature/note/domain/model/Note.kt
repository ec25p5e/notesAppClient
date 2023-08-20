package com.feature.note.domain.model

import androidx.compose.ui.graphics.Path
import androidx.room.Entity
import androidx.room.PrimaryKey

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
        val noteColors = listOf(null
            /* RedOrange,
            LightGreen,
            Violet,
            BabyBlue,
            RedPink,
            At_Primary,
            At_Secondary,
            At_Third,
            Nt_Primary,
            Nt_Secondary,
            Nt_Third */
        )
    }
}

