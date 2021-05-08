package com.mancode.easycrm.db.dao

import androidx.room.Dao
import androidx.room.Insert
import com.mancode.easycrm.db.CustomerRaw

@Dao
interface CustomerDao {
    @Insert
    suspend fun insertCustomer(customer: CustomerRaw)

    @Insert
    suspend fun insertAll(customers: List<CustomerRaw>)
}