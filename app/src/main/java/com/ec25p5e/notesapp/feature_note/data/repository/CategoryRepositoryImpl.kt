package com.ec25p5e.notesapp.feature_note.data.repository

import android.util.Log
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.data.dto.response.BasicApiResponse
import com.ec25p5e.notesapp.core.data.local.datastore_pref.DataStorePreferenceConstants
import com.ec25p5e.notesapp.core.data.local.datastore_pref.DataStorePreferenceImpl
import com.ec25p5e.notesapp.core.util.Resource
import com.ec25p5e.notesapp.core.util.SimpleResource
import com.ec25p5e.notesapp.core.util.UiText
import com.ec25p5e.notesapp.feature_note.data.data_source.CategoryDao
import com.ec25p5e.notesapp.feature_note.data.mapper.toCategory
import com.ec25p5e.notesapp.feature_note.data.mapper.toCreateCategoryRequest
import com.ec25p5e.notesapp.feature_note.data.remote.api.CategoryApi
import com.ec25p5e.notesapp.feature_note.data.remote.request.GetCategoriesRequest
import com.ec25p5e.notesapp.feature_note.data.remote.response.CategoryResponse
import com.ec25p5e.notesapp.feature_note.domain.models.Category
import com.ec25p5e.notesapp.feature_note.domain.models.Note
import com.ec25p5e.notesapp.feature_note.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import java.io.IOException

class CategoryRepositoryImpl(
    private val dao: CategoryDao,
    private val api: CategoryApi,
    dataStore: DataStorePreferenceImpl,
): CategoryRepository {

    private val userId = runBlocking(Dispatchers.IO) {
        dataStore.getPreference(DataStorePreferenceConstants.USER_ID, "").first().toString()
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

    override suspend fun pushCategories(): Resource<List<CategoryResponse>> {
        val localDatabaseCategories = dao.getCategories()
        val remoteList = runBlocking(Dispatchers.IO) {
            localDatabaseCategories.first()
        }

        return try {
            val response = api.pushCategories(
                remoteList.map {
                    it.toCreateCategoryRequest()
                }
            )

            if(response.successful) {
                Resource.Success(response.data)
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
            }
        } catch(e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch(e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }

    override fun getCategoryById(categoryId: Int): Category {
        return dao.getCategoryById(categoryId)
    }

    override fun insertCategory(category: Category) {
        dao.insertCategory(category)
    }

    override fun deleteCategory(category: Category) {
        dao.deleteCategory(category)
    }
}