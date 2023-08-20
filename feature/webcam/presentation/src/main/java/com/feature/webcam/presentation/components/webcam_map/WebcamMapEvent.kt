package com.feature.webcam.presentation.components.webcam_map

import com.google.android.gms.maps.model.LatLng
import com.feature.webcam.domain.model.Continent
import com.feature.webcam.domain.model.Webcam

sealed class WebcamMapEvent {

    data class ContinentValueChange(val value: Continent): WebcamMapEvent()
    data class OnMapLongClick(val latLng: LatLng): WebcamMapEvent()
    data class OnInfoWindowLongClick(val webcamDetail: Webcam): WebcamMapEvent()
    data object ToggleFalloutMap: WebcamMapEvent()
    data object ToggleFilter: WebcamMapEvent()
    data object ToggleWebcamInfo: WebcamMapEvent()
}
