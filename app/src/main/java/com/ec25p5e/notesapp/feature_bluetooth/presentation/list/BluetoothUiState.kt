package com.ec25p5e.notesapp.feature_bluetooth.presentation.list

import com.ec25p5e.notesapp.feature_bluetooth.domain.model.BluetoothDevice

data class BluetoothUiState(
    val scannedDevices: List<BluetoothDevice> = emptyList(),
    val pairedDevices: List<BluetoothDevice> = emptyList()
)