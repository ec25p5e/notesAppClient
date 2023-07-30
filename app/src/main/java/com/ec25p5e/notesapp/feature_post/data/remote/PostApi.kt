package com.ec25p5e.notesapp.feature_post.data.remote

import com.ec25p5e.notesapp.core.domain.models.Post
import retrofit2.http.GET
import retrofit2.http.Query

interface PostApi {

    @GET("/api/post/get")
    suspend fun getPostsForFollows(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): List<Post>

    @GET("/api/user/posts")
    suspend fun getPostsForProfile(
        @Query("userId") userId: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): List<Post>

    companion object {
        const val BASE_URL = "http://192.168.183.107:8080/"
    }
}