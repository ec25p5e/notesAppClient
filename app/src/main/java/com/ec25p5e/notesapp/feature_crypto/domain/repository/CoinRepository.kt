package com.ec25p5e.notesapp.feature_crypto.domain.repository

import com.ec25p5e.notesapp.feature_crypto.data.remote.dto.CoinDetailDto
import com.ec25p5e.notesapp.feature_crypto.data.remote.dto.CoinDto

interface CoinRepository {

    suspend fun getCoins(): List<CoinDto>

    suspend fun getCoinById(coinId: String): CoinDetailDto
}