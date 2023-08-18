package com.ec25p5e.notesapp.feature_cam.data.remote.dto

import com.google.gson.annotations.SerializedName

data class City(
    val city: String,
    val region: String,
    val country: String,
    val continent: String,
    val latitude: Double,
    val longitude: Double,

    @SerializedName("region_code") val regionCode: String,
    @SerializedName("country_code") val countryCode: String,
    @SerializedName("continent_code") val continentCode: String,
)