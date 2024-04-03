package com.example.shoppinglist

import android.app.Application
import androidx.room.Room
import com.example.shoppinglist.ui.Room.MainDb

class App: Application() {
    lateinit var factory: ViewMFactory

    override fun onCreate() {
        super.onCreate()
        val room = Room.databaseBuilder(
            this, MainDb::class.java, "database")
            .build()
        factory = ViewMFactory(room)

    }
}