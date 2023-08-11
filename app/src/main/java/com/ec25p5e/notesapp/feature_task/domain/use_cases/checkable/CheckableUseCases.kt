package com.ec25p5e.notesapp.feature_task.domain.use_cases.checkable

data class CheckableUseCases(
    val addCheckableList: AddCheckableList,
    val addCheckable: AddCheckable,
    val getCheckableByTask: GetCheckableByTask
)