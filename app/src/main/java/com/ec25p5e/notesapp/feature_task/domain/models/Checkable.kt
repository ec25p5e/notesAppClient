package com.ec25p5e.notesapp.feature_task.domain.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/* @Entity(foreignKeys = [
    ForeignKey(
        entity = Task::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("id"),
        onDelete = ForeignKey.CASCADE
    )
]) */
@Entity
data class Checkable(
    @PrimaryKey var id: Int? = null,
    var uid: Long = 0L,
    var value: String = "",
    var checked: Boolean = false,
    var created: Long = 0L,
    var updated: Long = 0L,
    var taskId: Int? = null,
)