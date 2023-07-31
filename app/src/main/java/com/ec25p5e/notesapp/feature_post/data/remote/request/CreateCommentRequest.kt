package com.ec25p5e.notesapp.feature_post.data.remote.request

data class CreateCommentRequest(
    val comment: String,
    val postId: String
)
