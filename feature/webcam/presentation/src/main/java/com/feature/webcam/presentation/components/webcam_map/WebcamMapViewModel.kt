package com.feature.webcam.presentation.components.webcam_map

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.feature.webcam.domain.use_cases.WebcamUseCases
import com.feature.webcam.presentation.components.MapStyle
import com.google.android.gms.maps.model.MapStyleOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WebcamMapViewModel @Inject constructor(
    private val webcamUseCases: WebcamUseCases
) : ViewModel() {

    private val _state = mutableStateOf(WebcamMapState())
    val state: State<WebcamMapState> = _state

    fun onEvent(event: WebcamMapEvent) {
        when(event) {
            is WebcamMapEvent.ContinentValueChange -> {
                /* _continentState.value = _continentState.value.copy(
                    text = event.value.name
                ) */
            }
            is WebcamMapEvent.ToggleFilter -> {
                _state.value = state.value.copy(
                    isFiltering = !state.value.isFiltering
                )
            }
            is WebcamMapEvent.ToggleFalloutMap -> {
                _state.value = state.value.copy(
                    properties = state.value.properties.copy(
                        mapStyleOptions = if(state.value.isFalloutMap) {
                            null
                        } else MapStyleOptions(MapStyle.json),
                    ),
                    isFalloutMap = !state.value.isFalloutMap
                )
            }
            is WebcamMapEvent.OnInfoWindowLongClick -> {
                _state.value = state.value.copy(
                    isMapDetailShowing = true
                )
            }
            is WebcamMapEvent.ToggleWebcamInfo -> {
                _state.value = state.value.copy(
                    isMapDetailShowing = !state.value.isMapDetailShowing
                )
            }
            else -> Unit
        }
    }
}