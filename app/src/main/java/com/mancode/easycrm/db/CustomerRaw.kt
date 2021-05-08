package com.mancode.easycrm.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.Instant

@Entity(tableName = "customers")
data class CustomerRaw(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val webpageUrl: String,
    val dateAdded: Instant = Instant.now(),
    val dateLastContacted: Instant? = null,
    val dateNextContact: Instant? = null
)
