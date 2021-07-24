package com.mancode.easycrm.ui.list

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mancode.easycrm.data.addresses
import com.mancode.easycrm.data.contacts
import com.mancode.easycrm.data.customersRaw
import com.mancode.easycrm.db.Customer
import com.mancode.easycrm.db.CustomerRaw
import com.mancode.easycrm.db.dao.CustomerDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomersListViewModel @Inject constructor(
    private val customerDao: CustomerDao
) : ViewModel() {

    val customers = customerDao.getCustomers()

    // VM probably shouldn't keep state (?)
    val filterState = mutableStateOf(TextFieldValue(""))

    fun populateDB() {
        viewModelScope.launch {
            customerDao.insertAddresses(addresses)
            customerDao.insertContacts(contacts)
            customerDao.insertAll(customersRaw)
        }
    }

    fun insertCustomer(name: String) {
        viewModelScope.launch {
            customerDao.insertCustomer(
                CustomerRaw(0, name)
            )
        }
    }

    fun deleteCustomer(customer: Customer) {
        viewModelScope.launch {
            Log.e("DELETE", "Before deleteCustomer")
            customerDao.deleteCustomer(customer.raw)
            Log.e("DELETE", "After deleteCustomer")
            customer.address?.let { customerDao.deleteAddress(it) }
            customerDao.deleteContacts(customer.contacts)
            customerDao.deleteNotes(customer.notes)
        }
    }

}
