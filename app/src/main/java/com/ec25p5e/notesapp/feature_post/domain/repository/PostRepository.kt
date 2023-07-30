package com.ec25p5e.notesapp.feature_post.domain.repository

import android.net.Uri
import com.ec25p5e.notesapp.core.domain.models.Comment
import com.ec25p5e.notesapp.core.domain.models.Post
import com.ec25p5e.notesapp.core.domain.models.UserItem
import com.ec25p5e.notesapp.core.util.Resource
import com.ec25p5e.notesapp.core.util.SimpleResource

interface PostRepository {

    suspend fun getPostsForFollows(page: Int, pageSize: Int): Resource<List<Post>>

    suspend fun createPost(description: String, imageUrl: Uri): SimpleResource

    suspend fun getPostDetails(postId: String): Resource<Post>

    suspend fun getCommentsForPost(postId: String): Resource<List<Comment>>

    suspend fun createComment(postId: String, comment: String): SimpleResource

    suspend fun likeParent(parentId: String, parentType: Int): SimpleResource

    suspend fun unlikeParent(parentId: String, parentType: Int): SimpleResource

    suspend fun getLikesForParent(parentId: String): Resource<List<UserItem>>

    suspend fun deletePost(postId: String): SimpleResource
}