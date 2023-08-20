package com.feature.webcam.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Url(
    @SerializedName("detail") val detail: String,
    @SerializedName("edit") val edit: String,
)
