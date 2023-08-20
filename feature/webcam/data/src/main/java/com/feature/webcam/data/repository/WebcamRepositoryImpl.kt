package com.feature.webcam.data.repository

import com.feature.webcam.data.remote.WebcamApi
import com.feature.webcam.domain.model.CategoryCam
import com.feature.webcam.domain.model.Webcam
import com.feature.webcam.domain.repository.WebcamRepository
import java.lang.RuntimeException

class WebcamRepositoryImpl(
    private val api: WebcamApi
): WebcamRepository {

    override suspend fun getCameraForRegions(
        regions: String,
        category: String,
        include: String,
        limit: Int
    ): List<Webcam> {
        return api.getCamForRegions(regions, include, category, limit).webcams
    }

    override suspend fun getCameraDetail(
        webcamId: Int,
        include: String
    ): Webcam {
        return api.getCameraDetail(webcamId, include).toWebcam()
    }


    /**
     * METHOD: getWebcamForRegionsMap ==> used for fetching webcams by regions
     * LIMIT: Max number of rows to fetch. > 0 and < 50
     * OFFSET: Start position of fetching. Max: 50, offset 1 and 51 at second try
     * INCLUDE: element to include into fetching result. Default value are provided by default
     */
    override suspend fun getWebcamForGlobalMap(
        limit: Int,
        offset: Int,
        include: String,
        regions: String
    ): List<Webcam> {
        if(limit in 51 downTo -1)
            throw RuntimeException("Limit is major of 0 and loser than 50")

        if(offset < 0)
            throw RuntimeException("Offset must be greater than 0")

        return api.getWebcamForGlobalMap(
            limit = limit,
            offset = offset,
            include = include,
            regions = regions,
        ).webcams
    }

    /**
     * METHOD: getContinents ==> Get all continents
     */
    override suspend fun getContinents(): List<com.feature.webcam.domain.model.Continent> {
        return api.getContinents().map { it.toContinents() }
    }

    /**
     * METHOD: getCategories ==> Get all categories
     */
    override suspend fun getCategories(): List<CategoryCam> {
        return api.getCategories()
    }
}