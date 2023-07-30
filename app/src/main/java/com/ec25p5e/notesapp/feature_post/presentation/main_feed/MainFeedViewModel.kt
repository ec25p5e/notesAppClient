package com.ec25p5e.notesapp.feature_post.presentation.main_feed

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ec25p5e.notesapp.core.util.ParentType
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.domain.models.Post
import com.ec25p5e.notesapp.core.presentation.PagingState
import com.ec25p5e.notesapp.core.presentation.util.UiEvent
import com.ec25p5e.notesapp.core.util.*
import com.ec25p5e.notesapp.feature_post.domain.use_case.PostUseCases
import com.ec25p5e.notesapp.feature_post.presentation.person_list.PostEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFeedViewModel @Inject constructor(
    private val postUseCases: PostUseCases,
    private val postLiker: PostLiker
): ViewModel() {

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _pagingState = mutableStateOf<PagingState<Post>>(PagingState())
    val pagingState: State<PagingState<Post>> = _pagingState

    private val paginator = DefaultPaginator(
        onLoadUpdated = { isLoading ->
            _pagingState.value = pagingState.value.copy(
                isLoading = isLoading
            )
        },
        onRequest = { page ->
            postUseCases.getPostsForFollows(page = page)
        },
        onSuccess = { posts ->
            _pagingState.value = pagingState.value.copy(
                items = pagingState.value.items + posts,
                endReached = posts.isEmpty(),
                isLoading = false
            )
        },
        onError = { uiText ->
            _eventFlow.emit(UiEvent.ShowSnackbar(uiText))
        }
    )


    init {
        loadNextPosts()
    }

    fun onEvent(event: MainFeedEvent) {
        when(event) {
            is MainFeedEvent.LikedPost -> {

            }
            is MainFeedEvent.DeletePost -> {

            }
        }
    }

    fun loadNextPosts() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }
}