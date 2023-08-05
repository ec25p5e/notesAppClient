package com.ec25p5e.notesapp.feature_settings.presentation.settings

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import coil.ImageLoader
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.presentation.components.StandardToolbar
import com.ec25p5e.notesapp.core.presentation.ui.theme.SpaceMedium
import com.ec25p5e.notesapp.core.presentation.util.asString
import com.ec25p5e.notesapp.core.util.Screen
import com.ec25p5e.notesapp.feature_settings.presentation.components.SettingsClickableComp
import com.ec25p5e.notesapp.feature_settings.presentation.components.SettingsGroup
import com.ec25p5e.notesapp.feature_settings.presentation.components.SettingsSwitchComp
import com.ec25p5e.notesapp.feature_settings.presentation.util.UiEventSettings
import kotlinx.coroutines.flow.collectLatest


@SuppressLint("UnrememberedMutableState")
@Composable
fun SettingsScreen(
    scaffoldState: SnackbarHostState,
    imageLoader: ImageLoader,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

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


    LaunchedEffect(lifecycleState) {
        when (lifecycleState) {
            Lifecycle.State.DESTROYED -> {}
            Lifecycle.State.INITIALIZED -> {}
            Lifecycle.State.CREATED -> {}
            Lifecycle.State.STARTED -> {}
            Lifecycle.State.RESUMED -> {}
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
                    text = stringResource(id = R.string.settings_screen_title),
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
            /* SettingsGroup(name = R.string.settings_first_category) {
                SettingsSwitchComp(
                    name = R.string.settings_switch,
                    icon = R.drawable.ic_save,
                    iconDesc = R.string.cont_archive_home,
                    state = vm.isSwitchOn.collectAsState()
                ) {
                    vm.toggleSwitch()
                }

                SettingsClickableComp(
                    name = R.string.title,
                    icon = R.drawable.ic_save,
                    iconDesc = R.string.cont_archive_home,
                ) {
                    // here you can do anything - navigate - open other settings, ...
                }

                SettingsTextComp(
                    name = R.string.title,
                    icon = R.drawable.ic_save,
                    iconDesc = R.string.cont_archive_home,
                    state = vm.textPreference.collectAsState(),
                    onSave = { finalText -> vm.saveText(finalText) },
                    onCheck = { text -> vm.checkTextInput(text) },
                )

                SettingsTextComp(
                    name = R.string.save_btn_text,
                    icon = R.drawable.ic_save,
                    iconDesc = R.string.cont_archive_home,
                    state = vm.textPreference.collectAsState(),
                    onSave = { finalText -> vm.saveText(finalText) },
                    onCheck = { text -> vm.checkTextInput(text) },
                )
            } */

            /**
             * General section
             */
            SettingsGroup(name = R.string.settings_general) {
                SettingsSwitchComp(
                    name = R.string.settings_general_auto_save,
                    icon = R.drawable.ic_save,
                    iconDesc = R.string.settings_general_auto_save,
                    state = viewModel.getSettings().isAutoSaveEnabled
                ) {
                    viewModel.onEvent(SettingsEvent.ToggleAutoSave)
                }

                SettingsClickableComp(
                    name = R.string.settings_general_unlock_method,
                    icon = R.drawable.ic_unlock,
                    iconDesc = R.string.settings_general_unlock_method,
                    onClick = {
                        onNavigate(Screen.UnlockMethodScreen.route)
                    }
                )

                SettingsClickableComp(
                    name = R.string.settings_general_theme,
                    icon = R.drawable.ic_theme,
                    iconDesc = R.string.settings_general_theme,
                    onClick = {
                        onNavigate(Screen.SelectThemeScreen.route)
                    }
                )
            }

            Spacer(modifier = Modifier.height(SpaceMedium))


            /**
             * Advanced section
             */
            SettingsGroup(name = R.string.settings_advanced) {
                SettingsClickableComp(
                    name = R.string.settings_advanced_import_note,
                    icon = R.drawable.ic_import_note,
                    iconDesc = R.string.settings_advanced_import_note,
                    onClick = {
                        onNavigate(Screen.ImportDataScreen.route)
                    }
                )

                SettingsClickableComp(
                    name = R.string.settings_advanced_permission,
                    icon = R.drawable.ic_permission,
                    iconDesc = R.string.settings_advanced_permission,
                    onClick = {
                        onNavigate(Screen.PermissionScreen.route)
                    }
                )

                SettingsSwitchComp(
                    name = R.string.settings_advanced_screenshot_note,
                    icon = R.drawable.ic_screenshot,
                    iconDesc = R.string.settings_advanced_screenshot_note,
                    state = viewModel.getSettings().isScreenshotEnabled
                ) {
                    viewModel.onEvent(SettingsEvent.ToggleScreenShotMode)
                }


                SettingsSwitchComp(
                    name = R.string.settings_advanced_block_sharing,
                    icon = R.drawable.ic_sharing,
                    iconDesc = R.string.settings_advanced_block_sharing,
                    state = viewModel.getSettings().isSharingEnabled
                ) {
                    viewModel.onEvent(SettingsEvent.ToggleSharingMode)
                }
            }

            Spacer(modifier = Modifier.height(SpaceMedium))

            /**
             * Privacy section
             */
            SettingsGroup(name = R.string.settings_privacy) {
                SettingsClickableComp(
                    name = R.string.settings_privacy_info,
                    icon = R.drawable.ic_privacy,
                    iconDesc = R.string.settings_privacy_info,
                    onClick = {
                        onNavigate(Screen.PrivacyAdviceScreen.route)
                    }
                )
            }

            Spacer(modifier = Modifier.height(SpaceMedium))

            /**
             * About us section
             */
            SettingsGroup(name = R.string.settings_about_us) {
                SettingsClickableComp(
                    name = R.string.settings_about_us_info,
                    icon = R.drawable.ic_info,
                    iconDesc = R.string.settings_about_us_info,
                    onClick = {
                        onNavigate(Screen.InfoAppScreen.route)
                    }
                )

                SettingsClickableComp(
                    name = R.string.settings_about_us_contact,
                    icon = R.drawable.ic_support,
                    iconDesc = R.string.settings_about_us_contact,
                    onClick = {
                        onNavigate(Screen.ContactMeScreen.route)
                    }
                )
            }
        }
    }
}

@Composable
fun Lifecycle.observeAsState(): State<Lifecycle.Event> {
    val state = remember { mutableStateOf(Lifecycle.Event.ON_ANY) }
    DisposableEffect(this) {
        val observer = LifecycleEventObserver { _, event ->
            state.value = event
        }
        this@observeAsState.addObserver(observer)
        onDispose {
            this@observeAsState.removeObserver(observer)
        }
    }
    return state
}