package com.ec25p5e.notesapp.feature_note.data.repository

import android.content.SharedPreferences
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.util.Constants
import com.ec25p5e.notesapp.core.util.Resource
import com.ec25p5e.notesapp.core.util.SimpleResource
import com.ec25p5e.notesapp.core.util.UiText
import com.ec25p5e.notesapp.feature_note.data.data_source.CategoryDao
import com.ec25p5e.notesapp.feature_note.data.remote.api.CategoryApi
import com.ec25p5e.notesapp.feature_note.data.remote.request.CreateCategoryRequest
import com.ec25p5e.notesapp.feature_note.data.remote.request.GetCategoriesRequest
import com.ec25p5e.notesapp.feature_note.domain.models.Category
import com.ec25p5e.notesapp.feature_note.domain.models.Note
import com.ec25p5e.notesapp.feature_note.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException

class CategoryRepositoryImpl(
    private val dao: CategoryDao,
    private val api: CategoryApi,
    private val sharedPreferences: SharedPreferences
): CategoryRepository {

    private val userId = sharedPreferences.getString(Constants.KEY_USER_ID, "").toString()

    override suspend fun getAllCategories(fetchFromRemote: Boolean): Flow<List<Category>> {
        if(fetchFromRemote) {
            val userId = sharedPreferences.getString(Constants.KEY_USER_ID, "").toString()
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

    override suspend fun insertCategory(category: Category): SimpleResource {
        val request = CreateCategoryRequest(
            userId = userId,
            name = category.name,
            color = category.color,
            timestamp = category.timestamp
        )

        return try {
            val response = api.createCategory(request)

            if(response.successful) {
                dao.insertCategory(category)
                getAllCategories(true)
                Resource.Success(Unit)
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

        // return dao.insertCategory(category)
    }
}