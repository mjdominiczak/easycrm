package com.mancode.easycrm.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes WHERE customerId = :id ORDER BY timestamp ASC")
    fun getNotesForCustomer(id: Int): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNoteById(id: Int): Flow<Note>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(notes: List<Note>)

    @Insert
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)
}
