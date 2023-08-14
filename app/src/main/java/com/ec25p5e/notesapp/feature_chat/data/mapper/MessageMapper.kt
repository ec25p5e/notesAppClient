package com.ec25p5e.notesapp.feature_chat.data.mapper

import com.ec25p5e.notesapp.feature_chat.data.remote.data.MessageDto
import com.ec25p5e.notesapp.feature_chat.domain.model.Message
import java.text.SimpleDateFormat
import java.util.Locale

fun MessageDto.toMessage(): Message {
    return Message(
        fromId = fromId,
        toId = toId,
        text = text,
        formattedTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            .format(timestamp),
        chatId = chatId,
    )
}