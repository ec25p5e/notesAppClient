package com.ec25p5e.notesapp.feature_auth.presentation.splash

import android.util.Log
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.data.local.connectivity.ConnectivityObserver
import com.ec25p5e.notesapp.core.presentation.util.LottieView
import com.ec25p5e.notesapp.core.presentation.util.UiEvent
import com.ec25p5e.notesapp.core.util.Screen
import com.ec25p5e.notesapp.core.util.Constants
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SplashScreen(
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    onPopBackStack: () -> Unit = {},
    onNavigate: (String) -> Unit = {},
    viewModel: SplashScreenViewModel = hiltViewModel()
) {
    val scale = remember {
        Animatable(0f)
    }

    val overshootInterpolator = remember {
        OvershootInterpolator(1f)
    }

    LaunchedEffect(key1 = true) {
        withContext(dispatcher) {
            scale.animateTo(
                targetValue = 0.5f,
                animationSpec = tween(
                    durationMillis = 1000,
                    easing = {
                        overshootInterpolator.getInterpolation(it)
                    }
                )
            )
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is UiEvent.Navigate -> {
                    onPopBackStack()
                    onNavigate(event.route)
                }
                else -> Unit
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Row {
            LottieView(
                json = R.raw.splash,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
        }
    }
}