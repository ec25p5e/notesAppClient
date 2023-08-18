package com.ec25p5e.notesapp.feature_cam.presentation.cam_detail

import com.ec25p5e.notesapp.feature_cam.domain.model.Webcam
import com.google.android.gms.maps.model.LatLng


sealed class CameraDetailEvent {
    data object ToggleFalloutMap: CameraDetailEvent()
    data class OnMapLongClick(val latLng: LatLng): CameraDetailEvent()
    data class OnInfoWindowLongClick(val webcamDetail: Webcam): CameraDetailEvent()
    data object ToggleWebcamInfo: CameraDetailEvent()
}
