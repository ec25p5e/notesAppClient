package com.ec25p5e.notesapp.feature_auth.data.repository

import android.content.SharedPreferences
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.data.util.PreferencesManager
import com.ec25p5e.notesapp.core.util.Constants
import com.ec25p5e.notesapp.core.util.Resource
import com.ec25p5e.notesapp.core.util.SimpleResource
import com.ec25p5e.notesapp.core.util.UiText
import com.ec25p5e.notesapp.feature_auth.data.remote.AuthApi
import com.ec25p5e.notesapp.feature_auth.data.remote.request.CreateAccountRequest
import com.ec25p5e.notesapp.feature_auth.data.remote.request.LoginRequest
import com.ec25p5e.notesapp.feature_auth.domain.repository.AuthRepository
import retrofit2.HttpException
import java.io.IOException

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val sharedPreferencesManager: PreferencesManager
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

                    sharedPreferencesManager.put(Constants.KEY_JWT_TOKEN, authResponse.token)
                    sharedPreferencesManager.put(Constants.KEY_USER_ID, authResponse.userId)

                    /* sharedPreferences.edit()
                        .putString(Constants.KEY_JWT_TOKEN, authResponse.token)
                        .putString(Constants.KEY_USER_ID, authResponse.userId)
                        .apply() */
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