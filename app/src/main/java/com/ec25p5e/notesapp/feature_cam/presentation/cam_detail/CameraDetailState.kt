package com.ec25p5e.notesapp.feature_cam.presentation.cam_detail

import com.ec25p5e.notesapp.feature_cam.domain.model.Webcam
import com.google.maps.android.compose.MapProperties

data class CameraDetailState(
    val isLoading: Boolean = true,
    val webcamDetail: Webcam? = null,
    val properties: MapProperties = MapProperties(),
    val isFalloutMap: Boolean = false,
    val isMapDetailShowing: Boolean = false,
)