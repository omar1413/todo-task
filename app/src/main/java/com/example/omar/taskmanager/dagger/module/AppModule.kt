package com.example.omar.taskmanager.dagger.module

import android.content.Context
import android.content.SharedPreferences
import com.example.omar.taskmanager.TaskManagerApp
import com.example.omar.taskmanager.dagger.qualifier.ApplicationContext
import com.example.omar.taskmanager.dagger.qualifier.SharedInfo
import com.example.omar.taskmanager.dagger.scope.AppScope
import com.example.omar.taskmanager.data.Repository
import com.example.omar.taskmanager.data.database.TaskManagerDatabase
import com.example.omar.taskmanager.data.shared_pref.AppSharedPref
import dagger.Module
import dagger.Provides


@Module
class AppModule(val context: Context){

    @AppScope
    @Provides
    fun getRepository(taskManagerDatabase: TaskManagerDatabase, appSharedPref: AppSharedPref):Repository{
        return Repository(taskManagerDatabase,appSharedPref)
    }

    @AppScope
    @Provides
    fun getSharedPref(@ApplicationContext context: Context, @SharedInfo name: String): SharedPreferences{
        return context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    @AppScope
    @Provides
    fun getTaskManagerDatabase():TaskManagerDatabase{
        return TaskManagerDatabase.getInstance(context)
    }

    @SharedInfo
    @AppScope
    @Provides
    fun getSharedPrefName():String{
        return "shared_app_pref"
    }

    @ApplicationContext
    @AppScope
    @Provides
    fun getAppContext():Context{
        return context
    }
}