package com.ec25p5e.notesapp.feature_auth.data.repository

import android.content.SharedPreferences
import androidx.compose.ui.platform.LocalContext
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.data.util.PreferencesManager
import com.ec25p5e.notesapp.core.presentation.util.asString
import com.ec25p5e.notesapp.core.util.Constants
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
import com.ec25p5e.notesapp.feature_settings.domain.models.Settings
import com.google.common.io.Resources
import retrofit2.HttpException
import java.io.IOException

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val sharedPreferencesManager: PreferencesManager,
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
                    println("Overriding token with ${authResponse.token}")

                    sharedPreferencesManager.put(authResponse.token, Constants.KEY_JWT_TOKEN)
                    sharedPreferencesManager.put(authResponse.userId, Constants.KEY_USER_ID)
                    sharedPreferencesManager.put(Settings(
                        isAutoSaveEnabled = false,
                        isScreenshotEnabled = true
                    ), Constants.KEY_SETTINGS)

                    categoryRepository.insertCategory(
                        Category(
                            name = "All notes",
                            color = -8266006,
                            timestamp = System.currentTimeMillis()
                        )
                    )

                    noteRepository.insertNote(
                        Note(
                            title = "Simple note",
                            content = "This is a content of simple note",
                            color = -8266006,
                            timestamp = System.currentTimeMillis(),
                            isArchived = false,
                            categoryId = 1,
                            image = ""
                        )
                    )
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