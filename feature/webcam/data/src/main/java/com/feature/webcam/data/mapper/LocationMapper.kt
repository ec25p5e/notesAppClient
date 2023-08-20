package com.feature.webcam.data.mapper

import com.feature.webcam.data.remote.dto.City

fun City.toCity(): com.feature.webcam.domain.model.City {
    return com.feature.webcam.domain.model.City(
        city = city,
        region = region,
        country = country,
        continent = continent,
        latitude = latitude,
        longitude = longitude,
        regionCode = regionCode,
        countryCode = countryCode,
        continentCode = continentCode,
    )
}