package com.ec25p5e.notesapp.feature_note.data.repository

import android.content.SharedPreferences
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.util.Resource
import com.ec25p5e.notesapp.core.util.UiText
import com.ec25p5e.notesapp.feature_note.data.data_source.NoteDao
import com.ec25p5e.notesapp.feature_note.data.remote.NoteApi
import com.ec25p5e.notesapp.feature_note.data.remote.request.GetNotesRequest
import com.ec25p5e.notesapp.feature_note.data.remote.response.NoteResponse
import com.ec25p5e.notesapp.feature_note.domain.model.Note
import com.ec25p5e.notesapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException

class NoteRepositoryImpl(
    private val dao: NoteDao,
    private val api: NoteApi,
    private val sharedPreferences: SharedPreferences
): NoteRepository {

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override fun getAllNotes(
        fetchFromRemote: Boolean
    ): Flow<Resource<List<Note>>> {
        return flow {
            val request = GetNotesRequest("1")
            emit(Resource.Loading(true))

            val localListings = dao.getLocalNotes()
            emit(Resource.Success(
                data = localListings.map { it.toNote() }
            ))

            val isDbEmpty = localListings.isEmpty()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote

            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteListings = try {
                api.getNotes(request)
            } catch(e: IOException) {
                Resource.Error<List<Note>>(
                    uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
                )
                null
            } catch(e: HttpException) {
                Resource.Error<List<Note>>(
                    uiText = UiText.StringResource(R.string.oops_something_went_wrong)
                )
                null
            }

            remoteListings?.let { listings ->
                dao.insertBulkNote(
                    listings.map { it.toNote() }
                )
                emit(Resource.Success(
                    data = dao
                        .getLocalNotes()
                        .map { it.toNote() }
                ))
                emit(Resource.Loading(false))
            }
        }
    }

    override fun getNotesForArchive(): Flow<List<Note>> {
        return dao.getNotesForArchive()
    }

    override fun getNotesByCategory(categoryId: Int): Flow<List<Note>> {
        return dao.getNotesByCategory(categoryId)
    }

    override fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override fun insertNote(note: Note) {
        dao.insertNote(note)
    }

    override fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }

    override fun archiveNote(id: Int) {
        dao.archiveNote(id)
    }

    override fun dearchiveNote(id: Int) {
        dao.dearchiveNote(id)
    }
}