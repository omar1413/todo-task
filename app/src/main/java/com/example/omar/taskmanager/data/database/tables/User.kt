package com.example.omar.taskmanager.data.database.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "user_table", indices = arrayOf(Index(value = ["user_name"], unique = true)))
class User(
    @ColumnInfo(name = "user_name") var username: String
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}