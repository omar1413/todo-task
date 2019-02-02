package com.example.omar.taskmanager.data

import androidx.lifecycle.LiveData
import com.example.omar.taskmanager.data.database.TaskManagerDatabase
import com.example.omar.taskmanager.data.database.tables.Comment
import com.example.omar.taskmanager.data.database.tables.Task
import com.example.omar.taskmanager.data.database.tables.User
import com.example.omar.taskmanager.data.shared_pref.AppSharedPref
import io.reactivex.*
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class Repository(val taskManagerDatabase: TaskManagerDatabase, val appSharedPref: AppSharedPref){

    fun getUser(name: String): Single<User> {
        return taskManagerDatabase.userDao().getUser(name).subscribeOn(Schedulers.io())
    }

    fun getUser(id: Int): Single<User> {
        return taskManagerDatabase.userDao().getUser(id).subscribeOn(Schedulers.io())
    }

    fun insertUser(user:User):Completable{
        return taskManagerDatabase.userDao().insertUser(user).subscribeOn(Schedulers.io())
    }

    fun insertTask(task: Task): Completable {
        return taskManagerDatabase.taskDao().insertTask(task).subscribeOn(Schedulers.io())
    }

    fun getCurrentUser(): Single<User>? {
        val id = appSharedPref.getCurrentUser()
        if (id != null){
            return getUser(id)
        }

        val s = Single.create<User> {
            it.onError(Throwable())
        }

        return s
    }

    fun setCurrentUser(user: User){
        appSharedPref.setCurrentUser(user.id)
    }

    fun getAllTasks(userId: Int):LiveData<List<Task>>{
        return taskManagerDatabase.taskDao().getAllTasks(userId)
    }

    fun updateTask(task: Task): Completable {
        return taskManagerDatabase.taskDao().updateTask(task).subscribeOn(Schedulers.io())
    }

    fun saveCurrentTask(task: Task) {
        appSharedPref.saveCurrentTask(task.id)
    }

    fun getCurerntTask():LiveData<Task>?{
        val id = appSharedPref.getCurrentTask()
        if (id != null) {
            return taskManagerDatabase.taskDao().getTask(id)
        }

        return null
    }

    fun getComments(id: Int): LiveData<List<Comment>> {
        return taskManagerDatabase.commentDao().getAllComments(id)
    }

}