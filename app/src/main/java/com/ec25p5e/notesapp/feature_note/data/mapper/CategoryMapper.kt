package com.ec25p5e.notesapp.feature_note.data.mapper

import com.ec25p5e.notesapp.feature_note.data.remote.request.CreateCategoryRequest
import com.ec25p5e.notesapp.feature_note.data.remote.response.CategoryResponse
import com.ec25p5e.notesapp.feature_note.domain.models.Category

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