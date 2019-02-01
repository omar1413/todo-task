package com.example.omar.taskmanager

import android.app.Application
import com.example.omar.taskmanager.dagger.component.AppComponent
import com.example.omar.taskmanager.dagger.component.DaggerAppComponent
import com.example.omar.taskmanager.dagger.module.AppModule

class TaskManagerApp: Application() {

    var component:AppComponent? = null

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        component?.inject(this)
    }
}