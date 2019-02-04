package com.example.omar.taskmanager.ui.tasks

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.omar.taskmanager.R
import com.example.omar.taskmanager.TaskManagerApp
import com.example.omar.taskmanager.dagger.component.DaggerTaskDialogComponent
import com.example.omar.taskmanager.dagger.module.TaskDialogModule
import com.example.omar.taskmanager.data.database.tables.Task
import com.example.omar.taskmanager.data.database.tables.User
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_tasks_list.view.*
import kotlinx.android.synthetic.main.create_task_dialog.view.*
import java.util.*
import javax.inject.Inject

class TaskDialog(): DialogFragment(){

    @Inject
    lateinit var taskCreateViewModel: TaskCreateViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.create_task_dialog,container, false)
        DaggerTaskDialogComponent.builder().appComponent((v.context.applicationContext as TaskManagerApp).component)
            .taskDialogModule(TaskDialogModule(this))
            .build().inject(this)
        return v
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = taskCreateViewModel.getCurrentUser()?.observeOn(AndroidSchedulers.mainThread())?.subscribe{
            it,th->

            if (it != null){
                onClickCreate(view, it)
            }
            else{
                //handle error
                Log.d("", "")

            }
        }

    }

    fun onClickCreate(view: View, user: User){
        view.task_create_btn.setOnClickListener {
            val taskTitle = view.task_title_edtxt.text.toString()
            if(taskTitle.trim().isEmpty()){
                Toast.makeText(context, "Enter a task title, plz", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            taskCreateViewModel.createTask(Task(taskTitle,Task.Status.IN_PROGRESS,user.id)).subscribe(
                {
                    //done
                    Log.d("", "")
                    dismiss()
                },
                {
                    //handle error
                    Log.d("", "")
                }
            )


        }
    }
}