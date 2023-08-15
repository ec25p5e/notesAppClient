package com.ec25p5e.notesapp.feature_calc.domain.models

import com.ec25p5e.notesapp.feature_calc.data.local.WriteResult

data class WriteResultData(
    val description: String,
    val result: WriteResult
)
