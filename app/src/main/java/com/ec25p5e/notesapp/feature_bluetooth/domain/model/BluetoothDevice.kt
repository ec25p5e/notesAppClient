package com.ec25p5e.notesapp.feature_bluetooth.domain.model

typealias BluetoothDeviceDomain = BluetoothDevice

data class BluetoothDevice(
    val name: String?,
    val address: String,
)