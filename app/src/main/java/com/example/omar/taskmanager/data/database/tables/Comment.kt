package com.example.omar.taskmanager.data.database.tables

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.example.omar.taskmanager.utils.DateConverter
import java.util.*

@Entity(
    tableName = "comment_table",
    foreignKeys = [ForeignKey(
        entity = Task::class,
        parentColumns = ["id"],
        childColumns = ["task_id"],
        onDelete = CASCADE
    )]
)
class Comment(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var content: String,
    var date: Date,
    @ColumnInfo(name = "task_id") var taskId: Int
)