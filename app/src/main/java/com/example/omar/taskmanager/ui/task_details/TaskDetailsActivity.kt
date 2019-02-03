package com.example.omar.taskmanager.ui.task_details

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.omar.taskmanager.R
import com.example.omar.taskmanager.TaskManagerApp
import com.example.omar.taskmanager.dagger.component.DaggerActivityComponent
import com.example.omar.taskmanager.dagger.module.ActivityModule
import com.example.omar.taskmanager.data.database.tables.Comment
import com.example.omar.taskmanager.data.database.tables.Task
import com.example.omar.taskmanager.ui.tasks.TasksListActivity
import com.example.omar.taskmanager.utils.Utils.Companion.createTask
import com.xwray.groupie.Group
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_task_details.*
import kotlinx.android.synthetic.main.activity_tasks_list.*
import kotlinx.android.synthetic.main.task_details_header.*
import java.util.*
import javax.inject.Inject

class TaskDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var taskDetailsVM: TaskDetailsViewModel

    @Inject
    lateinit var adapter: GroupAdapter<ViewHolder>

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    var task: Task? = null
    var comments: MutableList<Comment> = mutableListOf()
    var items:MutableList<Group> = mutableListOf()

    val callback = object : TaskDetailsHeader.CallbackTaskHeader {
        override fun taskUpdated(task: Task) {
            taskDetailsVM.updateTask(task).subscribe({
                Log.d("","")
            }, {
                Log.d("","")
            })
        }

    }

    var shouldObserve = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_details)

        DaggerActivityComponent.builder().activityModule(ActivityModule(this))
            .appComponent((application as TaskManagerApp).component)
            .build().inject(this)

        setupToolpar()
        setupRecycler()
        taskDetailsVM.getCurrentTask()?.observe(this, object : Observer<Task> {
            override fun onChanged(t: Task?) {
                if (t != null) {

                    if (task == null) {
                        //insert
                        task = t
                        task_details_title_tool_bar.text = task!!.title
                        items.add(0,TaskDetailsHeader(task!!,callback))
                    } else {
                        //update
                        task = t
                        items.removeAt(0)
                        items.add(0, TaskDetailsHeader(t, callback))
                    }

                    adapter.update(items)

                    observComments()
                }else{
                   finish()
                }
            }

        })

        send_comment_btn.setOnClickListener {
            if(task != null &&!(comment_edtxt.text.toString().trim().isEmpty())){
                val comment = Comment(comment_edtxt.text.toString(),Date(),task!!.id)
                taskDetailsVM.addComment(comment).subscribe({
                    Log.d("","")
                },{
                    Log.d("","")
                })
            }
        }

    }



    private fun setupToolpar(){
        setSupportActionBar(task_details_toolbar)

        delete_action_tool_bar.setOnClickListener {
            if (task!= null) {
                taskDetailsVM.delete(task!!).observeOn(AndroidSchedulers.mainThread()).subscribe({
                    Log.d("","")
                    Toast.makeText(this, "task has been removed",Toast.LENGTH_LONG).show()
                }, {
                    Log.d("","")
                })
            }
        }

        if(supportActionBar != null) {
            supportActionBar!!.setTitle("")
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        }
    }

    private fun setupRecycler() {
        task_details_recycler_view.layoutManager = linearLayoutManager
        task_details_recycler_view.adapter = adapter
    }

    fun observComments() {
        if (shouldObserve) {
            shouldObserve = false

            taskDetailsVM.getComments(task!!.id).observe(this, object : Observer<List<Comment>> {
                override fun onChanged(t: List<Comment>?) {
                    if (t != null) {
                        var counter = 1
                        for (comment in t) {

                            if (items.size <= counter){
                                items.add(1,CommentItem(comment))
                            }
                            counter++
                        }

                        adapter.update(items)
                    }
                }

            })
        }
    }
}
