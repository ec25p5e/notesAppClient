package com.ec25p5e.notesapp.feature_cam.presentation.cam_map

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ec25p5e.notesapp.core.presentation.util.UiEvent
import com.ec25p5e.notesapp.feature_cam.data.mapper.toWebcam
import com.ec25p5e.notesapp.feature_cam.domain.use_case.CameraUseCases
import com.ec25p5e.notesapp.feature_cam.presentation.cam_detail.CameraDetailEvent
import com.ec25p5e.notesapp.feature_cam.presentation.components.MapStyle
import com.google.android.gms.maps.model.MapStyleOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraMapViewModel @Inject constructor(
    private val cameraUseCases: CameraUseCases
): ViewModel() {

    private val _state = mutableStateOf(CameraMapState())
    val state: State<CameraMapState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        initCameraMap()
    }

    fun onEvent(event: CameraMapEvent) {
        when(event) {
            is CameraMapEvent.ToggleFalloutMap -> {
                _state.value = state.value.copy(
                    properties = state.value.properties.copy(
                        mapStyleOptions = if(state.value.isFalloutMap) {
                            null
                        } else MapStyleOptions(MapStyle.json),
                    ),
                    isFalloutMap = !state.value.isFalloutMap
                )
            }
            is CameraMapEvent.OnInfoWindowLongClick -> {
                _state.value = state.value.copy(
                    isMapDetailShowing = true
                )
            }
            is CameraMapEvent.ToggleWebcamInfo -> {
                _state.value = state.value.copy(
                    isMapDetailShowing = !state.value.isMapDetailShowing
                )
            }
            else -> Unit
        }
    }

    private fun initCameraMap() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                webcams = cameraUseCases.getCameraForGlobalMap(),
            )
        }
    }
}