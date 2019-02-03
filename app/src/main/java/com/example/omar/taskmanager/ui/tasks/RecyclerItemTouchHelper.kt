package com.example.omar.taskmanager.ui.tasks

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.task_item.view.*

class RecyclerItemTouchHelper(
    dragDire: Int,
    swapDir: Int,
    var recyclerItemTouchHelperListner: RecyclerItemTouchHelperListner
) : ItemTouchHelper.SimpleCallback(dragDire, swapDir) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        recyclerItemTouchHelperListner.onSwiped(viewHolder.adapterPosition)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (viewHolder != null) {
            val foreground = (viewHolder as ViewHolder).itemView.task_item_foreground
            ItemTouchHelper.Callback.getDefaultUIUtil().onSelected(foreground)
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        val foreground = (viewHolder as ViewHolder).itemView.task_item_foreground
        ItemTouchHelper.Callback.getDefaultUIUtil().clearView(foreground)

    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val foreground = (viewHolder as ViewHolder).itemView.task_item_foreground
        ItemTouchHelper.Callback.getDefaultUIUtil()
            .onDraw(c, recyclerView, foreground, dX, dY, actionState, isCurrentlyActive)
    }


    override fun onChildDrawOver(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder?,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (viewHolder != null) {
            val foreground = (viewHolder as ViewHolder).itemView.task_item_foreground
            ItemTouchHelper.Callback.getDefaultUIUtil()
                .onDrawOver(c, recyclerView, foreground, dX, dY, actionState, isCurrentlyActive)
        }
    }

    interface RecyclerItemTouchHelperListner {
        fun onSwiped(pos: Int)
    }

}