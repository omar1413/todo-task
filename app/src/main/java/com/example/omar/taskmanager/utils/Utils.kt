package com.example.omar.taskmanager.utils

import com.example.omar.taskmanager.data.database.tables.Task
import java.util.*

class Utils {
    companion object {

        fun createTask(task: Task): Task {
            val newTask = Task(task.title, task.status, task.userId)
            newTask.priority = task.priority
            newTask.date = task.date
            newTask.id = task.id
            return newTask
        }

        fun dateDiff(date1: Date, dat2: Date): String {
            val c1 = Calendar.getInstance()
            val c2 = Calendar.getInstance()

            var diffC: Long = 0

            c1.time = date1
            c2.time = dat2

            diffC = Math.abs(c1.timeInMillis - c2.timeInMillis)

            val seconds: Long = diffC / 1000
            val minutes: Long = seconds / 60
            val hours: Long = minutes / 60
            val days: Long = hours / 24
            val months: Long = days / 30

            if (months != 0L) {
                return months.toString() + " month ago"
            }
            if (days != 0L) {
                return days.toString() + " day ago"
            }
            if (hours != 0L) {
                if (hours == 1L) {
                    return hours.toString() + " hour ago"
                } else {
                    return hours.toString() + " hours ago"
                }
            }
            if (minutes != 0L) {
                if (minutes == 1L) {
                    return minutes.toString() + " minute ago"
                } else {
                    return minutes.toString() + " minutes ago"
                }
            }
            if (seconds <= 1L) {
                return seconds.toString() + " second ago"
            } else {
                return seconds.toString() + " seconds ago"
            }

        }
    }
}