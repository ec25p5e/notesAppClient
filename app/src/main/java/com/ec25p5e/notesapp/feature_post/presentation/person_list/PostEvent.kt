package com.ec25p5e.notesapp.feature_post.presentation.person_list

import com.ec25p5e.notesapp.core.util.Event

sealed class PostEvent : Event() {
    object OnLiked: PostEvent()
}