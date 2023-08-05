package com.ec25p5e.notesapp.feature_note.data.repository

import android.content.SharedPreferences
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.data.local.preferences.DataStorePreferenceConstants
import com.ec25p5e.notesapp.core.data.local.preferences.DataStorePreferenceImpl
import com.ec25p5e.notesapp.core.util.Constants
import com.ec25p5e.notesapp.core.util.Resource
import com.ec25p5e.notesapp.core.util.SimpleResource
import com.ec25p5e.notesapp.core.util.UiText
import com.ec25p5e.notesapp.feature_note.data.data_source.CategoryDao
import com.ec25p5e.notesapp.feature_note.data.mapper.toCategory
import com.ec25p5e.notesapp.feature_note.data.remote.api.CategoryApi
import com.ec25p5e.notesapp.feature_note.data.remote.request.CreateCategoryRequest
import com.ec25p5e.notesapp.feature_note.data.remote.request.GetCategoriesRequest
import com.ec25p5e.notesapp.feature_note.data.remote.response.CategoryResponse
import com.ec25p5e.notesapp.feature_note.domain.models.Category
import com.ec25p5e.notesapp.feature_note.domain.models.Note
import com.ec25p5e.notesapp.feature_note.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import java.io.IOException

class CategoryRepositoryImpl(
    private val dao: CategoryDao,
    private val api: CategoryApi,
    dataStore: DataStorePreferenceImpl,
): CategoryRepository {

    private val userId = runBlocking {
        dataStore.getPreference(DataStorePreferenceConstants.USER_ID, "").toString()
    }

    override suspend fun getAllCategories(fetchFromRemote: Boolean): Flow<List<Category>> {
        if(fetchFromRemote) {
            val request = GetCategoriesRequest(userId)

            val remoteListings = try {
                val response = api.getCategories(request)

                if(response.successful) {
                    Resource.Success(response.data)
                } else {
                    response.message?.let { msg ->
                        Resource.Error(UiText.DynamicString(msg))
                    } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
                }
            } catch (e: IOException) {
                Resource.Error<Flow<List<Note>>>(
                    uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
                )
                null
            } catch (e: HttpException) {
                Resource.Error<Flow<List<Note>>>(
                    uiText = UiText.StringResource(R.string.oops_something_went_wrong)
                )
                null
            }

            dao.clearAll()

            val convertFetch = remoteListings?.data?.map { it.toCategory() }
            dao.insertBulkCategories(convertFetch)
        }

        return dao.getCategories()
    }

    override fun insertCategory(category: Category) {
        dao.insertCategory(category)
    }
}