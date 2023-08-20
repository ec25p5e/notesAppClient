package com.feature.webcam.data.remote.dto

data class WebcamImage(
    val current: CurrentWebcamImage,
    val sizes: CurrentWebcamImageSize,
    val daylight: CurrentWebcamImageDaylight
)
