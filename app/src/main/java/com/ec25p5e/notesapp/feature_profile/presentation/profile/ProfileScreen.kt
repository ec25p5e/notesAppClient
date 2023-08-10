package com.ec25p5e.notesapp.feature_profile.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.domain.models.User
import com.ec25p5e.notesapp.core.presentation.ui.theme.ProfilePictureSizeLarge
import com.ec25p5e.notesapp.core.presentation.ui.theme.SpaceSmall
import com.ec25p5e.notesapp.core.util.Screen
import com.ec25p5e.notesapp.core.util.toPx
import com.ec25p5e.notesapp.feature_profile.presentation.components.BannerSection
import com.ec25p5e.notesapp.feature_profile.presentation.components.ProfileHeaderSection

@Composable
fun ProfileScreen(
    imageLoader: ImageLoader,
    scaffoldState: SnackbarHostState,
    userId: String? = null,
    onNavigate: (String) -> Unit = {},
    onLogout: () -> Unit = {},
    profilePictureSize: Dp = ProfilePictureSizeLarge,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val toolbarState = viewModel.toolbarState.value

    val iconHorizontalCenterLength =
        (LocalConfiguration.current.screenWidthDp.dp.toPx() / 4f -
                (profilePictureSize / 4f).toPx() -
                SpaceSmall.toPx()) / 2f
    val iconSizeExpanded = 35.dp
    val toolbarHeightCollapsed = 75.dp
    val imageCollapsedOffsetY = remember {
        (toolbarHeightCollapsed - profilePictureSize / 2f) / 2f
    }
    val iconCollapsedOffsetY = remember {
    (toolbarHeightCollapsed - iconSizeExpanded) / 2f
    }
    val bannerHeight = (LocalConfiguration.current.screenWidthDp / 2.5f).dp
    val toolbarHeightExpanded = remember {
        bannerHeight + profilePictureSize
    }
    val maxOffset = remember {
        toolbarHeightExpanded - toolbarHeightCollapsed
    }

    LaunchedEffect(key1 = true) {
        viewModel.getProfile(userId)
    }

    if(state.profile != null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item {
                    Spacer(
                        modifier = Modifier.height(
                            toolbarHeightExpanded - profilePictureSize / 2f
                        )
                    )
                }
                item {
                    state.profile?.let { profile ->
                        ProfileHeaderSection(
                            user = User(
                                userId = profile.userId,
                                username = profile.username,
                                email = profile.email,
                                profilePictureUrl = profile.profilePictureUrl
                            ),
                            onLogoutClick = {
                                viewModel.onEvent(ProfileEvent.ShowLogoutDialog)
                            },
                            onEditClick = {
                                onNavigate(Screen.EditProfileScreen.route + "/${profile.userId}")
                            }
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
            ) {
                state.profile?.let { profile ->
                    BannerSection(
                        modifier = Modifier
                            .height(
                                (bannerHeight * toolbarState.expandedRatio).coerceIn(
                                    minimumValue = toolbarHeightCollapsed,
                                    maximumValue = bannerHeight
                                )
                            ),
                        imageLoader = imageLoader
                    )

                    Image(
                        painter = rememberAsyncImagePainter(
                            model = profile.profilePictureUrl,
                            imageLoader = imageLoader
                        ),
                        contentDescription = stringResource(id = R.string.profile_image),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .graphicsLayer {
                                translationY = -profilePictureSize.toPx() / 2f -
                                        (1f - toolbarState.expandedRatio) * imageCollapsedOffsetY.toPx()
                                transformOrigin = TransformOrigin(
                                    pivotFractionX = 0.5f,
                                    pivotFractionY = 0f
                                )
                                val scale = 0.5f + toolbarState.expandedRatio * 0.5f
                                scaleX = scale
                                scaleY = scale
                            }
                            .size(profilePictureSize)
                            .clip(CircleShape)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colors.onSurface,
                                shape = CircleShape
                            )
                    )
                }
            }
        }


        /* Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
        ) {
            Button(
                onClick = {
                    onNavigate(Screen.SettingsScreen.route)
                }
            ) {
                Text("Open settings")
            }

        } */
    }
}