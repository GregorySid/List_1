package com.example.shoppinglist.ui.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insItem(item: Item): Long

    @Query("SELECT * FROM items ORDER BY id")
    fun getItems(): Flow<List<Item>>

    @Query("UPDATE ITEMS SET is_checked=:checked WHERE id=:id")
    suspend fun setChecked(id: Long, checked: Boolean)

    @Query("DELETE FROM items WHERE ID =:id")
    suspend fun deleteById(id: Long)
}
