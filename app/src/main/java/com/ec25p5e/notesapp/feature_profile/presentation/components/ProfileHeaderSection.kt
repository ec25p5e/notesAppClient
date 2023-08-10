package com.ec25p5e.notesapp.feature_profile.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.domain.models.User
import com.ec25p5e.notesapp.core.presentation.ui.theme.SpaceSmall

@Composable
fun ProfileHeaderSection(
    user: User,
    isOwnProfile: Boolean = true,
    onEditClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
    onMessageClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .offset(
                    x = if(isOwnProfile) {
                        (30.dp + SpaceSmall) / 2f
                    } else 0.dp
                )
        ) {
            Text(
                text = user.username,
                textAlign = TextAlign.Center
            )

            if(!isOwnProfile) {
                Spacer(modifier = Modifier.width(SpaceSmall))
                IconButton(
                    onClick = onMessageClick,
                    modifier = Modifier.size(30.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Message,
                        contentDescription = stringResource(id = R.string.message)
                    )
                }
            }

            if(isOwnProfile) {
                Spacer(modifier = Modifier.width(SpaceSmall))
                IconButton(
                    onClick = onEditClick,
                    modifier = Modifier.size(30.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(id = R.string.edit_profile)
                    )
                }

                IconButton(
                    onClick = onLogoutClick,
                    modifier = Modifier.size(30.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = stringResource(id = R.string.logout)
                    )
                }
            }
        }
    }
}