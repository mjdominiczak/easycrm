package com.mancode.easycrm.ui.details

import androidx.lifecycle.ViewModel
import com.mancode.easycrm.db.EasyCrmDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CustomerDetailViewModel @Inject constructor(
    private val db: EasyCrmDatabase
) : ViewModel() {

    fun getAllNotes() = db.noteDao().getAllNotes()
}