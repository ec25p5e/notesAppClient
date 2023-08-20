package com.feature.webcam.data.mapper

import com.feature.webcam.data.remote.dto.OverviewDto
import com.feature.webcam.domain.model.Webcam

fun OverviewDto.toWebcam(): Webcam {
    return Webcam(
        title = title,
        viewCount = viewCount,
        webcamId = webcamId,
        status = status,
        lastUpdatedOn = lastUpdatedOn,
        categories = categories?.map { it.toCategoryCam() },
        location = location.toCity(),
        images = images.toWebcamImage(),
        player = player.toPlayer(),
        urls = urls.toUrl(),
    )
}

