package com.feature.webcam.data.mapper

import com.feature.webcam.data.remote.dto.Url

fun Url.toUrl(): com.feature.webcam.domain.model.Url {
    return com.feature.webcam.domain.model.Url(
        detail = detail,
        edit = edit,
    )
}