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
import com.mancode.easycrm.db.dao.CustomerDao
import com.mancode.easycrm.ui.list.SortOrder.BY_NAME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomersListViewModel @Inject constructor(
    private val customerDao: CustomerDao
) : ViewModel() {

    val customers = customerDao.getCustomers()

    private val _searchActivated = MutableStateFlow(false)
    val searchActivated = _searchActivated.asStateFlow()

    fun onSearchStateChanged(active: Boolean) {
        _searchActivated.value = active
    }

    val filterState = mutableStateOf(TextFieldValue(""))
    var sortOrder = mutableStateOf(BY_NAME)

    fun populateDB() {
        viewModelScope.launch {
            customerDao.insertAddresses(addresses)
            customerDao.insertContacts(contacts)
            customerDao.insertAll(customersRaw)
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

enum class SortOrder {
    BY_NAME, BY_NEXT_CONTACT_DATE
}