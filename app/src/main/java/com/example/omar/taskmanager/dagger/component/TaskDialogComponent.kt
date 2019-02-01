package com.example.omar.taskmanager.dagger.component

import androidx.fragment.app.DialogFragment
import com.example.omar.taskmanager.dagger.module.TaskDialogModule
import com.example.omar.taskmanager.dagger.scope.DialogFragmentScope
import com.example.omar.taskmanager.ui.tasks.TaskCreateViewModel
import com.example.omar.taskmanager.ui.tasks.TaskDialog
import dagger.Component


@Component(modules = [TaskDialogModule::class], dependencies = [AppComponent::class])
@DialogFragmentScope
interface TaskDialogComponent{

    fun inject(taskDialogFragment: TaskDialog)
}