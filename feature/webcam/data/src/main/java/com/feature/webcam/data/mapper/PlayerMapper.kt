package com.feature.webcam.data.mapper

import com.feature.webcam.data.remote.dto.Player

fun Player.toPlayer(): com.feature.webcam.domain.model.Player {
    return com.feature.webcam.domain.model.Player(
        playerDay = playerDay,
        playerMonth = playerMonth,
        playerYear = playerYear,
        playerLifetime = playerLifetime
    )
}