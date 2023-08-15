package com.ec25p5e.notesapp.feature_crypto.domain.model

import com.ec25p5e.notesapp.feature_crypto.data.remote.dto.TeamMember

data class CoinDetail(
    val coinId: String,
    val name: String,
    val description: String,
    val symbol: String,
    val rank: Int,
    val isActive: Boolean,
    val tags: List<String>,
    val team: List<TeamMember>
)