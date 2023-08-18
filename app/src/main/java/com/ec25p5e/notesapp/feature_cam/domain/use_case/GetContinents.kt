package com.ec25p5e.notesapp.feature_cam.domain.use_case

import com.ec25p5e.notesapp.feature_cam.data.mapper.toContinents
import com.ec25p5e.notesapp.feature_cam.domain.model.Continent
import com.ec25p5e.notesapp.feature_cam.domain.repository.CamRepository

class GetContinents(
    private val repository: CamRepository
) {

    suspend operator fun invoke(): List<Continent> {
        val continents = repository.getContinents()
        return continents.map { it.toContinents() }
    }
}