package com.ec25p5e.notesapp.core.domain.models

import com.ec25p5e.notesapp.core.data.local.nfc.WriteResult

data class WriteResultData(
    val description: String,
    val result: WriteResult
)
