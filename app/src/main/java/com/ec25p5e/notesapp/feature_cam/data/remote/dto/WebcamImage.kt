package com.ec25p5e.notesapp.feature_cam.data.remote.dto

data class WebcamImage(
    val current: CurrentWebcamImage,
    val sizes: CurrentWebcamImageSize,
    val daylight: CurrentWebcamImageDaylight
)
