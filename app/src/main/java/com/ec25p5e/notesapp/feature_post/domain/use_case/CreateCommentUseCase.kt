package com.ec25p5e.notesapp.feature_post.domain.use_case

import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.util.Resource
import com.ec25p5e.notesapp.core.util.SimpleResource
import com.ec25p5e.notesapp.core.util.UiText
import com.ec25p5e.notesapp.feature_post.domain.repository.PostRepository

class CreateCommentUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(postId: String, comment: String): SimpleResource {
        if(comment.isBlank())
            return Resource.Error(UiText.StringResource(R.string.field_empty_text_error))

        if(postId.isBlank())
            return Resource.Error(UiText.unknownError())

        return repository.createComment(postId, comment)

    }
}