package com.ec25p5e.notesapp.feature_cam.presentation.cam_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ec25p5e.notesapp.core.domain.states.StandardDropdownMenu
import com.ec25p5e.notesapp.core.presentation.util.UiEvent
import com.ec25p5e.notesapp.feature_cam.data.mapper.toWebcam
import com.ec25p5e.notesapp.feature_cam.domain.use_case.CameraUseCases
import com.ec25p5e.notesapp.feature_cam.presentation.components.MapStyle
import com.google.android.gms.maps.model.MapStyleOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CamListViewModel @Inject constructor(
    private val cameraUseCases: CameraUseCases
): ViewModel() {

    private val _continentState = mutableStateOf(StandardDropdownMenu())
    val continentState: State<StandardDropdownMenu> = _continentState

    private val _state = mutableStateOf(CamListState())
    val state: State<CamListState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        initCamera()
    }

    fun onEvent(event: CamListEvent) {
        when(event) {
            is CamListEvent.ContinentValueChange -> {
                _continentState.value = _continentState.value.copy(
                    text = event.value.name
                )
            }
            is CamListEvent.ToggleFilter -> {
                _state.value = state.value.copy(
                    isFiltering = !state.value.isFiltering
                )
            }
            is CamListEvent.ToggleFalloutMap -> {
                _state.value = state.value.copy(
                    properties = state.value.properties.copy(
                        mapStyleOptions = if(state.value.isFalloutMap) {
                            null
                        } else MapStyleOptions(MapStyle.json),
                    ),
                    isFalloutMap = !state.value.isFalloutMap
                )
            }
            is CamListEvent.OnInfoWindowLongClick -> {
                _state.value = state.value.copy(
                    isMapDetailShowing = true
                )
            }
            is CamListEvent.ToggleWebcamInfo -> {
                _state.value = state.value.copy(
                    isMapDetailShowing = !state.value.isMapDetailShowing
                )
            }
            else -> Unit
        }
    }


    private fun initCamera() {
        viewModelScope.launch {
            _state.value = state.value.copy(
                webcams = cameraUseCases.getCameraForRegions().webcams.map { it.toWebcam() },
                continents = cameraUseCases.getContinents(),
                categories = cameraUseCases.getCategories(),
                isLoading = false
            )
        }
    }
}