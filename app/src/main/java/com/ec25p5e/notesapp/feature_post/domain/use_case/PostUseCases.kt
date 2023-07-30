package com.ec25p5e.notesapp.feature_post.domain.use_case

data class PostUseCases(
    val createComment: CreateCommentUseCase,
    val getPostsForFollows: GetPostsForFollowsUseCase
)