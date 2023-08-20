package com.feature.webcam.data.mapper

import com.feature.webcam.data.remote.dto.CategoryCamDto
import com.feature.webcam.domain.model.Category
import com.feature.webcam.domain.model.CategoryCam

fun CategoryCamDto.toCategory(): Category {
    return Category(
        id = id,
        name = name
    )
}

fun CategoryCamDto.toCategoryCam(): CategoryCam {
    return CategoryCam(
        id = id,
        name = name,
    )
}

fun Category.toCategoryCam(): CategoryCamDto {
    return CategoryCamDto(
        id = id,
        name = name,
    )
}