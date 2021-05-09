package com.mancode.easycrm.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mancode.easycrm.data.addresses
import com.mancode.easycrm.data.contacts
import com.mancode.easycrm.data.customersRaw
import com.mancode.easycrm.db.dao.CustomerDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomersListViewModel @Inject constructor(
    private val customerDao: CustomerDao
) : ViewModel() {

    val customers = customerDao.getCustomers()

    fun populateDB() {
        viewModelScope.launch {
            customerDao.insertAddresses(addresses)
            customerDao.insertContacts(contacts)
            customerDao.insertAll(customersRaw)
        }
    }
}
