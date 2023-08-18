package com.ec25p5e.notesapp.feature_cam.data.mapper

import com.ec25p5e.notesapp.feature_cam.data.remote.dto.ContinentDto
import com.ec25p5e.notesapp.feature_cam.domain.model.Continent

fun ContinentDto.toContinents(): Continent {
    return Continent(
        code = code,
        name = name,
    )
}

fun Continent.toContinentsDto(): ContinentDto {
    return ContinentDto(
        code = code,
        name = name,
    )
}