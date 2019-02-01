package com.example.omar.taskmanager.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.omar.taskmanager.data.database.tables.User
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface UserDao{

    @Insert
    fun insertUser(user:User): Completable

    @Query("Select * From user_table where user_name = :name")
    fun getUser(name:String): Single<User>

    @Query("Select * From user_table where id = :id")
    fun getUser(id:Int): Single<User>


}