package com.mancode.easycrm.db.dao

import androidx.room.*
import com.mancode.easycrm.db.Address
import com.mancode.easycrm.db.Contact
import com.mancode.easycrm.db.Customer
import com.mancode.easycrm.db.CustomerRaw
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {

    @Transaction
    @Query("SELECT * FROM customers")
    fun getCustomers(): Flow<List<Customer>>

    @Transaction
    @Query("SELECT * FROM customers WHERE id = :id")
    fun getCustomerById(id: Int): Flow<Customer>

    @Insert
    suspend fun insertCustomer(customer: CustomerRaw)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(customers: List<CustomerRaw>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAddresses(address: List<Address>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertContacts(contacts: List<Contact>)
}