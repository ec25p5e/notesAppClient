package com.feature.webcam.presentation.components.webcam_map

import com.feature.webcam.domain.model.Category
import com.feature.webcam.domain.model.Continent
import com.feature.webcam.domain.model.Webcam
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType

data class WebcamMapState(
    val webcams: List<Webcam> = emptyList(),
    val continents: List<Continent> = emptyList(),
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = true,
    val isFiltering: Boolean = false,
    val isShowMap: Boolean = true,
    val properties: MapProperties = MapProperties(
        isBuildingEnabled = true,
        isMyLocationEnabled = true,
    ),
    val isFalloutMap: Boolean = false,
    val isMapDetailShowing: Boolean = false,
)
