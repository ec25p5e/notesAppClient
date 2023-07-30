package com.ec25p5e.notesapp.feature_post.presentation.main_feed

import com.ec25p5e.notesapp.core.domain.models.Post

sealed class MainFeedEvent {
    data class LikedPost(val postId: String): MainFeedEvent()
    data class DeletePost(val post: Post): MainFeedEvent()
}
