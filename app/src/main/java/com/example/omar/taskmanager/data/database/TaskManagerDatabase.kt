package com.example.omar.taskmanager.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.omar.taskmanager.data.database.dao.CommentDao
import com.example.omar.taskmanager.data.database.dao.TaskDao
import com.example.omar.taskmanager.data.database.dao.UserDao
import com.example.omar.taskmanager.data.database.tables.Comment
import com.example.omar.taskmanager.data.database.tables.Task
import com.example.omar.taskmanager.data.database.tables.User
import com.example.omar.taskmanager.utils.DateConverter


@Database(entities = [User::class, Task::class, Comment::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class TaskManagerDatabase : RoomDatabase() {

    companion object {
        private var instance: TaskManagerDatabase? = null

        @Synchronized
        fun getInstance(context: Context): TaskManagerDatabase {

            if (instance == null) {
                instance = Room.databaseBuilder(context, TaskManagerDatabase::class.java, "task_manager_database")
                    .build()
            }

            return instance!!
        }
    }

    abstract fun taskDao():TaskDao
    abstract fun userDao():UserDao
    abstract fun commentDao():CommentDao
}