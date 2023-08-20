package com.feature.webcam.data.mapper

import com.feature.webcam.data.remote.dto.ContinentDto
import com.feature.webcam.domain.model.Continent

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