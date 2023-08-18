package com.ec25p5e.notesapp.feature_cam.presentation.cam_detail

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.graphics.ColorUtils
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.presentation.components.StandardToolbar
import com.ec25p5e.notesapp.core.presentation.ui.theme.SpaceMedium
import com.ec25p5e.notesapp.core.presentation.ui.theme.SpaceSmall
import com.ec25p5e.notesapp.core.presentation.util.UiEvent
import com.ec25p5e.notesapp.core.presentation.util.asString
import com.ec25p5e.notesapp.feature_note.presentation.add_edit_note.AddEditNoteEvent
import com.ec25p5e.notesapp.feature_note.presentation.components.CategoryItem
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun CameraDetailScreen(
    imageLoader: ImageLoader,
    scaffoldState: SnackbarHostState,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: CameraDetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.state.value
    val webcamDetail = viewModel.state.value.webcamDetail
    val cornerRadius = 10.dp
    val cutCornerSize = 30.dp
    val backgroundColorBoxLocation = -9450839
    val backgroundColorBoxInfoWebcam = -9450839
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

    if(state.isMapDetailShowing) {
        AlertDialog(
            onDismissRequest = {
                viewModel.onEvent(CameraDetailEvent.ToggleWebcamInfo)
            },
            icon = {
                Icon(painterResource(id = R.drawable.ic_category), contentDescription = null) },
            title = {
                Text(text = webcamDetail!!.title)
            },
            text = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.88f)
                        .padding(vertical = 20.dp)
                ) {
                    Canvas(modifier = Modifier.matchParentSize()) {
                        val clipPath = Path().apply {
                            lineTo(size.width - cutCornerSize.toPx(), 0f)
                            lineTo(size.width, cutCornerSize.toPx())
                            lineTo(size.width, size.height)
                            lineTo(0f, size.height)
                            close()
                        }

                        clipPath(clipPath) {
                            drawRoundRect(
                                color = Color(backgroundColorBoxLocation),
                                size = size,
                                cornerRadius = CornerRadius(cornerRadius.toPx())
                            )
                            drawRoundRect(
                                color = Color(
                                    ColorUtils.blendARGB(backgroundColorBoxLocation, 0x000000, 0.2f)
                                ),
                                topLeft = Offset(size.width - cutCornerSize.toPx(), -100f),
                                size = Size(cutCornerSize.toPx() + 100f, cutCornerSize.toPx() + 100f),
                                cornerRadius = CornerRadius(cornerRadius.toPx())
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .padding(end = 32.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.localization_info_text),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(SpaceSmall))

                        Text(
                            text = stringResource(id = R.string.city_label) + " " + webcamDetail!!.location.city,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(SpaceSmall))

                        Text(
                            text = stringResource(id = R.string.region_label) + " " + webcamDetail.location.region,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(SpaceSmall))

                        Text(
                            text = stringResource(id = R.string.region_code_label) + " " + webcamDetail.location.regionCode,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(SpaceSmall))

                        Text(
                            text = stringResource(id = R.string.country_label) + " " + webcamDetail.location.country,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(SpaceSmall))

                        Text(
                            text = stringResource(id = R.string.country_code_label) + " " + webcamDetail.location.countryCode,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(SpaceSmall))

                        Text(
                            text = stringResource(id = R.string.continent_label) + " " + webcamDetail.location.continent,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(SpaceSmall))

                        Text(
                            text = stringResource(id = R.string.continent_code_label) + " " + webcamDetail.location.continentCode,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(SpaceSmall))

                        Text(
                            text = stringResource(id = R.string.latitude_label) + " " + webcamDetail.location.latitude,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(SpaceSmall))

                        Text(
                            text = stringResource(id = R.string.longitude_label) + " " + webcamDetail.location.longitude,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.onEvent(CameraDetailEvent.ToggleWebcamInfo)
                    }
                ) {
                    Text(stringResource(id = R.string.close_btn_text))
                }
            },
            modifier = Modifier
                .fillMaxWidth()
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardToolbar(
            onNavigateUp = {
                onNavigateUp()
            },
            title = {
                if(!state.isLoading) {
                    Text(
                        text = webcamDetail!!.title,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth(),
            showBackArrow = true,
            navActions = {
            }
        )

        if(!state.isLoading) {
            Column(
                modifier = Modifier
                    .padding(SpaceMedium)
            ) {
                /* Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.3f),
                    painter = rememberAsyncImagePainter(
                        webcamDetail!!.images.current.preview
                    ),
                    contentDescription = null,
                ) */

                GoogleMap(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.4f),
                    properties = state.properties,
                    uiSettings = uiSettings,
                    onMapLongClick = {

                    }
                ) {
                    Marker(
                        position = LatLng(webcamDetail!!.location.latitude, webcamDetail!!.location.longitude),
                        title = webcamDetail!!.title,
                        snippet = stringResource(id = R.string.show_detail_webcam_info),
                        onInfoWindowLongClick = {
                            viewModel.onEvent(CameraDetailEvent.OnInfoWindowLongClick(webcamDetail))
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



                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.8f)
                        .padding(vertical = 20.dp)
                ) {
                    Canvas(modifier = Modifier.matchParentSize()) {
                        val clipPath = Path().apply {
                            lineTo(size.width - cutCornerSize.toPx(), 0f)
                            lineTo(size.width, cutCornerSize.toPx())
                            lineTo(size.width, size.height)
                            lineTo(0f, size.height)
                            close()
                        }

                        clipPath(clipPath) {
                            drawRoundRect(
                                color = Color(backgroundColorBoxInfoWebcam),
                                size = size,
                                cornerRadius = CornerRadius(cornerRadius.toPx())
                            )
                            drawRoundRect(
                                color = Color(
                                    ColorUtils.blendARGB(backgroundColorBoxInfoWebcam, 0x000000, 0.2f)
                                ),
                                topLeft = Offset(size.width - cutCornerSize.toPx(), -100f),
                                size = Size(cutCornerSize.toPx() + 100f, cutCornerSize.toPx() + 100f),
                                cornerRadius = CornerRadius(cornerRadius.toPx())
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .padding(end = 32.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.webcam_info_text),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(SpaceSmall))

                        Text(
                            text = stringResource(id = R.string.webcamId_label) + " " + webcamDetail!!.webcamId,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(SpaceSmall))

                        Text(
                            text = stringResource(id = R.string.viewcount_label) + " " + webcamDetail.viewCount,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(SpaceSmall))

                        Text(
                            text = stringResource(id = R.string.status_label) + " " + webcamDetail.status,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(SpaceSmall))

                        Text(
                            text = stringResource(id = R.string.lastupdate_on_label) + " " + webcamDetail.lastUpdatedOn,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}