package com.ec25p5e.notesapp.core.util

import com.ec25p5e.notesapp.core.domain.models.Post

interface PostLiker {

    suspend fun toggleLike(
        posts: List<Post>,
        parentId: String,
        onRequest: suspend (isLiked: Boolean) -> SimpleResource,
        onStateUpdated: (List<Post>) -> Unit
    )
}