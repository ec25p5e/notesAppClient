package com.ec25p5e.notesapp.feature_profile.data.repository

import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.data.local.datastore_pref.DataStorePreferenceImpl
import com.ec25p5e.notesapp.core.util.Resource
import com.ec25p5e.notesapp.core.util.UiText
import com.ec25p5e.notesapp.feature_profile.data.mapper.toProfile
import com.ec25p5e.notesapp.feature_profile.data.remote.ProfileApi
import com.ec25p5e.notesapp.feature_profile.domain.models.Profile
import com.ec25p5e.notesapp.feature_profile.domain.repository.ProfileRepository
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import java.io.IOException

class ProfileRepositoryImpl(
    private val profileApi: ProfileApi,
    private val gson: Gson,
    private val dataStore: DataStorePreferenceImpl
): ProfileRepository {

    override suspend fun getProfile(userId: String): Resource<Profile> {
        return try {
            val response = profileApi.getProfile(userId)

            if(response.successful) {
                Resource.Success(response.data?.toProfile())
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

    override fun logout() {
        runBlocking {
            dataStore.clearAllPreference()
        }
    }
}