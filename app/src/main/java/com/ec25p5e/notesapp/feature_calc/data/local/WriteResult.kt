package com.ec25p5e.notesapp.feature_calc.data.local

sealed class WriteResult {
    data object Success : WriteResult()
    data object UnsupportedFormat : WriteResult()
    data object AuthenticationFailure : WriteResult()
    data object TagUnavailable : WriteResult()
    data class NfcATransceiveNotOk(val response: ByteArray) : WriteResult()
    data object UnknownError : WriteResult()
}