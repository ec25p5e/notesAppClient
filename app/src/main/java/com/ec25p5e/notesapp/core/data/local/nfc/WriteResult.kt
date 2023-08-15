package com.ec25p5e.notesapp.core.data.local.nfc

sealed class WriteResult {
    data object Success : WriteResult()
    data object UnsupportedFormat : WriteResult()
    data object AuthenticationFailure : WriteResult()
    data object TagUnavailable : WriteResult()
    data class NfcATransceiveNotOk(val response: ByteArray) : WriteResult()
    data object UnknownError : WriteResult()
}