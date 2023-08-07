package com.ec25p5e.notesapp.feature_task.domain.models

import com.ec25p5e.notesapp.R

enum class TaskFilters(val text: Int) {
    Today(R.string.today_filter),
    Upcoming(R.string.upcoming_filter),
    Regular(R.string.regular_filter),
    CheckLists(R.string.checklist_filter),
    All(R.string.all_filter),
}