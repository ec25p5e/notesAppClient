package com.ec25p5e.notesapp.feature_auth.presentation.onboarding

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.data.local.permission.CameraPermissionTextProvider
import com.ec25p5e.notesapp.core.data.local.permission.ReadStoragePermissionTextProvider
import com.ec25p5e.notesapp.core.data.local.permission.RecordAudioPermissionTextProvider
import com.ec25p5e.notesapp.core.presentation.components.PermissionDialog
import com.ec25p5e.notesapp.core.presentation.util.LottieView
import com.ec25p5e.notesapp.core.presentation.util.UiEvent
import com.ec25p5e.notesapp.core.util.Screen
import com.ec25p5e.notesapp.core.util.openSettings
import com.ec25p5e.notesapp.feature_auth.presentation.util.OnBoardingPage
import com.google.accompanist.pager.*
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalPagerApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun OnBoardingScreen(
    scaffoldState: SnackbarHostState,
    imageLoader: ImageLoader,
    onNavigate: (String) -> Unit = {},
    viewModel: OnBoardingViewModel = hiltViewModel()
) {
    val permissionsToRequest = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
    )
    val activity = LocalContext.current as Activity
    val dialogQueue = viewModel.visiblePermissionDialogQueue
    val context = LocalContext.current
    val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            permissionsToRequest.forEach { permission ->
                viewModel.onEvent(OnBoardingEvent.PermissionResult(
                    permission = permission,
                    isGranted = perms[permission] == true
                ))
            }
        }
    )
    val pages = listOf(
        OnBoardingPage.FeatureNote,
        OnBoardingPage.TodoFeature,
        OnBoardingPage.ProfileFeature,
        OnBoardingPage.AllowPermission
    )
    val pagerState = rememberPagerState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is UiEvent.Navigate -> {
                    onNavigate(event.route)
                }
                else -> Unit
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            modifier = Modifier.weight(10f),
            count = 4,
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) { position ->
            PagerScreen(onBoardingPage = pages[position])
        }
        HorizontalPagerIndicator(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .weight(1f),
            pagerState = pagerState
        )
        FinishButton(
            modifier = Modifier.weight(1f),
            pagerState = pagerState,
            onClick = {
                multiplePermissionResultLauncher.launch(permissionsToRequest)
                viewModel.onEvent(OnBoardingEvent.SaveOnBoarding(true))
            }
        )
    }

    dialogQueue
        .reversed()
        .forEach { permission ->
            PermissionDialog(
                permissionTextProvider = when (permission) {
                    Manifest.permission.CAMERA -> {
                        CameraPermissionTextProvider()
                    }
                    Manifest.permission.RECORD_AUDIO -> {
                        RecordAudioPermissionTextProvider()
                    }
                    Manifest.permission.READ_EXTERNAL_STORAGE -> {
                        ReadStoragePermissionTextProvider()
                    }
                    else -> return@forEach
                },
                isPermanentlyDeclined = !shouldShowRequestPermissionRationale(
                    activity, permission
                ),
                onDismiss = { viewModel.onEvent(OnBoardingEvent.DismissDialog) },
                onOkClick = {
                    viewModel.onEvent(OnBoardingEvent.DismissDialog)
                    multiplePermissionResultLauncher.launch(
                        arrayOf(permission)
                    )
                },
                onGoToAppSettingsClick = { context.openSettings() }
            )
        }
}

@Composable
fun PagerScreen(onBoardingPage: OnBoardingPage) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        LottieView(
            json = onBoardingPage.image,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.7f)
                .align(Alignment.CenterHorizontally)
        )

        Text(
            text = stringResource(id = onBoardingPage.title),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )

        Text(
            text = stringResource(id = onBoardingPage.description),
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .padding(top = 20.dp)
        )
    }
}


@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun FinishButton(
    modifier: Modifier,
    pagerState: PagerState,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(horizontal = 40.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth(),
            visible = pagerState.currentPage == 3
        ) {
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White
                )
            ) {
                Text(text = stringResource(id = R.string.finish_btn_text))
            }
        }
    }
}

