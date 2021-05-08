package com.mancode.easycrm.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey(autoGenerate = true) val contactId: Int,
    val customerId: Int,
    val name: String,
    val phoneNumber: String? = null,
    val email: String? = null,
    val position: String? = null
)
