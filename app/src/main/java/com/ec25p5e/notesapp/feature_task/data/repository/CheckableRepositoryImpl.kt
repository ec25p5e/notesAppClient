package com.ec25p5e.notesapp.feature_task.data.repository

import com.ec25p5e.notesapp.core.data.local.datastore_pref.DataStorePreferenceImpl
import com.ec25p5e.notesapp.feature_task.data.data_source.CheckableDao
import com.ec25p5e.notesapp.feature_task.domain.models.Checkable
import com.ec25p5e.notesapp.feature_task.domain.repository.CheckableRepository
import kotlinx.coroutines.flow.Flow

class CheckableRepositoryImpl(
    private val dao: CheckableDao,
): CheckableRepository {

    override fun getCheckablesByTask(taskId: Int): Flow<List<Checkable>> {
        return dao.getCheckablesByTask(taskId)
    }

    override fun insertCheckable(checkable: Checkable) {
        dao.insertCheckable(checkable)
    }
}