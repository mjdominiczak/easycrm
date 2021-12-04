package com.mancode.easycrm.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mancode.easycrm.db.Contact
import com.mancode.easycrm.db.InstantConverter
import com.mancode.easycrm.db.Note
import com.mancode.easycrm.db.Task
import com.mancode.easycrm.db.dao.ContactDao
import com.mancode.easycrm.db.dao.CustomerDao
import com.mancode.easycrm.db.dao.NoteDao
import com.mancode.easycrm.db.dao.TaskDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import javax.inject.Inject

@HiltViewModel
class CustomerDetailViewModel @Inject constructor(
    private val noteDao: NoteDao,
    private val customerDao: CustomerDao,
    private val contactDao: ContactDao,
    private val taskDao: TaskDao
) : ViewModel() {

    var customerId: Int? = null
    var noteId: Int = 0
    val customer by lazy { customerDao.getCustomerById(customerId!!) }

    fun getNotes() = noteDao.getNotesForCustomer(customerId!!)
    fun getTasks() = taskDao.getTasksForCustomer(customerId!!)

    fun getNoteToUpdate() = noteDao.getNoteById(noteId)

    fun insertContact(lookupKey: String, name: String, hasPhoneNumber: Boolean) =
        viewModelScope.launch {
            contactDao.insertContact(
                Contact(
                    contactLookupKey = lookupKey,
                    customerId = customerId!!,
                    name = name,
                    hasPhoneNumber = hasPhoneNumber
                )
            )
        }

    fun deleteContact(contact: Contact) {
        viewModelScope.launch {
            contactDao.deleteContact(contact)
        }
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

    fun insertTask(description: String) {
        viewModelScope.launch {
            taskDao.insertTask(
                Task(
                    id = 0,
                    customerId = customerId!!,
                    description = description
                )
            )
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskDao.deleteTask(task)
        }
    }

    fun flipTaskChecked(task: Task) {
        viewModelScope.launch {
            taskDao.updateTask(
                task.copy(done = !task.done)
            )
        }
    }

    fun updateTaskDate(task: Task, stamp: Long) {
        viewModelScope.launch {
            val instant = InstantConverter.toInstant(stamp)
            instant?.let {
                val updated = task.copy(dueDate = instant)
                taskDao.updateTask(updated)
            }
        }
    }

    fun updateLastContactDate(stamp: Long) {
        viewModelScope.launch {
            val instant = InstantConverter.toInstant(stamp)
            instant?.let {
                val updated = customer.first().raw.copy(dateLastContacted = instant)
                customerDao.updateCustomer(updated)
            }
        }
    }

    fun updateNextContactDate(stamp: Long) {
        viewModelScope.launch {
            val instant = InstantConverter.toInstant(stamp)
            instant?.let {
                val updated = customer.first().raw.copy(dateNextContact = instant)
                customerDao.updateCustomer(updated)
            }
        }
    }
}