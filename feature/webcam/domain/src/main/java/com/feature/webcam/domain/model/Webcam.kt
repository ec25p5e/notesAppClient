package com.feature.webcam.domain.model

data class Webcam(
    val title: String,
    val viewCount: Int,
    val webcamId: Int,
    val status: String,
    val lastUpdatedOn: String,
    val categories: List<CategoryCam>?,
    val location: City,
    val images: WebcamImage,
    val player: Player,
    val urls: Url
)