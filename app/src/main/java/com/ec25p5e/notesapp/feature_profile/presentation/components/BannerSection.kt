package com.ec25p5e.notesapp.feature_profile.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberImagePainter
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.presentation.ui.theme.SpaceSmall
import com.ec25p5e.notesapp.core.util.toPx


@Composable
fun BannerSection(
    imageLoader: ImageLoader,
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    iconSize: Dp = 35.dp,
    leftIconModifier: Modifier = Modifier,
    rightIconModifier: Modifier = Modifier,
    bannerUrl: String? = null,
    shouldShowGithub: Boolean = false,
    onGitHubClick: () -> Unit = {},
) {
    BoxWithConstraints(
        modifier = modifier
    ) {
        Image(
            painter = rememberImagePainter(
                data = bannerUrl,
                imageLoader = imageLoader
            ),
            contentDescription = stringResource(id = R.string.banner_image),
            contentScale = ContentScale.Crop,
            modifier = imageModifier
                .fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        ),
                        startY = constraints.maxHeight - iconSize.toPx() * 2f
                    )
                )
        )
        Row(
            modifier = leftIconModifier
                .height(iconSize)
                .align(Alignment.BottomStart)
                .padding(SpaceSmall)
        ) {
            /* topSkills.forEach { skill ->
                Spacer(modifier = Modifier.width(SpaceSmall))
                Image(
                    painter = rememberImagePainter(
                        data = skill.imageUrl,
                        imageLoader = imageLoader,
                    ),
                    contentDescription = null,
                    modifier = Modifier.height(iconSize)
                )
            } */
        }

        Row(
            modifier = rightIconModifier
                .height(iconSize)
                .align(Alignment.BottomEnd)
                .padding(SpaceSmall)
        ) {
            if (shouldShowGithub) {
                IconButton(
                    onClick = onGitHubClick,
                    modifier = Modifier.size(iconSize)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_category),
                        contentDescription = "GitHub",
                        modifier = Modifier.size(iconSize)
                    )
                }
            }
        }
    }
}