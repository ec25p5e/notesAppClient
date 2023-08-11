package com.ec25p5e.notesapp.feature_task.domain.repository

import com.ec25p5e.notesapp.feature_task.domain.models.Checkable
import kotlinx.coroutines.flow.Flow

interface CheckableRepository {

    fun getCheckablesByTask(taskId: Int): List<Checkable>

    fun insertBulkCheckable(checkables: List<Checkable>)

    fun insertCheckable(checkable: Checkable)
}