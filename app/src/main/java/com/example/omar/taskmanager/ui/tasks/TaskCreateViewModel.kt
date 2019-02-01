package com.example.omar.taskmanager.ui.tasks

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.omar.taskmanager.data.database.tables.Task
import com.example.omar.taskmanager.data.database.tables.User
import com.example.omar.taskmanager.ui.base.ViewModelBase
import io.reactivex.Completable
import io.reactivex.Single

class TaskCreateViewModel(application: Application): ViewModelBase(application){


    fun createTask(task:Task):Completable{
        return repository.insertTask(task)
    }

    fun getCurrentUser(): Single<User>? {
        return repository.getCurrentUser()
    }

}