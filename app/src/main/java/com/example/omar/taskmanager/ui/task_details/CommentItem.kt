package com.example.omar.taskmanager.ui.task_details

import com.example.omar.taskmanager.R
import com.example.omar.taskmanager.data.database.tables.Comment
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.comment_of_task_item.view.*
import java.util.*

class CommentItem(var comment: Comment): Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.comment_of_task_item
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val v = viewHolder.itemView
        v.comment_txt_view.text = comment.content
        v.time_ago_txt_view.text = timeAgo().toString()
    }

    private fun timeAgo(): Date {
        val oldDate = comment.date
        val newData = Date()

        var timeAgo = ""
        val diffDate = Date(newData.time - oldDate.time)
        return diffDate
    }

}