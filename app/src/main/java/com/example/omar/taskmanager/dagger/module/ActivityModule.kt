package com.example.omar.taskmanager.dagger.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.omar.taskmanager.ui.login.LoginActivity
import com.example.omar.taskmanager.ui.login.LoginViewModel
import com.example.omar.taskmanager.dagger.qualifier.ActivityContext
import com.example.omar.taskmanager.dagger.scope.ActivityScope
import com.example.omar.taskmanager.ui.task_details.TaskDetailsViewModel
import com.example.omar.taskmanager.ui.tasks.TasksListViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import dagger.Module
import dagger.Provides

@Module
class ActivityModule( val activity: AppCompatActivity){

    @ActivityScope
    @Provides
    fun getLoginViewModel():LoginViewModel{
        return ViewModelProviders.of(activity).get(LoginViewModel::class.java)
    }


    @ActivityScope
    @Provides
    fun getGroupieAdapter():GroupAdapter<ViewHolder>{
        return GroupAdapter()
    }

    @ActivityScope
    @Provides
    fun getLinearLayoutManager(@ActivityContext context: Context):LinearLayoutManager{
        return LinearLayoutManager(context)
    }

    @ActivityContext
    @ActivityScope
    @Provides
    fun getContext():Context{
        return activity
    }


    @ActivityScope
    @Provides
    fun getactivity():AppCompatActivity{
        return activity
    }

    @ActivityScope
    @Provides
    fun getTaskListViewModel():TasksListViewModel{
        return ViewModelProviders.of(activity).get(TasksListViewModel::class.java)
    }

    @ActivityScope
    @Provides
    fun getTaskDetailsViewModel():TaskDetailsViewModel{
        return ViewModelProviders.of(activity).get(TaskDetailsViewModel::class.java)
    }
}