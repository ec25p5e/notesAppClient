package com.ec25p5e.notesapp.feature_crypto.presentation.coin_detail

import com.ec25p5e.notesapp.feature_crypto.domain.model.CoinDetail

data class CoinDetailState(
    val isLoading: Boolean = false,
    val coin: CoinDetail? = null,
    val error: String = ""
)
