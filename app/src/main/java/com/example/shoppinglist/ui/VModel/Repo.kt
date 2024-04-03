package com.example.shoppinglist.ui.VModel

import com.example.shoppinglist.ui.Room.MainDb

class Repo(private val db: MainDb) {
    suspend fun dalete(id: Long) = db.getDao().deleteById(id)
}