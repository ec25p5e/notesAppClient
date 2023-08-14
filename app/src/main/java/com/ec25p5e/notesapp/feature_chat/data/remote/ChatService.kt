package com.ec25p5e.notesapp.feature_chat.data.remote

import com.ec25p5e.notesapp.feature_chat.data.remote.data.WsClientMessage
import com.ec25p5e.notesapp.feature_chat.data.remote.data.WsServerMessage
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow

interface ChatService {

    @Receive
    fun observeEvents(): ReceiveChannel<WebSocket.Event>

    @Send
    fun sendMessage(message: WsClientMessage)

    @Receive
    fun observeMessages(): ReceiveChannel<WsServerMessage>
}