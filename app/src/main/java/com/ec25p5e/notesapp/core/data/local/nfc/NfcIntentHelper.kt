package com.ec25p5e.notesapp.core.data.local.nfc

import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.tech.NfcA
import android.nfc.tech.NfcB

object NfcIntentHelper {

    val intentFilters: Array<IntentFilter>
    val techLists: Array<Array<String>>

    init {
        val intentFilter = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED).apply {
            try {
                addDataType("*/*")
            } catch (e: IntentFilter.MalformedMimeTypeException) {
                throw RuntimeException("Compile time error: MalformedMimeTypeException", e)
            }
        }

        intentFilters = arrayOf(intentFilter, IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED))
        techLists = arrayOf(arrayOf(NfcA::class.java.name), arrayOf(NfcB::class.java.name))
    }
}