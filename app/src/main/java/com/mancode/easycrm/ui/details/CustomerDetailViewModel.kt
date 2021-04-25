package com.mancode.easycrm.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mancode.easycrm.db.Note
import com.mancode.easycrm.db.NoteDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import javax.inject.Inject

@HiltViewModel
class CustomerDetailViewModel @Inject constructor(
    private val noteDao: NoteDao
) : ViewModel() {

    var customerId: Int? = null
    var noteId: Int = 0

    fun getNotes() = noteDao.getNotesForCustomer(customerId!!)

    fun getNoteToUpdate() = noteDao.getNoteById(noteId)

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