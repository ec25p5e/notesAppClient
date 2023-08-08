package com.ec25p5e.notesapp.feature_auth.data.repository

import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.data.local.datastore_pref.DataStorePreferenceConstants.USER_ID
import com.ec25p5e.notesapp.core.data.local.datastore_pref.DataStorePreferenceConstants.USER_TOKEN
import com.ec25p5e.notesapp.core.data.local.datastore_pref.DataStorePreferenceImpl
import com.ec25p5e.notesapp.core.util.Resource
import com.ec25p5e.notesapp.core.util.SimpleResource
import com.ec25p5e.notesapp.core.util.UiText
import com.ec25p5e.notesapp.feature_auth.data.remote.AuthApi
import com.ec25p5e.notesapp.feature_auth.data.remote.request.CreateAccountRequest
import com.ec25p5e.notesapp.feature_auth.data.remote.request.LoginRequest
import com.ec25p5e.notesapp.feature_auth.domain.repository.AuthRepository
import com.ec25p5e.notesapp.feature_note.domain.models.Category
import com.ec25p5e.notesapp.feature_note.domain.models.Note
import com.ec25p5e.notesapp.feature_note.domain.repository.CategoryRepository
import com.ec25p5e.notesapp.feature_note.domain.repository.NoteRepository
import retrofit2.HttpException
import java.io.IOException

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val dataStore: DataStorePreferenceImpl,
    private val categoryRepository: CategoryRepository,
    private val noteRepository: NoteRepository
): AuthRepository {

    override suspend fun register(
        email: String,
        username: String,
        password: String
    ): SimpleResource {
        val request = CreateAccountRequest(email, username, password)

        return try {
            val response = api.register(request)

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

    override suspend fun login(email: String, password: String): SimpleResource {
        val request = LoginRequest(email, password)

        return try {
            val response = api.login(request)

            if(response.successful) {
                response.data?.let { authResponse ->
                    dataStore.putPreference(USER_TOKEN, authResponse.token)
                    dataStore.putPreference(USER_ID, authResponse.userId)
                }

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

    override suspend fun authenticate(): SimpleResource {
        return try {
            api.authenticate()
            Resource.Success(Unit)
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
}