package com.ec25p5e.notesapp.feature_cam.domain.use_case

import com.ec25p5e.notesapp.feature_cam.data.remote.dto.WebcamDto
import com.ec25p5e.notesapp.feature_cam.domain.repository.CamRepository
import kotlinx.coroutines.flow.Flow

class GetCameraForRegions(
    private val repository: CamRepository
) {

    suspend operator fun invoke(): WebcamDto {
        return repository.getCameraForRegions(
            regions = "CH.TI",
            category = "traffic",
            include = "location,player,urls,images,categories",
            limit = 50
        )
    }
}