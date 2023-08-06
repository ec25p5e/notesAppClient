package com.ec25p5e.notesapp.feature_settings.presentation.unlock_method

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.presentation.components.StandardToolbar
import com.ec25p5e.notesapp.feature_settings.presentation.settings.SettingsViewModel

@Composable
fun UnlockMethodScreen(
    scaffoldState: SnackbarHostState,
    imageLoader: ImageLoader,
    onNavigateUp: () -> Unit = {},
    viewModelSettings: SettingsViewModel = hiltViewModel(),
    viewModel: UnlockMethodViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        StandardToolbar(
            onNavigateUp = onNavigateUp,
            title = {
                Text(
                    text = stringResource(id = R.string.settings_general_unlock_method),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = true,
            navActions = {
            }
        )

        if(state.isLoggedIn) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = "Set a pin")
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {

                    }
                ) {
                    Text(text = "Authenticate")
                }

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {

                    }
                ) {
                    Text(text = "Change Pin")
                }
            }
        }

    }
}