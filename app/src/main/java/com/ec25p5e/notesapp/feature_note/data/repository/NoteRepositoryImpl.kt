package com.ec25p5e.notesapp.feature_note.data.repository

import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.data.local.datastore_pref.DataStorePreferenceConstants.USER_ID
import com.ec25p5e.notesapp.core.data.local.datastore_pref.DataStorePreferenceImpl
import com.ec25p5e.notesapp.core.util.Resource
import com.ec25p5e.notesapp.core.util.SimpleResource
import com.ec25p5e.notesapp.core.util.UiText
import com.ec25p5e.notesapp.feature_note.data.data_source.CategoryDao
import com.ec25p5e.notesapp.feature_note.data.data_source.NoteDao
import com.ec25p5e.notesapp.feature_note.data.mapper.toNote
import com.ec25p5e.notesapp.feature_note.data.remote.api.NoteApi
import com.ec25p5e.notesapp.feature_note.data.remote.request.SimpleNoteRequest
import com.ec25p5e.notesapp.feature_note.domain.models.Note
import com.ec25p5e.notesapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Singleton

@Singleton
class NoteRepositoryImpl(
    private val dao: NoteDao,
    private val daoCategory: CategoryDao,
    private val api: NoteApi,
    dataStore: DataStorePreferenceImpl,
): NoteRepository {

    private val userId = runBlocking { 
        dataStore.getPreference(USER_ID, "").toString()
    }

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
        val remoteList: List<Note> = emptyList()

        remoteList.apply {
            localDatabaseNotes.map {
                it.asFlow().toList()
            }
        }

        return try {
            val response = api.pushNotes(remoteList)

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
        }
    }

    override fun insertNote(note: Note) {
        val categoryId = note.categoryId
        val category = daoCategory.getCategoryById(categoryId)

        // Incrementa il contatore
        category.numNotesAssoc = category.numNotesAssoc + 1

        // Inserisci la nota
        dao.insertNote(note)

        // Fai la modifica del valore
        daoCategory.insertCategory(category)
    }

    override fun deleteNote(note: Note) {
        val categoryId = note.categoryId
        val category = daoCategory.getCategoryById(categoryId)

        // Decrementa il contatore, se inferiore a 0 imposta 0
        category.numNotesAssoc = if(category.numNotesAssoc - 1 < 0) 0 else category.numNotesAssoc - 1

        // Elimina la nota
        dao.deleteNote(note)

        // Fai la modifica al contatore
        daoCategory.insertCategory(category)
    }

    override fun archiveNote(id: Int) {
        dao.archiveNote(id)
    }

    override fun dearchiveNote(id: Int) {
        dao.dearchiveNote(id)
    }

    override fun copyNote(id: Int) {
        val numberOfCopy = dao.getNumberOfCopy(id)
        var currentNote = dao.getNoteById(id)
        val newTitle = currentNote?.title.toString().plus(" ").plus(numberOfCopy)
        val newNote = currentNote?.copy(
            title = newTitle,
        )

        dao.insertNote(newNote!!)
        currentNote = currentNote?.copy(
            isCopied = currentNote.isCopied + 1
        )
        dao.insertNote(currentNote!!)
    }

    override fun lockNote(id: Int) {
        dao.lockNote(id)
    }

    override fun unLockNote(id: Int) {
        dao.unLockNote(id)
    }
}