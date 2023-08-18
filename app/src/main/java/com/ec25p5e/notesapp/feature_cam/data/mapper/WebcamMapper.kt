package com.ec25p5e.notesapp.feature_cam.data.mapper

import com.ec25p5e.notesapp.feature_cam.data.remote.dto.OverviewDto
import com.ec25p5e.notesapp.feature_cam.domain.model.Webcam

fun OverviewDto.toWebcam(): Webcam {
    return Webcam(
        title = title,
        viewCount = viewCount,
        webcamId = webcamId,
        status = status,
        lastUpdatedOn = lastUpdatedOn,
        categories = categories,
        location = location,
        images = images,
        player = player,
        urls = urls,
    )
}