package com.example.shoppinglist.ui.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(
    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "notify_enabled")
    var notifyenabled: Boolean,

    @ColumnInfo(name = "datatime")
    var datatime: Long?,

    @ColumnInfo(name = "is_checked")
    var isChecked: Boolean = false,

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
)