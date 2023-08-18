package com.ec25p5e.notesapp.feature_cam.presentation.cam_list

import com.ec25p5e.notesapp.feature_cam.domain.model.Continent
import com.ec25p5e.notesapp.feature_cam.domain.model.Webcam
import com.google.android.gms.maps.model.LatLng

sealed class CamListEvent {
    data class ContinentValueChange(val value: Continent): CamListEvent()
    data class OnMapLongClick(val latLng: LatLng): CamListEvent()
    data class OnInfoWindowLongClick(val webcamDetail: Webcam): CamListEvent()
    data object ToggleFalloutMap: CamListEvent()
    data object ToggleFilter: CamListEvent()
    data object ToggleWebcamInfo: CamListEvent()
}