package com.feature.webcam.data.remote.dto

data class OverviewDto(
    val title: String,
    val viewCount: Int,
    val webcamId: Int,
    val status: String,
    val lastUpdatedOn: String,
    val categories: List<CategoryCamDto>?,
    val location: City,
    val images: WebcamImage,
    val player: Player,
    val urls: Url
)