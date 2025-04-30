package ir.yusefpasha.taskmanagerapp.presentation.util

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ir.yusefpasha.taskmanagerapp.presentation.dapter.TaskAdapter
import ir.yusefpasha.taskmanagerapp.presentation.model.TaskItem

fun setupSwipeToDismiss(
    recyclerView: RecyclerView,
    onSwipeLeft: (TaskItem) -> Unit,
    onSwipeRight: (TaskItem) -> Unit
) {
    val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
        0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            when (direction) {
                ItemTouchHelper.LEFT -> {
                    val position = viewHolder.adapterPosition
                    val task = (recyclerView.adapter as TaskAdapter).currentList[position]
                    onSwipeLeft(task)
                }

                ItemTouchHelper.RIGHT -> {
                    val position = viewHolder.adapterPosition
                    val task = (recyclerView.adapter as TaskAdapter).currentList[position]
                    onSwipeRight(task)
                }
            }
        }
    })
    itemTouchHelper.attachToRecyclerView(recyclerView)
}