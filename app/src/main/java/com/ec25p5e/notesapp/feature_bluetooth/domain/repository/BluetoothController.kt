package com.ec25p5e.notesapp.feature_bluetooth.domain.repository

import com.ec25p5e.notesapp.feature_bluetooth.domain.model.BluetoothDevice
import kotlinx.coroutines.flow.StateFlow

interface BluetoothController {

    val scannedDevices: StateFlow<List<BluetoothDevice>>
    val pairedDevices: StateFlow<List<BluetoothDevice>>

    fun startDiscovery()
    fun stopDiscovery()

    fun release()
}