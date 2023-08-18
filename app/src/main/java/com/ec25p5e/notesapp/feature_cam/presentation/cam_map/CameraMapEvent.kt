package com.ec25p5e.notesapp.feature_cam.presentation.cam_map

import com.ec25p5e.notesapp.feature_cam.domain.model.Webcam
import com.ec25p5e.notesapp.feature_cam.presentation.cam_detail.CameraDetailEvent
import com.google.android.gms.maps.model.LatLng

sealed class CameraMapEvent {
    data object ToggleFalloutMap: CameraMapEvent()
    data class OnMapLongClick(val latLng: LatLng): CameraMapEvent()
    data class OnInfoWindowLongClick(val webcamDetail: Webcam): CameraMapEvent()
    data object ToggleWebcamInfo: CameraMapEvent()
}
