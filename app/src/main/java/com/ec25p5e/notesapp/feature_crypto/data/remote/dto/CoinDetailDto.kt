package com.ec25p5e.notesapp.feature_crypto.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CoinDetailDto(
    val id: String,
    val description: String,
    val links: Links,
    val message: String,
    val name: String,
    val rank: Int,
    val symbol: String,
    val tags: List<Tag>,
    val team: List<TeamMember>,
    val type: String,
    val whitepaper: Whitepaper,

    @SerializedName("development_status") val developmentStatus: String,
    @SerializedName("first_data_at") val firstDataAt: String,
    @SerializedName("hardware_wallet") val hardwareWallet: Boolean,
    @SerializedName("hash_algorithm") val hashAlgorithm: String,
    @SerializedName("started_at") val startedAt: String,
    @SerializedName("is_active") val isActive: Boolean,
    @SerializedName("is_new") val isNew: Boolean,
    @SerializedName("last_data_at") val lastDataAt: String,
    @SerializedName("links_extended") val linksExtended: List<LinksExtended>,
    @SerializedName("open_source") val openSource: Boolean,
    @SerializedName("org_structure") val orgStructure: String,
    @SerializedName("proof_type") val proofType: String,
)