package ir.yusefpasha.taskmanagerapp.presentation.util

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ir.yusefpasha.taskmanagerapp.R
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
            val position = viewHolder.adapterPosition
            val task = (recyclerView.adapter as TaskAdapter).currentList[position]
            when (direction) {
                ItemTouchHelper.LEFT -> onSwipeLeft(task)
                ItemTouchHelper.RIGHT -> onSwipeRight(task)
            }
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
            val itemView = viewHolder.itemView
            val paint = Paint()
            val iconSize = 32
            val iconMargin = 16
            val density = recyclerView.context.resources.displayMetrics.density
            val iconSizePx = (iconSize * density).toInt()
            val iconMarginPx = (iconMargin * density).toInt()
            val cornerRadius = itemView.height * 0.15f

            if (dX < 0) {
                paint.color = ContextCompat.getColor(recyclerView.context, R.color.delete)
                val rect = RectF(
                    itemView.right + dX,
                    itemView.top.toFloat(),
                    itemView.right.toFloat(),
                    itemView.bottom.toFloat()
                )
                c.drawRoundRect(rect, cornerRadius, cornerRadius, paint)

                val icon = ContextCompat.getDrawable(recyclerView.context, R.drawable.delete)
                icon?.let {
                    val iconTop = itemView.top + (itemView.height - iconSizePx) / 2
                    val iconLeft = itemView.right - iconMarginPx - iconSizePx
                    it.setBounds(
                        iconLeft,
                        iconTop,
                        iconLeft + iconSizePx,
                        iconTop + iconSizePx
                    )
                    it.draw(c)
                }
            } else if (dX > 0) {

                paint.color = ContextCompat.getColor(recyclerView.context, R.color.edit)
                val rect = RectF(
                    itemView.left.toFloat(),
                    itemView.top.toFloat(),
                    itemView.left + dX,
                    itemView.bottom.toFloat()
                )
                c.drawRoundRect(rect, cornerRadius, cornerRadius, paint)

                val icon = ContextCompat.getDrawable(recyclerView.context, R.drawable.edit)
                icon?.let {
                    val iconTop = itemView.top + (itemView.height - iconSizePx) / 2
                    val iconLeft = itemView.left + iconMarginPx
                    it.setBounds(
                        iconLeft,
                        iconTop,
                        iconLeft + iconSizePx,
                        iconTop + iconSizePx
                    )
                    it.draw(c)
                }
            }

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    })
    itemTouchHelper.attachToRecyclerView(recyclerView)
}
