package com.ec25p5e.notesapp.feature_settings.presentation.sync_remote

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.presentation.components.StandardToolbar
import com.ec25p5e.notesapp.core.presentation.ui.theme.SpaceLarge
import com.ec25p5e.notesapp.core.presentation.ui.theme.SpaceMedium
import com.ec25p5e.notesapp.core.presentation.ui.theme.SpaceSmall
import com.ec25p5e.notesapp.core.presentation.util.LottieView
import com.ec25p5e.notesapp.core.presentation.util.asString
import com.ec25p5e.notesapp.feature_settings.presentation.components.SettingsGroup
import com.ec25p5e.notesapp.feature_settings.presentation.components.SettingsSwitchComp
import com.ec25p5e.notesapp.feature_settings.presentation.util.UiEventSettings
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SyncRemoteScreen(
    scaffoldState: SnackbarHostState,
    imageLoader: ImageLoader,
    onNavigateUp: () -> Unit = {},
    viewModel: SyncViewModel = hiltViewModel()
) {
    val syncOptionState = viewModel.state.value.settingsLive.syncOptions
    val viewModelState = viewModel.viewModelState.value
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is UiEventSettings.ShowSnackbar -> {
                    Toast.makeText(
                        context,
                        event.uiText!!.asString(context),
                        Toast.LENGTH_SHORT
                    ).show()
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
                    text = stringResource(id = R.string.settings_sync_to_server),
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
                        json = R.raw.sync_to_server,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        iterations = 3
                    )
                }
            }

            SettingsGroup(name = R.string.sync_to_server_options_inner) {
                SettingsSwitchComp(
                    name = R.string.sync_to_server_notes,
                    icon = R.drawable.ic_note,
                    iconDesc = R.string.sync_to_server_notes,
                    state = syncOptionState.isSyncNotes
                ) {
                    viewModel.onEvent(SyncEvent.ToggleNoteSync)
                }

                SettingsSwitchComp(
                    name = R.string.sync_to_server_categories,
                    icon = R.drawable.ic_category,
                    iconDesc = R.string.sync_to_server_categories,
                    state = syncOptionState.isSyncCategories
                ) {
                    viewModel.onEvent(SyncEvent.ToggleCategoriesSync)
                }

                SettingsSwitchComp(
                    name = R.string.sync_to_server_tasks,
                    icon = R.drawable.ic_todo,
                    iconDesc = R.string.sync_to_server_tasks,
                    state = syncOptionState.isSyncTasks
                ) {
                    viewModel.onEvent(SyncEvent.ToggleTasksSync)
                }
            }

            Button(
                onClick = {
                    viewModel.onEvent(SyncEvent.StartSync)
                },
                enabled = !viewModelState.isSyncing,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = SpaceMedium)
            ) {
                Text(stringResource(id = R.string.make_sync_text))
            }
        }

        if(viewModelState.isSyncing) {
            Column(
                modifier = Modifier
                    .padding(top = SpaceLarge)
                    .padding(horizontal = SpaceMedium)
            ) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                        .background(Color.LightGray),
                    color = MaterialTheme.colorScheme.secondary
                )

                Text(
                    text = stringResource(id = R.string.sync_in_time),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(SpaceSmall)
                )
            }
        }
    }
}