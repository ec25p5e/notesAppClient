package com.feature.webcam.domain.repository

import com.feature.webcam.domain.model.CategoryCam
import com.feature.webcam.domain.model.Continent
import com.feature.webcam.domain.model.Webcam

interface WebcamRepository {

    suspend fun getCameraForRegions(
        regions: String,
        category: String,
        include: String,
        limit: Int,
    ): List<Webcam>


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
    ): List<Webcam>


    /**
     * METHOD: getContinents ==> Get all continents
     */
    suspend fun getContinents(): List<Continent>

    /**
     * METHOD: getCategories ==> Get all categories
     */
    suspend fun getCategories(): List<CategoryCam>


    suspend fun getCameraDetail(
        webcamId: Int,
        include: String = "location,player,urls,images,categories"
    ): Webcam

}