package com.example.omar.taskmanager.dagger.component

import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.example.omar.taskmanager.TaskManagerApp
import com.example.omar.taskmanager.ui.login.LoginViewModel
import com.example.omar.taskmanager.dagger.module.AppModule
import com.example.omar.taskmanager.dagger.scope.AppScope
import com.example.omar.taskmanager.data.Repository
import com.example.omar.taskmanager.ui.base.ViewModelBase
import dagger.Component


@Component(modules = [AppModule::class])
@AppScope
interface AppComponent{
    fun inject(taskManagerApp: TaskManagerApp)

    fun inject(viewModelBase: ViewModelBase)

    fun getInputManage(): InputMethodManager
}