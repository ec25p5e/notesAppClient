package com.ec25p5e.notesapp.feature_crypto.data.repository

import com.ec25p5e.notesapp.feature_crypto.data.remote.CoinApi
import com.ec25p5e.notesapp.feature_crypto.data.remote.dto.CoinDetailDto
import com.ec25p5e.notesapp.feature_crypto.data.remote.dto.CoinDto
import com.ec25p5e.notesapp.feature_crypto.domain.repository.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val api: CoinApi
): CoinRepository {

    override suspend fun getCoins(): List<CoinDto> {
        return api.getCoins()
    }

    override suspend fun getCoinById(coinId: String): CoinDetailDto {
        return api.getCoinById(coinId)
    }
}