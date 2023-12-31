package com.ec25p5e.notesapp.feature_cam.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Player(
    @SerializedName("day") val playerDay: String,
    @SerializedName("month") val playerMonth: String,
    @SerializedName("year") val playerYear: String,
    @SerializedName("lifetime") val playerLifetime: String,
)
