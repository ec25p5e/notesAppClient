package com.feature.note.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(
    val userId: String = "",
    var name: String,
    val color: Int,
    val timestamp: Long,
    val remoteId: String = "",
    @ColumnInfo(name = "num_notes")
    var numNotesAssoc: Int = 0,
    @PrimaryKey val id: Int? = null
) {

    companion object {
        val categoryColor = listOf(null
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
