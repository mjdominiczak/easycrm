package com.mancode.easycrm.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.Instant

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val customerId: Int,
    val description: String,
    val done: Boolean = false,
    val dueDate: Instant? = null
)