package com.ec25p5e.notesapp.feature_cam.domain.use_case

import com.ec25p5e.notesapp.feature_cam.data.remote.dto.OverviewDto
import com.ec25p5e.notesapp.feature_cam.domain.repository.CamRepository

class GetCameraDetail(
    private val repository: CamRepository
) {

    suspend operator fun invoke(webcamId: Int): OverviewDto {
        return repository.getCameraDetail(webcamId)
    }
}