package com.ec25p5e.notesapp.feature_crypto.data.mapper

import com.ec25p5e.notesapp.feature_crypto.data.remote.dto.CoinDetailDto
import com.ec25p5e.notesapp.feature_crypto.data.remote.dto.CoinDto
import com.ec25p5e.notesapp.feature_crypto.domain.model.Coin
import com.ec25p5e.notesapp.feature_crypto.domain.model.CoinDetail


fun CoinDto.toCoin(): Coin {
    return Coin(
        id = id,
        isActive = isActive,
        name = name,
        rank = rank,
        symbol = symbol,
    )
}


fun CoinDetailDto.toCoinDetail(): CoinDetail {
    return CoinDetail(
        coinId = id,
        name = name,
        description = description,
        symbol = symbol,
        rank = rank,
        isActive = isActive,
        tags = tags.map { it.name },
        team = team
    )
}