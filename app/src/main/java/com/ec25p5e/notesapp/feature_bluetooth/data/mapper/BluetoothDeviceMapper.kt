package com.ec25p5e.notesapp.feature_bluetooth.data.mapper

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import com.ec25p5e.notesapp.feature_bluetooth.domain.model.BluetoothDeviceDomain

@SuppressLint("MissingPermission")
fun BluetoothDevice.toBluetoothDeviceDomain(): BluetoothDeviceDomain {
    return BluetoothDeviceDomain(
        name = name,
        address = address
    )
}