package com.ec25p5e.notesapp.feature_crypto.data.remote

import com.ec25p5e.notesapp.feature_crypto.data.remote.dto.CoinDetailDto
import com.ec25p5e.notesapp.feature_crypto.data.remote.dto.CoinDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinApi {

    @GET("/v1/coins")
    suspend fun getCoins(): List<CoinDto>

    @GET("/v1/coins/{coinId}")
    suspend fun getCoinById(@Path("coinId") coinId: String): CoinDetailDto
}