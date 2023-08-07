package com.ec25p5e.notesapp.feature_task.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ec25p5e.notesapp.feature_task.domain.models.Checkable
import kotlinx.coroutines.flow.Flow

@Dao
interface CheckableDao {

    @Query("SELECT * FROM checkable WHERE taskId = :taskId")
    fun getCheckablesByTask(taskId: Int): List<Checkable>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBulkCheckable(checkables: List<Checkable>)
}