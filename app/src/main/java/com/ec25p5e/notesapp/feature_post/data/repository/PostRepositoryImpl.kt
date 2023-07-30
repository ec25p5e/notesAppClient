package com.ec25p5e.notesapp.feature_post.data.repository

import android.net.Uri
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.domain.models.Comment
import com.ec25p5e.notesapp.core.domain.models.Post
import com.ec25p5e.notesapp.core.domain.models.UserItem
import com.ec25p5e.notesapp.core.util.Resource
import com.ec25p5e.notesapp.core.util.SimpleResource
import com.ec25p5e.notesapp.core.util.UiText
import com.ec25p5e.notesapp.feature_post.data.remote.PostApi
import com.ec25p5e.notesapp.feature_post.domain.repository.PostRepository
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException

class PostRepositoryImpl(
    private val api: PostApi,
    private val gson: Gson
): PostRepository {

    override suspend fun getPostsForFollows(page: Int, pageSize: Int): Resource<List<Post>> {
        return try {
            val posts = api.getPostsForFollows(
                page = page,
                pageSize = pageSize
            )

            Resource.Success(data = posts)
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

    override suspend fun createPost(description: String, imageUrl: Uri): SimpleResource {
        TODO("Not yet implemented")
    }

    override suspend fun getPostDetails(postId: String): Resource<Post> {
        TODO("Not yet implemented")
    }

    override suspend fun getCommentsForPost(postId: String): Resource<List<Comment>> {
        TODO("Not yet implemented")
    }

    override suspend fun createComment(postId: String, comment: String): SimpleResource {
        TODO("Not yet implemented")
    }

    override suspend fun likeParent(parentId: String, parentType: Int): SimpleResource {
        TODO("Not yet implemented")
    }

    override suspend fun unlikeParent(parentId: String, parentType: Int): SimpleResource {
        TODO("Not yet implemented")
    }

    override suspend fun getLikesForParent(parentId: String): Resource<List<UserItem>> {
        TODO("Not yet implemented")
    }

    override suspend fun deletePost(postId: String): SimpleResource {
        TODO("Not yet implemented")
    }
}