package com.ec25p5e.notesapp.feature_crypto.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CoinDto(
    val id: String,
    @SerializedName("isActive") val isActive: Boolean,
    @SerializedName("is_new") val isNew: Boolean,
    val name: String,
    val rank: Int,
    val symbol: String,
    val type: String,
)