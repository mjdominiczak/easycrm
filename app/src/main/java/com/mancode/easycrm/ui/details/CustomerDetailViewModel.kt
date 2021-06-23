package com.mancode.easycrm.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mancode.easycrm.db.Contact
import com.mancode.easycrm.db.Note
import com.mancode.easycrm.db.NoteDao
import com.mancode.easycrm.db.dao.ContactDao
import com.mancode.easycrm.db.dao.CustomerDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import javax.inject.Inject

@HiltViewModel
class CustomerDetailViewModel @Inject constructor(
    private val noteDao: NoteDao,
    private val customerDao: CustomerDao,
    private val contactDao: ContactDao
) : ViewModel() {

    var customerId: Int? = null
    var noteId: Int = 0

    fun getCustomer() = customerDao.getCustomerById(customerId!!)

    fun getNotes() = noteDao.getNotesForCustomer(customerId!!)

    fun getNoteToUpdate() = noteDao.getNoteById(noteId)

    fun insertContact(lookupKey: String, name: String, phoneNumber: String) = viewModelScope.launch {
            contactDao.insertContact(
                Contact(lookupKey, customerId!!, name, phoneNumber)
            )
        }

    fun insertNote(time: LocalDateTime, note: String) {
        viewModelScope.launch {
            noteDao.insertNote(
                Note(0, customerId!!, time.atZone(ZoneId.systemDefault()).toInstant(), note)
            )
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            noteDao.updateNote(note)
        }
    }
}