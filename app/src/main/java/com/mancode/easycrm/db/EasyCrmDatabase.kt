package com.mancode.easycrm.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(InstantConverter::class)
@Database(entities = [CustomerRaw::class, Address::class, Contact::class, Note::class], version = 1)
abstract class EasyCrmDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}