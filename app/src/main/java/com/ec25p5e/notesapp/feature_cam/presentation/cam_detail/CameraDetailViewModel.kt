package com.ec25p5e.notesapp.feature_cam.presentation.cam_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ec25p5e.notesapp.core.presentation.util.UiEvent
import com.ec25p5e.notesapp.feature_cam.data.mapper.toWebcam
import com.ec25p5e.notesapp.feature_cam.domain.use_case.CameraUseCases
import com.ec25p5e.notesapp.feature_cam.presentation.components.MapStyle
import com.google.android.gms.maps.model.MapStyleOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraDetailViewModel @Inject constructor(
    private val cameraUseCases: CameraUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = mutableStateOf(CameraDetailState())
    val state: State<CameraDetailState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentWebcamId: Int? = null

    init {
        savedStateHandle.get<Int>("webcamId")?.let { webcamId ->
            if(webcamId != -1) {
                initCameraDetail(webcamId)
            }
        }
    }

    fun onEvent(event: CameraDetailEvent) {
        when(event) {
            is CameraDetailEvent.ToggleFalloutMap -> {
                _state.value = state.value.copy(
                    properties = state.value.properties.copy(
                        mapStyleOptions = if(state.value.isFalloutMap) {
                            null
                        } else MapStyleOptions(MapStyle.json),
                    ),
                    isFalloutMap = !state.value.isFalloutMap
                )
            }
            is CameraDetailEvent.OnInfoWindowLongClick -> {
                _state.value = state.value.copy(
                    isMapDetailShowing = true
                )
            }
            is CameraDetailEvent.ToggleWebcamInfo -> {
                _state.value = state.value.copy(
                    isMapDetailShowing = !state.value.isMapDetailShowing
                )
            }
            else -> Unit
        }
    }

    private fun initCameraDetail(webcamId: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                webcamDetail = cameraUseCases.getCameraDetail(webcamId).toWebcam(),
                isLoading = false,
            )
        }
    }
}