package com.example.omar.taskmanager.ui.tasks

import android.view.View
import com.example.omar.taskmanager.R
import com.example.omar.taskmanager.data.database.tables.Task
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.task_details_header.view.*
import kotlinx.android.synthetic.main.task_item.view.*

class TaskItem(var task: Task, val callbackTaskItem: CallbackTaskItem) : Item<ViewHolder>() {


    override fun getLayout(): Int {
        return R.layout.task_item
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val v = viewHolder.itemView
        v.task_title_txt_view.text = task.title
        v.task_date_txt_view.text = task.date.toString()

        if (task.status == Task.Status.DONE) {
            v.task_done_status_btn.setBackgroundResource(R.drawable.check_circle)
        } else {
            v.task_done_status_btn.setBackgroundResource(R.drawable.empty_circle)
        }

        setupPriorityBtns(v)

        setupBtnsClickListner(v)
    }

    private fun setupBtnsClickListner(v: View) {
        v.task_done_status_btn.setOnClickListener {
            val newTask = createTask(task)

            if (task.status == Task.Status.DONE) {
                newTask.status = Task.Status.IN_PROGRESS
            } else {
                newTask.status = Task.Status.DONE
            }

            callbackTaskItem.taskUpdated(newTask)
        }

        v.task_low_priority_btn.setOnClickListener {
            val newTask = createTask(task)
            newTask.priority = Task.Priority.LOW
            callbackTaskItem.taskUpdated(newTask)
        }

        v.task_med_priority_btn.setOnClickListener {
            val newTask = createTask(task)
            newTask.priority = Task.Priority.MEDIUM
            callbackTaskItem.taskUpdated(newTask)
        }

        v.task_high_priority_btn.setOnClickListener {
            val newTask = createTask(task)
            newTask.priority = Task.Priority.HIGH
            callbackTaskItem.taskUpdated(newTask)
        }

        v.task_title_txt_view.setOnClickListener {
            callbackTaskItem.taskClicked(task)
        }

        v.task_date_txt_view.setOnClickListener {
            callbackTaskItem.taskClicked(task)
        }
    }

    private fun setupPriorityBtns(v: View) {
        if (task.priority == Task.Priority.LOW) {
            setLowPriority(v)
        } else if (task.priority == Task.Priority.MEDIUM) {
            setMedPriority(v)
        } else {
            setHighPriority(v)
        }
    }


    fun setLowPriority(v: View) {
        setPriorityDefault(v)

        v.task_low_priority_btn.setBackgroundResource(R.drawable.p1)
    }

    fun setMedPriority(v: View) {
        setPriorityDefault(v)
        v.task_med_priority_btn.setBackgroundResource(R.drawable.p2)
    }

    fun setHighPriority(v: View) {
        setPriorityDefault(v)
        v.task_high_priority_btn.setBackgroundResource(R.drawable.p3)
    }

    fun setPriorityDefault(v: View) {
        v.task_high_priority_btn.setBackgroundResource(R.drawable.r3)
        v.task_med_priority_btn.setBackgroundResource(R.drawable.r2)
        v.task_low_priority_btn.setBackgroundResource(R.drawable.r1)
    }

    interface CallbackTaskItem {
        fun taskUpdated(task: Task)
        fun taskClicked(task: Task)

    }

    private fun createTask(task: Task): Task {
        val newTask = Task(task.title, task.status, task.date, task.priority, task.userId)
        newTask.id = task.id
        return newTask
    }

    override fun equals(other: Any?): Boolean {
        val ob = other as TaskItem
        return task.id == ob.task.id
    }


}