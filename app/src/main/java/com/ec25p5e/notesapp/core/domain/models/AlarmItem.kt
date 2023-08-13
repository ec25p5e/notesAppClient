package com.ec25p5e.notesapp.core.domain.models

import java.time.LocalDateTime

data class AlarmItem(
    val alarmId: Long,
    val time: LocalDateTime,
    val message: String,
)