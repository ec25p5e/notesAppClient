package com.ec25p5e.notesapp.core.domain.repository

import com.ec25p5e.notesapp.core.domain.models.AlarmItem

interface AlarmScheduler {

    fun schedule(item: AlarmItem)
    fun cancel(item: AlarmItem)
}