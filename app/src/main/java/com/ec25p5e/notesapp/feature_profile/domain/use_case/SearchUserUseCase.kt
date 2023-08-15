package com.ec25p5e.notesapp.feature_profile.domain.use_case

import com.ec25p5e.notesapp.core.util.Resource
import com.ec25p5e.notesapp.feature_profile.domain.models.UserItem
import com.ec25p5e.notesapp.feature_profile.domain.repository.ProfileRepository

class SearchUserUseCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(query: String): Resource<List<UserItem>> {
        if(query.isBlank())
            return Resource.Success(data = emptyList())

        return repository.searchUser(query)
    }
}