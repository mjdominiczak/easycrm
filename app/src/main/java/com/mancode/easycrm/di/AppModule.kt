package com.mancode.easycrm.di

import android.app.Application
import androidx.room.Room
import com.mancode.easycrm.db.EasyCrmDatabase
import com.mancode.easycrm.db.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDb(app: Application): EasyCrmDatabase {
        return Room.databaseBuilder(app, EasyCrmDatabase::class.java, "easycrm-database")
            .build()
    }

    @Singleton
    @Provides
    fun provideNoteDao(db: EasyCrmDatabase): NoteDao {
        return db.noteDao()
    }
}