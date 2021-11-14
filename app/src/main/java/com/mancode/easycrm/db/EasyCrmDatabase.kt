package com.mancode.easycrm.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mancode.easycrm.db.dao.ContactDao
import com.mancode.easycrm.db.dao.CustomerDao
import com.mancode.easycrm.db.dao.NoteDao
import com.mancode.easycrm.db.dao.TaskDao

@TypeConverters(InstantConverter::class)
@Database(
    entities = [CustomerRaw::class, Address::class, Contact::class, Note::class, Task::class],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
abstract class EasyCrmDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun contactDao(): ContactDao
    abstract fun customerDao(): CustomerDao
    abstract fun taskDao(): TaskDao
}