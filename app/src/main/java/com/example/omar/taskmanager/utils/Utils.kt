package com.example.omar.taskmanager.utils

import com.example.omar.taskmanager.data.database.tables.Task

class Utils{
    companion object {

        fun createTask(task: Task): Task {
            val newTask = Task(task.title, task.status, task.date, task.priority, task.userId)
            newTask.id = task.id
            return newTask
        }
    }
}