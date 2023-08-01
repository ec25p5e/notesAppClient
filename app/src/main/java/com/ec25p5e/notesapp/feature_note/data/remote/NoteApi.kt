package com.ec25p5e.notesapp.feature_note.data.remote

import com.ec25p5e.notesapp.core.data.dto.response.BasicApiResponse
import com.ec25p5e.notesapp.core.util.Resource
import com.ec25p5e.notesapp.feature_note.data.remote.request.GetNotesRequest
import com.ec25p5e.notesapp.feature_note.data.remote.response.NoteResponse
import com.ec25p5e.notesapp.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.POST

interface NoteApi {

    @POST("/api/note/notes")
    suspend fun getNotes(
        @Body request: GetNotesRequest
    ): List<NoteResponse>

    companion object {
        const val BASE_URL = "http://192.168.183.107:8080/"
    }
}