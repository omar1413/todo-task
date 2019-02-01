package com.example.omar.taskmanager.dagger.module

import androidx.fragment.app.DialogFragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.omar.taskmanager.dagger.scope.DialogFragmentScope
import com.example.omar.taskmanager.ui.tasks.TaskCreateViewModel
import com.example.omar.taskmanager.ui.tasks.TaskDialog
import dagger.Module
import dagger.Provides


@Module
class TaskDialogModule(var fragment: TaskDialog){


    @DialogFragmentScope
    @Provides
    fun getTaskCreateViewModel():TaskCreateViewModel{
        return ViewModelProviders.of(fragment).get(TaskCreateViewModel::class.java)
    }
}