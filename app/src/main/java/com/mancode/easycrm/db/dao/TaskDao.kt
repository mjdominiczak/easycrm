package com.mancode.easycrm.db.dao

import androidx.room.*
import com.mancode.easycrm.db.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks WHERE customerId = :id")
    fun getTasksForCustomer(id: Int): Flow<List<Task>>

    @Insert
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
}