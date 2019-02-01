package com.example.omar.taskmanager.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.omar.taskmanager.TaskManagerApp
import com.example.omar.taskmanager.data.Repository
import javax.inject.Inject

abstract class ViewModelBase(application: Application): AndroidViewModel(application){
    @Inject
    lateinit var repository: Repository

    init {
        (application as TaskManagerApp).component?.inject(this)
    }
}