package com.ec25p5e.notesapp.feature_note.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.util.Constants
import com.ec25p5e.notesapp.core.util.Resource
import com.ec25p5e.notesapp.core.util.SimpleResource
import com.ec25p5e.notesapp.core.util.UiText
import com.ec25p5e.notesapp.feature_note.data.data_source.NoteDao
import com.ec25p5e.notesapp.feature_note.data.remote.api.NoteApi
import com.ec25p5e.notesapp.feature_note.data.remote.request.CreateNoteRequest
import com.ec25p5e.notesapp.feature_note.data.remote.request.DeleteNoteRequest
import com.ec25p5e.notesapp.feature_note.data.remote.request.SimpleNoteRequest
import com.ec25p5e.notesapp.feature_note.domain.models.Note
import com.ec25p5e.notesapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Singleton

@Singleton
class NoteRepositoryImpl(
    private val dao: NoteDao,
    private val api: NoteApi,
    sharedPreferences: SharedPreferences
): NoteRepository {

    private val userId = sharedPreferences.getString(Constants.KEY_USER_ID, "").toString()

    override suspend fun getAllNotes(fetchFromRemote: Boolean): Resource<Flow<List<Note>>> {
        if(fetchFromRemote) {
            val request = SimpleNoteRequest(userId)

            val remoteListings = try {
                val response = api.getNotes(request)

                if (response.successful) {
                    Resource.Success(response.data)
                } else {
                    response.message?.let { msg ->
                        Resource.Error(UiText.DynamicString(msg))
                    } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
                }
            } catch (e: IOException) {
                Resource.Error<Flow<List<Note>>>(
                    uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
                )
                null
            } catch (e: HttpException) {
                Resource.Error<Flow<List<Note>>>(
                    uiText = UiText.StringResource(R.string.oops_something_went_wrong)
                )
                null
            }

            dao.clearAll()

            val convertFetch = remoteListings?.data?.map { it.toNote() }
            dao.insertBulkNote(convertFetch)
        }

        return Resource.Success(dao.getLocalNotes())
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

    override suspend fun pushNotes(): SimpleResource {
        val localDatabaseNotes = dao.getLocalNotes()

        return Resource.Success(Unit)

        /* return try {
            val response = api.pushNotes(localDatabaseNotes)

            if(response.successful) {
                Resource.Success(Unit)
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
            }
        } catch(e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch(e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        } */
    }

    override suspend fun insertNote(note: Note): SimpleResource {
        val request = CreateNoteRequest(
            userId = userId,
            title = note.title,
            content = note.content,
            timestamp = note.timestamp,
            color = note.color,
            isArchived = note.isArchived,
            idCategory = note.categoryId
        )

        return try {
            val response = api.createNote(request)

            if(response.successful) {
                dao.insertNote(note)
                getAllNotes(true)
                Resource.Success(Unit)
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
            }
        } catch(e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch(e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }

    override suspend fun deleteNote(note: Note): SimpleResource {
        val request = DeleteNoteRequest(note.remoteId, userId)

        return try {
            val response = api.deleteNote(request)

            if(response.successful) {
                dao.deleteNote(note)
                Resource.Success(Unit)
            }  else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
            }
        } catch(e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch(e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }

    override fun archiveNote(id: Int) {
        dao.archiveNote(id)
    }

    override fun dearchiveNote(id: Int) {
        dao.dearchiveNote(id)
    }
}