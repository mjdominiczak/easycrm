package com.mancode.easycrm

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.mancode.easycrm.data.notes
import com.mancode.easycrm.db.EasyCrmDatabase
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Room.databaseBuilder(
            baseContext,
            EasyCrmDatabase::class.java, "easycrm-database"
        ).build()
        lifecycleScope.launch {
            db.noteDao().insertAll(notes)
            val list = db.noteDao().getAllNotes()
            for (note in list) {
                Log.d("ROOM TEST", note.toString())
            }
        }
    }
}
