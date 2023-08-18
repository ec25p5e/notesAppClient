package com.ec25p5e.notesapp.feature_cam.data.mapper

import com.ec25p5e.notesapp.feature_cam.data.remote.dto.CategoryCamDto
import com.ec25p5e.notesapp.feature_cam.domain.model.Category

fun CategoryCamDto.toCategory(): Category {
    return Category(
        id = id,
        name = name
    )
}

fun Category.toCategoryCam(): CategoryCamDto {
    return CategoryCamDto(
        id = id,
        name = name,
    )
}