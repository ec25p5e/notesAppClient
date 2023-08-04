package com.ec25p5e.notesapp.core.data.util

import android.app.Application
import android.os.Bundle
import android.speech.RecognitionListener

class VoiceToTextParser(
    private val app: Application
): RecognitionListener {

    override fun onReadyForSpeech(params: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onBeginningOfSpeech() {
        TODO("Not yet implemented")
    }

    override fun onRmsChanged(rmsdB: Float) {
        TODO("Not yet implemented")
    }

    override fun onBufferReceived(buffer: ByteArray?) {
        TODO("Not yet implemented")
    }

    override fun onEndOfSpeech() {
        TODO("Not yet implemented")
    }

    override fun onError(error: Int) {
        TODO("Not yet implemented")
    }

    override fun onResults(results: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onPartialResults(partialResults: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onSegmentResults(segmentResults: Bundle) {
        super.onSegmentResults(segmentResults)
    }

    override fun onEndOfSegmentedSession() {
        super.onEndOfSegmentedSession()
    }

    override fun onEvent(eventType: Int, params: Bundle?) {
        TODO("Not yet implemented")
    }
}