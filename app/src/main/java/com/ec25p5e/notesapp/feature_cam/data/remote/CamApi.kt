package com.ec25p5e.notesapp.feature_cam.data.remote

import com.ec25p5e.notesapp.feature_cam.data.remote.dto.ContinentDto
import com.ec25p5e.notesapp.feature_cam.data.remote.dto.OverviewDto
import com.ec25p5e.notesapp.feature_cam.data.remote.dto.WebcamDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CamApi {

    @GET("/webcams/api/v3/webcams")
    suspend fun getCamForRegions(
        @Query("regions") regions: String,
        @Query("include") include: String,
        @Query("category") category: String,
        @Query("limit") limit: Int
    ): WebcamDto


    /**
     * METHOD: getWebcamForRegionsMap
     * REGIONS: Specific region to search
     * LIMIT: Max number of rows to fetch. > 0 and < 50
     * OFFSET: Start position of fetching. Max: 50, offset 1 and 51 at second try
     * INCLUDE: element to include into fetching result. Default value are provided by default
     */
    @GET("/webcams/api/v3/webcams")
    suspend fun getWebcamForGlobalMap(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("include") include: String,
        @Query("regions") regions: String,
    ): WebcamDto


    /**
     * METHOD: getContinents ==> Get all continents
     */
    @GET("/webcams/api/v3/continents")
    suspend fun getContinents(): List<ContinentDto>

    @GET("/webcams/api/v3/webcams/{webcamId}")
    suspend fun getCameraDetail(
        @Path("webcamId") webcamId: Int,
        @Query("include") include: String,
    ): OverviewDto

    companion object {
        const val BASE_URL = "https://api.windy.com/"
    }
}