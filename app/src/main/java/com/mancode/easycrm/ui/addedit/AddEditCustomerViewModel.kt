package com.mancode.easycrm.ui.addedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mancode.easycrm.db.CustomerRaw
import com.mancode.easycrm.db.dao.CustomerDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditCustomerViewModel @Inject constructor(
    private val customerDao: CustomerDao
) : ViewModel() {

    fun insertCustomer(name: String) {
        viewModelScope.launch {
            customerDao.insertCustomer(
                CustomerRaw(0, name)
            )
        }
    }

    fun updateCustomer(id: Int, newName: String) {
        viewModelScope.launch {
            customerDao.updateCustomer(
                CustomerRaw(id, newName)
            )
        }
    }
}