package com.example.omar.taskmanager.ui.task_details

import android.view.View
import com.example.omar.taskmanager.R
import com.example.omar.taskmanager.data.database.tables.Task
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.task_details_header.*
import kotlinx.android.synthetic.main.task_details_header.view.*
import kotlinx.android.synthetic.main.task_item.view.*
import java.text.DateFormat

class TaskDetailsHeader(val task: Task, val callbackTaskItem:CallbackTaskHeader): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.task_details_header
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val v = viewHolder.itemView
        setDateTxtView(v)
        setStatusButton(v)
        setPriorityBtns(v)

        setupBtnsClickListner(v)
    }


    private fun setDateTxtView(v: View) {
        val df = DateFormat.getDateInstance()
        v.date_text_view.text = df.format(task.date)
    }

    private fun setStatusButton(v: View) {
        if (task.status == Task.Status.DONE) {
            v.check_status_btn.setBackgroundResource(R.drawable.check_circle)
        } else {
            v.check_status_btn.setBackgroundResource(R.drawable.empty_circle)
        }


    }

    private fun setPriorityBtns(v: View) {
        if (task.priority == Task.Priority.LOW) {
            setLowPriority(v)
        } else if (task.priority == Task.Priority.MEDIUM) {
            setMedPriority(v)
        } else {
            setHighPriority(v)
        }
    }

    private fun setupBtnsClickListner(v: View) {
        v.check_status_btn.setOnClickListener {
            val newTask = createTask(task)

            if (task.status == Task.Status.DONE) {
                newTask.status = Task.Status.IN_PROGRESS
            } else {
                newTask.status = Task.Status.DONE
            }

            callbackTaskItem.taskUpdated(newTask)
        }

        v.task_details_low_priority_btn.setOnClickListener {
            val newTask = createTask(task)
            newTask.priority = Task.Priority.LOW
            callbackTaskItem.taskUpdated(newTask)
        }

        v.task_details_med_priority_btn.setOnClickListener {
            val newTask = createTask(task)
            newTask.priority = Task.Priority.MEDIUM
            callbackTaskItem.taskUpdated(newTask)
        }

        v.task_details_high_priority_btn.setOnClickListener {
            val newTask = createTask(task)
            newTask.priority = Task.Priority.HIGH
            callbackTaskItem.taskUpdated(newTask)
        }
    }


    fun setLowPriority(v: View) {
        setPriorityDefault(v)

        v.task_details_low_priority_btn.setBackgroundResource(R.drawable.p1)
    }

    fun setMedPriority(v: View) {
        setPriorityDefault(v)
        v.task_details_med_priority_btn.setBackgroundResource(R.drawable.p2)
    }

    fun setHighPriority(v: View) {
        setPriorityDefault(v)
        v.task_details_high_priority_btn.setBackgroundResource(R.drawable.p3)
    }

    fun setPriorityDefault(v: View) {
        v.task_details_high_priority_btn.setBackgroundResource(R.drawable.r3)
        v.task_details_med_priority_btn.setBackgroundResource(R.drawable.r2)
        v.task_details_low_priority_btn.setBackgroundResource(R.drawable.r1)
    }

    private fun createTask(task: Task): Task {
        val newTask = Task(task.title, task.status, task.date, task.priority, task.userId)
        newTask.id = task.id
        return newTask
    }

    interface CallbackTaskHeader {
        fun taskUpdated(task: Task)

    }

}