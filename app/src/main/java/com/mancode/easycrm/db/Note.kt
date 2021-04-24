package com.mancode.easycrm.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.Instant

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val customerId: Int,
    val timestamp: Instant,
    val text: String
)
