package com.ec25p5e.notesapp.feature_cam.data.repository

import com.ec25p5e.notesapp.feature_cam.data.remote.CamApi
import com.ec25p5e.notesapp.feature_cam.data.remote.dto.CategoryCamDto
import com.ec25p5e.notesapp.feature_cam.data.remote.dto.ContinentDto
import com.ec25p5e.notesapp.feature_cam.data.remote.dto.OverviewDto
import com.ec25p5e.notesapp.feature_cam.data.remote.dto.WebcamDto
import com.ec25p5e.notesapp.feature_cam.domain.repository.CamRepository
import java.lang.RuntimeException

class CamRepositoryImpl(
    private val api: CamApi
): CamRepository {

    override suspend fun getCameraForRegions(
        regions: String,
        category: String,
        include: String,
        limit: Int
    ): WebcamDto {
        return api.getCamForRegions(regions, include, category, limit)
    }

    override suspend fun getCameraDetail(webcamId: Int, include: String): OverviewDto {
        return api.getCameraDetail(webcamId, include)
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
    ): WebcamDto {
        if(limit in 51..-1)
            throw RuntimeException("Limit is major of 0 and loser than 50")

        if(offset < 0)
            throw RuntimeException("Offset must be greater than 0")

        return api.getWebcamForGlobalMap(
            limit = limit,
            offset = offset,
            include = include,
            regions = regions,
        )
    }

    /**
     * METHOD: getContinents ==> Get all continents
     */
    override suspend fun getContinents(): List<ContinentDto> {
        return api.getContinents()
    }

    /**
     * METHOD: getCategories ==> Get all categories
     */
    override suspend fun getCategories(): List<CategoryCamDto> {
        return api.getCategories()
    }
}