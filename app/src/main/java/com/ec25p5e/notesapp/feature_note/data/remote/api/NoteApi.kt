package com.ec25p5e.notesapp.feature_note.data.remote.api

import com.ec25p5e.notesapp.core.data.dto.response.BasicApiResponse
import com.ec25p5e.notesapp.feature_note.data.remote.request.SimpleNoteRequest
import com.ec25p5e.notesapp.feature_note.data.remote.response.NoteResponse
import com.ec25p5e.notesapp.feature_note.domain.models.Note
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.POST

interface NoteApi {

    @POST("/api/note/notes")
    suspend fun getNotes(
        @Body request: SimpleNoteRequest
    ): BasicApiResponse<List<NoteResponse>>

    @POST("/api/note/push")
    suspend fun pushNotes(
        @Body request: List<Note>
    ): BasicApiResponse<Unit>


    companion object {
        const val BASE_URL = "http://192.168.183.107:8080/" // ""http://85.0.253.197:8080/"
    }
}