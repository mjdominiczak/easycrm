package com.mancode.easycrm.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes WHERE customerId = :id ORDER BY timestamp ASC")
    fun getNotesForCustomer(id: Int): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(notes: List<Note>)

    @Insert
    suspend fun insertNote(note: Note)
}
