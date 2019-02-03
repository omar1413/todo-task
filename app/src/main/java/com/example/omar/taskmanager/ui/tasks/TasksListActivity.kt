package com.example.omar.taskmanager.ui.tasks

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.omar.taskmanager.R
import com.example.omar.taskmanager.TaskManagerApp
import com.example.omar.taskmanager.dagger.component.DaggerActivityComponent
import com.example.omar.taskmanager.dagger.module.ActivityModule
import com.example.omar.taskmanager.data.database.tables.Task
import com.example.omar.taskmanager.ui.task_details.TaskDetailsActivity
import com.example.omar.taskmanager.utils.TaskItemDiffUtilityCallback
import com.example.omar.taskmanager.utils.TaskListUpdateCallback
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_task_details.*
import kotlinx.android.synthetic.main.activity_tasks_list.*
import javax.inject.Inject

class TasksListActivity : AppCompatActivity() {

    @Inject
    lateinit var adapter: GroupAdapter<ViewHolder>

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var taskListVm: TasksListViewModel


    var recyclerItemTouchHelperListner = object :RecyclerItemTouchHelper.RecyclerItemTouchHelperListner{
        override fun onSwiped(pos: Int) {
           taskListVm.deleteTask(tasks.get(pos).task).subscribe({},{})
        }

    }

    var callbackTaskItem = object : TaskItem.CallbackTaskItem {
        override fun taskClicked(task: Task) {
            saveCurrentTask(task)
            gotoTaskDetailsActivity()
        }

        override fun taskUpdated(task: Task) {
            taskListVm.updateTask(task).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("", "")
                }, {
                    Log.d("", "")
                })
        }

    }

    private fun saveCurrentTask(task:Task) {

        taskListVm.saveCurrentTask(task)

    }

    private fun gotoTaskDetailsActivity() {
        val intent = Intent(this,TaskDetailsActivity::class.java)
        startActivity(intent)

    }

    var isFiltered = false

    var tasks: MutableList<TaskItem> = mutableListOf()
    var filteredTasks: MutableList<TaskItem> = mutableListOf()
    var unfilteredTasks: MutableList<TaskItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks_list)


        DaggerActivityComponent.builder().activityModule(ActivityModule(this))
            .appComponent((application as TaskManagerApp).component)
            .build().inject(this)


        add_task_btn.setOnClickListener {
            showDialog()
        }



        setupRecycler()

        val g = this
        taskListVm.getCurrentUser()?.observeOn(AndroidSchedulers.mainThread())?.subscribe({

            title_tool_bar.text = it.username + "'s " + "Tasks"
            taskListVm.getTasks(it.id).observe(this, object : Observer<List<Task>> {
                override fun onChanged(t: List<Task>?) {


                    if (t != null) {
                        filteredTasks.clear()
                        unfilteredTasks.clear()
                        for (task in t) {
                            val taskItem = TaskItem(task, callbackTaskItem)
                            if (task.status == Task.Status.DONE) {
                                filteredTasks.add(taskItem)
                            }
                            unfilteredTasks.add(taskItem)

                        }

                        updateTasksList()
                    }
                }

            })
        }, {

        })

        setupToolpar()
    }

    private fun setupToolpar(){
        setSupportActionBar(task_list_toolbar)
        filter_action_toolbar.setOnClickListener {
            isFiltered = !isFiltered

            if(isFiltered){
                filter_action_toolbar.setBackgroundResource(R.drawable.filter_blue)
            }else{
                filter_action_toolbar.setBackgroundResource(R.drawable.filter_black)
            }
            updateTasksList()
        }
        if(supportActionBar != null) {
            supportActionBar!!.setTitle("")
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        }
    }

    private fun updateTasksList(){
        if (isFiltered) {
            listDiff(tasks, filteredTasks)
        } else {
            listDiff(tasks,unfilteredTasks)
        }

        adapter.update(tasks)
    }

    private fun listDiff(list1: MutableList<TaskItem>, list2: MutableList<TaskItem> = mutableListOf()) {
        val listDiff = TaskItemDiffUtilityCallback(list1, list2)
        val calcDiff = DiffUtil.calculateDiff(listDiff)
        val listUpdate = TaskListUpdateCallback(list1, list2)
        calcDiff.dispatchUpdatesTo(listUpdate)
    }

    private fun setupRecycler() {
        tasks_recycler.layoutManager = linearLayoutManager
        ItemTouchHelper(RecyclerItemTouchHelper(0,ItemTouchHelper.LEFT,recyclerItemTouchHelperListner)).attachToRecyclerView(tasks_recycler)
        tasks_recycler.adapter = adapter

    }

    private fun showDialog() {
        val fm = supportFragmentManager
        val taskDialog = TaskDialog()
        taskDialog.show(fm, "fragment_dialog")
    }



}
