package com.ec25p5e.notesapp.feature_chat.presentation.chat

import android.util.Base64
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.presentation.components.StandardToolbar
import com.ec25p5e.notesapp.core.presentation.ui.theme.SpaceLarge
import com.ec25p5e.notesapp.core.presentation.ui.theme.SpaceMedium
import com.ec25p5e.notesapp.core.util.Screen
import com.ec25p5e.notesapp.feature_chat.presentation.components.ChatItem

@OptIn(ExperimentalCoilApi::class)
@ExperimentalMaterialApi
@Composable
fun ChatScreen(
    imageLoader: ImageLoader,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: ChatViewModel = hiltViewModel()
) {
    val chats = viewModel.state.value.chats
    val isLoading = viewModel.state.value.isLoading

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        StandardToolbar(
            onNavigateUp = onNavigateUp,
            title = {
                Text(
                    text = stringResource(id = R.string.your_chats),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = false,
            navActions = {
            }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(SpaceMedium),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(chats) { chat ->
                    ChatItem(
                        item = chat,
                        imageLoader = imageLoader,
                        onItemClick = {
                            onNavigate(
                                Screen.MessageScreen.route +
                                        "/${chat.remoteUserId}/${chat.remoteUsername}/" +
                                        "${Base64.encodeToString(chat.remoteUserProfilePictureUrl.encodeToByteArray(), 0)}" +
                                        "?chatId=${chat.chatId}"
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(SpaceLarge))
                }
            }
        }
    }
}