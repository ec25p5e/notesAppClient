package com.ec25p5e.notesapp.feature_cam.presentation.cam_list

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.presentation.components.StandardOptionsMenu
import com.ec25p5e.notesapp.core.presentation.components.StandardToolbar
import com.ec25p5e.notesapp.core.presentation.util.UiEvent
import com.ec25p5e.notesapp.core.presentation.util.asString
import com.ec25p5e.notesapp.core.util.Screen
import com.ec25p5e.notesapp.feature_cam.presentation.components.CamlistItem
import com.ec25p5e.notesapp.feature_crypto.presentation.components.CoinListItem
import com.ec25p5e.notesapp.feature_note.presentation.notes.NotesEvent
import com.ec25p5e.notesapp.feature_note.presentation.util.UiEventNote
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CamListScreen(
    imageLoader: ImageLoader,
    scaffoldState: SnackbarHostState,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: CamListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val continentState = viewModel.continentState.value
    val webcams = viewModel.state.value.webcams
    val context = LocalContext.current
    var isExpandedContinent by remember {
        mutableStateOf(false)
    }
    val uiSettings = remember {
        MapUiSettings(
            zoomControlsEnabled = true
        )
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is UiEvent.ShowSnackbar -> {
                    Toast.makeText(
                        context,
                        event.uiText!!.asString(context),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is UiEvent.Navigate -> {
                    onNavigate(event.route)
                }
                else -> {
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        StandardToolbar(
            onNavigateUp = onNavigateUp,
            title = {
                Text(
                    text = if(state.isFiltering) {
                        stringResource(id = R.string.filter_webcams)
                    } else{
                         stringResource(id = R.string.all_webcams_by_filter)
                    },
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = false,
            navActions = {
                StandardOptionsMenu(
                    menuItem = {
                        DropdownMenuItem(
                            text = {
                                Text(stringResource(id = R.string.show_filter_webcams))
                            },
                            onClick = {
                                viewModel.onEvent(CamListEvent.ToggleFilter)
                            },
                            leadingIcon = {
                                Icon(
                                    painterResource(id = R.drawable.ic_filter),
                                    contentDescription = stringResource(id = R.string.show_filter_webcams)
                                )
                            }
                        )
                    }
                )
            }
        )

        if(state.isFiltering) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterHorizontally)
            ) {
                ExposedDropdownMenuBox(
                    expanded = isExpandedContinent,
                    onExpandedChange = {
                        isExpandedContinent = it
                    }
                ) {
                    TextField(
                        value = continentState.text,
                        placeholder = {
                            Text(text = stringResource(id = R.string.continent_label))
                        },
                        onValueChange = { },
                        readOnly = true,
                        isError = continentState.error != null,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpandedContinent)
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier.menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = isExpandedContinent,
                        onDismissRequest = { isExpandedContinent = false }
                    ) {
                        state.continents.forEach { continent ->
                            DropdownMenuItem(
                                text = {
                                    Text(text = continent.name)
                                },
                                onClick = {
                                    isExpandedContinent = false
                                    viewModel.onEvent(CamListEvent.ContinentValueChange(continent))
                                }
                            )
                        }

                    }
                }

                ExposedDropdownMenuBox(
                    expanded = isExpandedContinent,
                    onExpandedChange = {
                        isExpandedContinent = it
                    }
                ) {
                    TextField(
                        value = continentState.text,
                        placeholder = {
                            Text(text = stringResource(id = R.string.continent_label))
                        },
                        onValueChange = {

                        },
                        readOnly = false,
                        isError = continentState.error != null,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpandedContinent)
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier.menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = isExpandedContinent,
                        onDismissRequest = { isExpandedContinent = false }
                    ) {
                        state.continents.forEach { continent ->
                            DropdownMenuItem(
                                text = {
                                    Text(text = (continent.name + " (" + continent.code + ")"))
                                },
                                onClick = {
                                    isExpandedContinent = false
                                    viewModel.onEvent(CamListEvent.ContinentValueChange(continent))
                                }
                            )
                        }

                    }
                }
            }
        } else if(state.isShowMap) {
            GoogleMap(
                modifier = Modifier
                    .fillMaxSize(),
                properties = state.properties,
                uiSettings = uiSettings,
                onMapLongClick = {

                }
            ) {
                webcams.forEach { webcam ->
                    Marker(
                        position = LatLng(webcam.location.latitude, webcam.location.longitude),
                        title = webcam.title,
                        snippet = stringResource(id = R.string.show_detail_webcam_info),
                        onInfoWindowLongClick = {
                            viewModel.onEvent(CamListEvent.OnInfoWindowLongClick(webcam))
                        },
                        onClick = {
                            it.showInfoWindow()
                            true
                        },
                        icon = BitmapDescriptorFactory.defaultMarker(
                            BitmapDescriptorFactory.HUE_BLUE
                        )
                    )
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.webcams) { webcam ->
                        CamlistItem(
                            camera = webcam,
                            onItemClick = {
                                onNavigate(Screen.CameraDetailScreen.route + "?webcamId=${webcam.webcamId}")
                            }
                        )
                    }
                }
            }
        }
    }
}