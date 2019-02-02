package com.example.omar.taskmanager.ui.task_details

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.omar.taskmanager.data.database.tables.Comment
import com.example.omar.taskmanager.data.database.tables.Task
import com.example.omar.taskmanager.ui.base.ViewModelBase
import io.reactivex.Completable
import io.reactivex.Single

class TaskDetailsViewModel(application: Application):ViewModelBase(application){

    fun getCurrentTask(): LiveData<Task>?{
        return repository.getCurerntTask()
    }


    fun updateTask(task: Task):Completable{
        return repository.updateTask(task)
    }

    fun getComments(id: Int): LiveData<List<Comment>> {
        return repository.getComments(id)
    }


}