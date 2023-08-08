package com.ec25p5e.notesapp.feature_settings.presentation.choose_theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.presentation.components.StandardToolbar
import com.ec25p5e.notesapp.core.presentation.ui.theme.SpaceMedium
import com.ec25p5e.notesapp.core.presentation.util.LottieView
import com.ec25p5e.notesapp.feature_settings.presentation.components.SettingsGroup
import com.ec25p5e.notesapp.feature_settings.presentation.components.SettingsSwitchComp
import com.ec25p5e.notesapp.feature_settings.presentation.settings.SettingsViewModel
import com.ec25p5e.notesapp.feature_settings.presentation.sync_remote.SyncEvent

@Composable
fun ChooseThemeScreen(
    scaffoldState: SnackbarHostState,
    imageLoader: ImageLoader,
    onNavigateUp: () -> Unit = {},
    vm: SettingsViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        StandardToolbar(
            onNavigateUp = onNavigateUp,
            title = {
                Text(
                    text = stringResource(id = R.string.settings_general_theme),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = true,
            navActions = {
            }
        )

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(SpaceMedium)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                Row {
                    LottieView(
                        json = R.raw.change_theme,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        iterations = 3
                    )
                }
            }

            SettingsSwitchComp(
                name = R.string.enable_dynamic_color,
                icon = R.drawable.ic_dynamic,
                iconDesc = R.string.enable_dynamic_color,
                state = true,
            ) {
                // viewModel.onEvent(SyncEvent.ToggleNoteSync)
            }
        }
    }
}