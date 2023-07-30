package com.ec25p5e.notesapp.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberImagePainter
import com.ec25p5e.notesapp.core.domain.models.Post
import com.ec25p5e.notesapp.core.presentation.ui.theme.MediumGray
import com.ec25p5e.notesapp.core.presentation.ui.theme.ProfilePictureSizeMedium
import com.ec25p5e.notesapp.core.presentation.ui.theme.SpaceMedium

@Composable
fun Post(
    post: Post,
    imageLoader: ImageLoader,
    modifier: Modifier = Modifier,
    showProfileImage: Boolean = true,
    onPostClick: () -> Unit = {},
    onLikeClick: () -> Unit = {},
    onCommentClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onUsernameClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(SpaceMedium)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(
                    y = if(showProfileImage) {
                        ProfilePictureSizeMedium / 2f
                    } else 0.dp
                )
                .clip(MaterialTheme.shapes.medium)
                .shadow(5.dp)
                .background(MediumGray)
                .clickable {
                    onPostClick()
                }
        ) {
            Image(
                painter = rememberImagePainter(
                    data = post.imageUrl,
                    imageLoader = imageLoader
                ),
                contentDescription = "Post image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
            )
        }
    }
}