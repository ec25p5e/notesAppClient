package com.ec25p5e.notesapp.feature_crypto.presentation.coin_list

import com.ec25p5e.notesapp.feature_crypto.domain.model.Coin

data class CoinListState(
    val isLoading: Boolean = false,
    val coins: List<Coin> = emptyList(),
    val error: String = ""
)
