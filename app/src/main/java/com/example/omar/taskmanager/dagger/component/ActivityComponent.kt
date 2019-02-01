package com.example.omar.taskmanager.dagger.component

import androidx.appcompat.app.AppCompatActivity
import com.example.omar.taskmanager.ui.login.LoginActivity
import com.example.omar.taskmanager.dagger.module.ActivityModule
import com.example.omar.taskmanager.dagger.scope.ActivityScope
import com.example.omar.taskmanager.ui.tasks.TasksListActivity
import dagger.Component


@Component(modules = [ActivityModule::class], dependencies = [AppComponent::class])
@ActivityScope
interface ActivityComponent{

    fun inject(activity: LoginActivity)
    fun inject(activity: TasksListActivity)
}