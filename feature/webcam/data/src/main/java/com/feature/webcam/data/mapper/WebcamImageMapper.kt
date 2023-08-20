package com.feature.webcam.data.mapper

import com.feature.webcam.data.remote.dto.CurrentWebcamImage
import com.feature.webcam.data.remote.dto.CurrentWebcamImageDaylight
import com.feature.webcam.data.remote.dto.CurrentWebcamImageSize
import com.feature.webcam.data.remote.dto.ImageSizeWebcam
import com.feature.webcam.data.remote.dto.WebcamImage

fun WebcamImage.toWebcamImage(): com.feature.webcam.domain.model.WebcamImage {
    return com.feature.webcam.domain.model.WebcamImage(
        current = current.toCurrentWebcamImage(),
        sizes = sizes.toCurrentWebcamSizes(),
        daylight = daylight.toCurrentWebcamDaylight()
    )
}

fun CurrentWebcamImage.toCurrentWebcamImage(): com.feature.webcam.domain.model.CurrentWebcamImage {
    return com.feature.webcam.domain.model.CurrentWebcamImage(
        icon = icon,
        thumbnail = thumbnail,
        preview = preview
    )
}

fun CurrentWebcamImageSize.toCurrentWebcamSizes(): com.feature.webcam.domain.model.CurrentWebcamImageSize {
    return com.feature.webcam.domain.model.CurrentWebcamImageSize(
        icon = icon.toImageSizeWebcam(),
        thumbnail = thumbnail.toImageSizeWebcam(),
        preview = preview.toImageSizeWebcam(),
    )
}

fun ImageSizeWebcam.toImageSizeWebcam(): com.feature.webcam.domain.model.ImageSizeWebcam {
    return com.feature.webcam.domain.model.ImageSizeWebcam(
        width = width,
        height = height,
    )
}

fun CurrentWebcamImageDaylight.toCurrentWebcamDaylight(): com.feature.webcam.domain.model.CurrentWebcamImageDaylight {
    return com.feature.webcam.domain.model.CurrentWebcamImageDaylight(
        icon = icon,
        thumbnail = thumbnail,
        preview = preview,
    )
}