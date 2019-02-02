package com.example.omar.taskmanager.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.omar.taskmanager.data.database.tables.Task
import io.reactivex.Completable


@Dao
interface TaskDao{


    @Insert
    fun insertTask(task: Task): Completable

    @Update
    fun updateTask(task: Task):Completable

    @Delete
    fun deleteTask(task: Task):Completable

    @Query("Select * From task_table where id = :id")
    fun getTask(id: Int):LiveData<Task>

    @Query("Select * From task_table where user_id = :userId")
    fun getAllTasks(userId: Int): LiveData<List<Task>>
}