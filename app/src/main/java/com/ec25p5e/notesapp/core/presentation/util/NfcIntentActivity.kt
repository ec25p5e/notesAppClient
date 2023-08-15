package com.ec25p5e.notesapp.core.presentation.util

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.data.local.nfc.NfcIntentHelper
import com.ec25p5e.notesapp.core.data.local.nfc.tagIdAsString
import com.ec25p5e.notesapp.core.data.local.nfc.techListOf

@ExperimentalUnsignedTypes
abstract class NfcIntentActivity: ComponentActivity() {

    private lateinit var pendingIntent: PendingIntent
    protected var nfcAdapter: NfcAdapter? = null
    protected abstract val TAG: String

    abstract fun onNfcTag(tag: Tag)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNfcTagIntents()
    }

    private fun initNfcTagIntents() {
        val intent = Intent(this, javaClass).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
    }

    override fun onPause() {
        Log.i(TAG, "onPause()")
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
    }

    override fun onResume() {
        Log.i(TAG, "onResume()")
        super.onResume()
        nfcAdapter?.enableForegroundDispatch(
            this,
            pendingIntent,
            NfcIntentHelper.intentFilters,
            NfcIntentHelper.techLists
        )
    }

    @SuppressLint("StringFormatInvalid")
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val tag: Tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG) ?: return
        Log.i(TAG, "onNewIntent tag: ${tag.id}")

        val id = tagIdAsString(tag)
        Toast.makeText(this, getString(R.string.nfc_tag_found, id), Toast.LENGTH_LONG).show()

        return onNfcTag(tag)
    }

    @SuppressLint("StringFormatInvalid")
    fun showReadErrorModalDialog(tag: Tag) {
        with(AlertDialog.Builder(this)) {
            setTitle(R.string.nfc_read_tag_failure)
            setMessage(getString(R.string.nfc_tag_technologies, techListOf(tag).joinToString(", ")))

            setPositiveButton(getString(R.string.ok)) { _, _ -> }
            create().show()
        }
    }

    companion object {
        const val PARCEL_TAG = "tag"
        const val PARCEL_TAGDATA = "bytes"
    }
}