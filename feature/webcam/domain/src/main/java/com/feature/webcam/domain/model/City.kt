package com.feature.webcam.domain.model

data class City(
    val city: String,
    val region: String,
    val country: String,
    val continent: String,
    val latitude: Double,
    val longitude: Double,
    val regionCode: String,
    val countryCode: String,
    val continentCode: String,
)