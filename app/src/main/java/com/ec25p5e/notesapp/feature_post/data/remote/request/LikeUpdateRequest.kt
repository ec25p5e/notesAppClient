package com.ec25p5e.notesapp.feature_post.data.remote.request

data class LikeUpdateRequest(
    val parentId: String,
    val parentType: Int
)