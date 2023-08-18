package com.ec25p5e.notesapp.feature_cam.presentation.cam_map

import com.ec25p5e.notesapp.feature_cam.domain.model.Webcam
import com.google.maps.android.compose.MapProperties

data class CameraMapState(
    val webcams: List<Webcam> = emptyList(),
    val properties: MapProperties = MapProperties(),
    val isFalloutMap: Boolean = false,
    val isMapDetailShowing: Boolean = false,
)
