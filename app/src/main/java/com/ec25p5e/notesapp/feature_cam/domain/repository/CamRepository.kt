package com.ec25p5e.notesapp.feature_cam.domain.repository

import com.ec25p5e.notesapp.core.util.Resource
import com.ec25p5e.notesapp.feature_cam.data.remote.dto.CategoryCamDto
import com.ec25p5e.notesapp.feature_cam.data.remote.dto.ContinentDto
import com.ec25p5e.notesapp.feature_cam.data.remote.dto.OverviewDto
import com.ec25p5e.notesapp.feature_cam.data.remote.dto.WebcamDto

interface CamRepository {

    suspend fun getCameraForRegions(
        regions: String,
        category: String,
        include: String,
        limit: Int,
    ): WebcamDto


    /**
     * METHOD: getWebcamForRegionsMap ==> used for fetching webcams by regions
     * LIMIT: Max number of rows to fetch. > 0 and < 50
     * OFFSET: Start position of fetching. Max: 50, offset 1 and 51 at second try
     * INCLUDE: element to include into fetching result. Default value are provided by default
     */
    suspend fun getWebcamForGlobalMap(
        limit: Int,
        offset: Int,
        include: String,
        regions: String,
    ): WebcamDto


    /**
     * METHOD: getContinents ==> Get all continents
     */
    suspend fun getContinents(): List<ContinentDto>

    /**
     * METHOD: getCategories ==> Get all categories
     */
    suspend fun getCategories(): List<CategoryCamDto>


    suspend fun getCameraDetail(
        webcamId: Int,
        include: String = "location,player,urls,images,categories"
    ): OverviewDto

}