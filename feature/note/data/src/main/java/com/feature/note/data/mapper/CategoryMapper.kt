package com.feature.note.data.mapper

import com.feature.note.data.remote.request.CreateCategoryRequest
import com.feature.note.data.remote.response.CategoryResponse
import com.feature.note.domain.model.Category

fun CategoryResponse.toCategory(): Category {
    return Category(
        name = name,
        color = color,
        timestamp = timestamp,
        remoteId = remoteId,
        userId = userId,
        id = localId.toInt(),
        numNotesAssoc = numNotesAssoc
    )
}

fun Category.toCreateCategoryRequest(): CreateCategoryRequest {
    return CreateCategoryRequest(
        localId = id.toString(),
        remoteId = remoteId,
        name = name,
        timestamp = timestamp,
        color = color,
        userId = userId,
        numNotesAssoc = numNotesAssoc
    )
}