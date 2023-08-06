package com.ec25p5e.notesapp.feature_task.domain.use_cases.checkable

import com.ec25p5e.notesapp.feature_task.domain.models.Checkable
import com.ec25p5e.notesapp.feature_task.domain.repository.CheckableRepository

class AddCheckable(
    private val repository: CheckableRepository
) {

    operator fun invoke(checkable: Checkable) {
        repository.insertCheckable(checkable)
    }
}