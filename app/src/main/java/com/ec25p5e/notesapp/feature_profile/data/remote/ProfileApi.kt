package com.ec25p5e.notesapp.feature_profile.data.remote

import com.ec25p5e.notesapp.core.data.dto.response.BasicApiResponse
import com.ec25p5e.notesapp.core.util.Constants
import com.ec25p5e.notesapp.feature_profile.data.dto.UserItemDto
import com.ec25p5e.notesapp.feature_profile.data.remote.response.ProfileResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ProfileApi {

    @GET("/api/user/profile")
    suspend fun getProfile(
        @Query("userId") userId: String
    ): BasicApiResponse<ProfileResponse>

    @GET("/api/user/search")
    suspend fun searchUser(
        @Query("query") query: String
    ): List<UserItemDto>

    companion object {
        const val BASE_URL = Constants.BASE_URL_SERVER
    }
}