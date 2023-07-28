package com.ec25p5e.notesapp.feature_auth.domain.repository

import com.ec25p5e.notesapp.core.util.SimpleResource

interface AuthRepository {


    suspend fun authenticate(): SimpleResource
}