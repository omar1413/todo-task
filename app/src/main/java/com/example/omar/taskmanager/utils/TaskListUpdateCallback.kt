package com.example.omar.taskmanager.utils

import android.util.Log
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.omar.taskmanager.ui.tasks.TaskItem

class TaskListUpdateCallback(val oldL: MutableList<TaskItem>, val newL: MutableList<TaskItem>): ListUpdateCallback{
    override fun onChanged(position: Int, count: Int, payload: Any?) {
        Log.d("","")
        for (i in position..position+count-1) {
            oldL.removeAt(i)
            oldL.add(i, newL.get(i))
        }
    }

    override fun onMoved(fromPosition: Int, toPosition: Int) {
        Log.d("","")
    }

    override fun onInserted(position: Int, count: Int) {
        Log.d("","")
        for (i in position..position+count-1) {
            if (position > 0) {
                val index = newL.indexOf(oldL.get(i - 1))
                oldL.add(i, newL.get(index + 1))
            }else {
                oldL.add(i, newL.get(i))
            }
        }
    }

    override fun onRemoved(position: Int, count: Int) {
        Log.d("","")
        for (i in position..position+count-1) {
            oldL.removeAt(position)
        }
    }

}