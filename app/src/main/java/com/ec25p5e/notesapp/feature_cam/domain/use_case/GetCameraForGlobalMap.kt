package com.ec25p5e.notesapp.feature_cam.domain.use_case

import com.ec25p5e.notesapp.core.util.concatenate
import com.ec25p5e.notesapp.feature_cam.data.mapper.toWebcam
import com.ec25p5e.notesapp.feature_cam.data.remote.dto.OverviewDto
import com.ec25p5e.notesapp.feature_cam.domain.model.Webcam
import com.ec25p5e.notesapp.feature_cam.domain.repository.CamRepository
import kotlin.math.roundToInt

class GetCameraForGlobalMap(
    private val repository: CamRepository
) {

    /**
     * REGIONS: Specific region to search
     * LIMIT: Max number of rows to fetch. > 0 and < 50
     * OFFSET: Start position of fetching. Max: 50, offset 1 and 51 at second try
     * INCLUDE: element to include into fetching result. Default value are provided by default
     */
    suspend operator fun invoke(): List<Webcam>{
        val limit = 50
        var objectList: List<OverviewDto> = emptyList()
        var nextOffset = (limit + 1)
        val totalObjectToFetch = repository.getWebcamForGlobalMap(
            limit = limit,
            offset = 0,
            include = "location,player,urls,images,categories",
            regions = "CH.TI"
        )
        objectList = concatenate(totalObjectToFetch.webcams, objectList)
        val maxCycles = ((totalObjectToFetch.total.toDouble().roundToInt() / limit).toInt() + 1)
        var prevOffset = 0

        for(i in 0..maxCycles) {
            if(i == 0) {
                prevOffset = 0
                nextOffset = (limit + 1)
            } else {
                prevOffset = nextOffset
                nextOffset = (prevOffset + limit)
            }

            val returnList = repository.getWebcamForGlobalMap(
                limit = limit,
                offset = nextOffset,
                include = "location,player,urls,images,categories",
                regions = "CH.TI",
            )

            objectList = concatenate(returnList.webcams, objectList)
        }

        return objectList.map { it.toWebcam() }
    }
}