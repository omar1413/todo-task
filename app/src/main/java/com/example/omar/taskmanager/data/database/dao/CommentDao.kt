package com.example.omar.taskmanager.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.omar.taskmanager.data.database.tables.Comment
import io.reactivex.Completable

@Dao
interface CommentDao{

    @Insert
    fun insertComment(comment: Comment):Completable

    @Query("Select * from comment_table")
    fun getAllComments():LiveData<List<Comment>>
}