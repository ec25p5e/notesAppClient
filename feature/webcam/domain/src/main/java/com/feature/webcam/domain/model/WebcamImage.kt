package com.feature.webcam.domain.model

data class WebcamImage(
    val current: CurrentWebcamImage,
    val sizes: CurrentWebcamImageSize,
    val daylight: CurrentWebcamImageDaylight
)
