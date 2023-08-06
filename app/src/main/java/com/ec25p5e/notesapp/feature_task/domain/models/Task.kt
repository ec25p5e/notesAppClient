package com.ec25p5e.notesapp.feature_task.domain.models

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
data class Task(
    @PrimaryKey val id: Int? = null,
    val uid: Long = 0L,
    val title: String,
    val description: String,
    val dueDateTime: String = "",
    val done: Boolean = false,
    val created: Long,
    val updated: Long,
) {

    companion object {
        val taskColors = listOf(
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