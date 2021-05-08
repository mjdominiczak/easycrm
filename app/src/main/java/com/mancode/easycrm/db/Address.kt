package com.mancode.easycrm.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "addresses")
data class Address(
    @PrimaryKey(autoGenerate = true) val addressId: Int,
    val street: String,
    val city: String,
    val country: String
)
