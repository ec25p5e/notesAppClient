package com.ec25p5e.notesapp.feature_task.domain.use_cases.checkable

import com.ec25p5e.notesapp.feature_task.domain.models.Checkable
import com.ec25p5e.notesapp.feature_task.domain.repository.CheckableRepository
import kotlinx.coroutines.flow.Flow

class GetCheckableByTask(
    private val repository: CheckableRepository
) {

    operator fun invoke(taskId: Int): List<Checkable> {
        return repository.getCheckablesByTask(taskId)
    }
}