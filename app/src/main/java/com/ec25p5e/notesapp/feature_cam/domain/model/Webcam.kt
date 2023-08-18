package com.ec25p5e.notesapp.feature_cam.domain.model

import com.ec25p5e.notesapp.feature_cam.data.remote.dto.CategoryCamDto
import com.ec25p5e.notesapp.feature_cam.data.remote.dto.City
import com.ec25p5e.notesapp.feature_cam.data.remote.dto.Player
import com.ec25p5e.notesapp.feature_cam.data.remote.dto.Url
import com.ec25p5e.notesapp.feature_cam.data.remote.dto.WebcamImage

data class Webcam(
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