package com.ec25p5e.notesapp.core.presentation

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.nfc.Tag
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import com.ec25p5e.notesapp.core.data.local.background_location.LocationService
import com.ec25p5e.notesapp.core.data.local.connectivity.ConnectivityObserver
import com.ec25p5e.notesapp.core.data.local.connectivity.NetworkConnectivityObserver
import com.ec25p5e.notesapp.core.data.local.nfc.byteArrayToHex
import com.ec25p5e.notesapp.core.data.local.nfc.readFromTag
import com.ec25p5e.notesapp.core.presentation.components.Navigation
import com.ec25p5e.notesapp.core.presentation.components.StandardScaffold
import com.ec25p5e.notesapp.core.presentation.ui.theme.NotesAppTheme
import com.ec25p5e.notesapp.core.presentation.util.NfcIntentActivity
import com.ec25p5e.notesapp.core.util.Screen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@ExperimentalComposeUiApi
@ExperimentalCoilApi
@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : NfcIntentActivity() {

    override val TAG = "MainActivity"

    @Inject
    lateinit var imageLoader: ImageLoader

    private val bluetoothManager by lazy {
        applicationContext.getSystemService(BluetoothManager::class.java)
    }
    private val bluetoothAdapter by lazy {
        bluetoothManager?.adapter
    }

    private val isBluetoothEnabled: Boolean
        get() = bluetoothAdapter?.isEnabled == true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)

        val channelId = "alarm_id"
        val channelName = "alarm_name"
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)

        val enableBluetoothLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { /* Not needed */ }

        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { perms ->
            val canEnableBluetooth = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                perms[Manifest.permission.BLUETOOTH_CONNECT] == true
            } else true

            if(canEnableBluetooth && !isBluetoothEnabled) {
                enableBluetoothLauncher.launch(
                    Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                )
            }
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT,
                )
            )
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "location",
                "Location",
                NotificationManager.IMPORTANCE_LOW
            )
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        setContent {
            NotesAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val scaffoldState = remember { SnackbarHostState() }

                    Intent(applicationContext, LocationService::class.java).apply {
                        action = LocationService.ACTION_START
                        startService(this)
                    }

                    StandardScaffold(
                        navController = navController,
                        showBottomBar = shouldShowBottomBar(navBackStackEntry),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Navigation(navController, scaffoldState, imageLoader)
                    }
                }
            }
        }
    }

    override fun onNfcTag(tag: Tag) {
        val bytes = readFromTag(tag)
        Log.d(TAG, "bytes: ${byteArrayToHex(bytes).joinToString(" ")}")

        if (bytes.isNotEmpty()) {
            /* val intent = Intent(this, ReadActivity::class.java).apply {
                putExtra(PARCEL_TAG, tag)
                putExtra(PARCEL_TAGDATA, TagData(bytes))
            }
            startActivity(intent) */
            Toast.makeText(this, "Test", Toast.LENGTH_SHORT).show()
        } else {
            showReadErrorModalDialog(tag)
        }
    }

    private fun shouldShowBottomBar(backStackEntry: NavBackStackEntry?): Boolean {
        val doesRouteMatch = backStackEntry?.destination?.route in listOf(
            Screen.NotesScreen.route,
            Screen.TodoScreen.route,
            Screen.ChatScreen.route,
            Screen.CamListScreen.route,
            Screen.CoinListScreen.route,
        )

        return doesRouteMatch
    }
}