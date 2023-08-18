package com.ec25p5e.notesapp.feature_cam.presentation.cam_list

import com.ec25p5e.notesapp.feature_cam.domain.model.Category
import com.ec25p5e.notesapp.feature_cam.domain.model.Continent
import com.ec25p5e.notesapp.feature_cam.domain.model.Webcam
import com.google.maps.android.compose.MapProperties

data class CamListState(
    val webcams: List<Webcam> = emptyList(),
    val continents: List<Continent> = emptyList(),
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = true,
    val isFiltering: Boolean = false,
    val isShowMap: Boolean = true,
    val properties: MapProperties = MapProperties(),
    val isFalloutMap: Boolean = false,
    val isMapDetailShowing: Boolean = false,
)
