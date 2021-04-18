package com.mancode.easycrm

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mancode.easycrm.data.notes
import com.mancode.easycrm.db.EasyCrmDatabase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var db: EasyCrmDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            db.noteDao().insertAll(notes)
            val list = db.noteDao().getAllNotes()
            for (note in list) {
                Log.d("ROOM TEST", note.toString())
            }
        }
    }
}
