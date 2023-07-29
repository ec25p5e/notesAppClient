package com.ec25p5e.notesapp.core.data.dto.response

data class BasicApiResponse<T>(
    val successful: Boolean,
    val message: String? = null,
    val data: T? = null
)