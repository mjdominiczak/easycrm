package com.mancode.easycrm.db

import androidx.room.Entity

@Entity(tableName = "contacts", primaryKeys = ["contactLookupKey", "customerId"])
data class Contact(
    val contactLookupKey: String,
    val customerId: Int,
    val name: String,
    val phoneNumber: String? = null,
    val email: String? = null,
    val position: String? = null,
    val hasPhoneNumber: Boolean = false
)
