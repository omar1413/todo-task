package com.example.omar.taskmanager.data.database.tables

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.example.omar.taskmanager.utils.DateConverter
import java.util.*

@Entity(
    tableName = "task_table",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["user_id"],
        onDelete = CASCADE
    )]
)
class Task(
    var title: String,
    var status: Int,
    var date: Date,
    var priority: Int,
    @ColumnInfo(name = "user_id") var userId: Int
){


    @PrimaryKey(autoGenerate = true) var id: Int = 0

  class Status{
      companion object {
          val DONE = 1
          val IN_PROGRESS = 2
      }
  }

    class Priority{
        companion object {
            val LOW = 1
            val MEDIUM = 2
            val HIGH = 3
        }
    }

    override fun equals(other: Any?): Boolean {
        val task = other as Task
        if (title.equals(task.title) && status == task.status && priority == task.priority){
            return true
        }

        return false
    }
}