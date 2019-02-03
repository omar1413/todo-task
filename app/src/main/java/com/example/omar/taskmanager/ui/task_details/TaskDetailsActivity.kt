package com.example.omar.taskmanager.ui.task_details

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.omar.taskmanager.R
import com.example.omar.taskmanager.TaskManagerApp
import com.example.omar.taskmanager.dagger.component.DaggerActivityComponent
import com.example.omar.taskmanager.dagger.module.ActivityModule
import com.example.omar.taskmanager.data.database.tables.Comment
import com.example.omar.taskmanager.data.database.tables.Task
import com.example.omar.taskmanager.utils.Utils.Companion.createTask
import com.xwray.groupie.Group
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_task_details.*
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
            taskDetailsVM.updateTask(task).subscribe({}, {})
        }

    }

    var shouldObserve = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_details)

        DaggerActivityComponent.builder().activityModule(ActivityModule(this))
            .appComponent((application as TaskManagerApp).component)
            .build().inject(this)

        setupRecycler()
        taskDetailsVM.getCurrentTask()?.observe(this, object : Observer<Task> {
            override fun onChanged(t: Task?) {
                if (t != null) {
                    if (task == null) {
                        //insert
                        task = t
                        items.add(0,TaskDetailsHeader(task!!,callback))
                    } else {
                        //update
                        task = t
                        items.removeAt(0)
                        items.add(0, TaskDetailsHeader(t, callback))
                    }

                    adapter.update(items)

                    observComments()
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
