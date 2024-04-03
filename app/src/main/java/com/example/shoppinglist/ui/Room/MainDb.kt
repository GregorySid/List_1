package com.example.shoppinglist.ui.Room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class MainDb : RoomDatabase() {
    abstract fun getDao(): Dao
}
