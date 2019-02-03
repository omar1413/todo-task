package com.example.omar.taskmanager.ui.tasks

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.omar.taskmanager.data.database.tables.Task
import com.example.omar.taskmanager.data.database.tables.User
import com.example.omar.taskmanager.ui.base.ViewModelBase
import io.reactivex.Completable
import io.reactivex.Single

class TasksListViewModel(application: Application): ViewModelBase(application){

    fun getTasks(userId: Int): LiveData<List<Task>>{
        return repository.getAllTasks(userId)
    }

    fun getCurrentUser(): Single<User>? {
        return repository.getCurrentUser()
    }

    fun updateTask(task: Task):Completable {
        return repository.updateTask(task)
    }

    fun saveCurrentTask(task: Task) {
        repository.saveCurrentTask(task)
    }

    fun deleteTask(task: Task): Completable {
        return repository.deleteTask(task)
    }

}