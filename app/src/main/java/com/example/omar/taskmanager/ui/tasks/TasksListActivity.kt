package com.example.omar.taskmanager.ui.tasks

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
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
import kotlinx.android.synthetic.main.activity_tasks_list.*
import javax.inject.Inject

class TasksListActivity : AppCompatActivity() {

    @Inject
    lateinit var adapter: GroupAdapter<ViewHolder>

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var taskListVm: TasksListViewModel

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


            taskListVm.getTasks(5).observe(g,object :Observer<List<Task>>{
                override fun onChanged(t: List<Task>?) {

                }
            })
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


//                    if(t != null){
//                        var shouldGoUp = false;
//                        var counter = 0
//                        for (task in t){
//                            if (tasks?.size!! > counter ){
//                                val realCounter = tasks?.size - counter - 1
//                                if (tasks?.get(realCounter).task.equals(task)){
//
//                                }else{
//                                    tasks?.removeAt(realCounter)
//                                    tasks?.add(realCounter, TaskItem(task,callbackTaskItem))
//                                }
//
//                            }else{
//                                tasks.add(0,TaskItem(task,callbackTaskItem))
//                                shouldGoUp = true
//                            }
//
//                            counter++
//                        }
//                        adapter.update(tasks)
//                        if (shouldGoUp) {
//                            tasks_recycler.scrollToPosition(0)
//                        }
//                    }
                }

            })
        }, {

        })
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
        tasks_recycler.adapter = adapter

    }

    private fun showDialog() {
        val fm = supportFragmentManager
        val taskDialog = TaskDialog()
        taskDialog.show(fm, "fragment_dialog")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if (id == R.id.filter) {
            isFiltered = !isFiltered

            updateTasksList()
        }
        return super.onOptionsItemSelected(item)
    }
}
