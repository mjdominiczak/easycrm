package com.mancode.easycrm.db.dao

import androidx.room.*
import com.mancode.easycrm.db.*
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

    @Delete
    suspend fun deleteCustomer(customer: CustomerRaw)

    @Delete
    suspend fun deleteAddress(address: Address)

    @Delete
    suspend fun deleteContacts(contacts: List<Contact>)

    @Delete
    suspend fun deleteNotes(notes: List<Note>)
}