package com.example.omar.taskmanager.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.omar.taskmanager.TaskManagerApp
import com.example.omar.taskmanager.data.Repository
import com.example.omar.taskmanager.data.database.tables.User
import com.example.omar.taskmanager.ui.base.ViewModelBase
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class LoginViewModel(application: Application): ViewModelBase(application){



    fun insertUser(user: User):Completable?{
        return repository.insertUser(user)
    }

    fun getUser(name:String):Single<User>?{

        return repository.getUser(name)
    }

    fun setCurrentUser(user: User){
        repository.setCurrentUser(user)
    }

}