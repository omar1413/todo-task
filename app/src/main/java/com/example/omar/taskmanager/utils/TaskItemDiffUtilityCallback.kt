package com.example.omar.taskmanager.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.omar.taskmanager.ui.tasks.TaskItem

class TaskItemDiffUtilityCallback(val oldList: MutableList<TaskItem>, val newList: MutableList<TaskItem>) :
    DiffUtil.Callback() {


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList.get(oldItemPosition).task.id == newList.get(newItemPosition).task.id

    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldTask = oldList.get(oldItemPosition).task
        val newTask = newList.get(newItemPosition).task
        return oldTask.equals(newTask)
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {

        return super.getChangePayload(oldItemPosition, newItemPosition)
    }

}