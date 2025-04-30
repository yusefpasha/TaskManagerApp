package ir.yusefpasha.taskmanagerapp.presentation.dapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import ir.yusefpasha.taskmanagerapp.R
import ir.yusefpasha.taskmanagerapp.domain.model.TimeRelation
import ir.yusefpasha.taskmanagerapp.domain.utils.checkTimeRelation
import ir.yusefpasha.taskmanagerapp.domain.utils.convertToDisplayText
import ir.yusefpasha.taskmanagerapp.presentation.model.TaskItem

class TaskAdapter(
    private val onItemSwipeListener: (TaskItem) -> Unit,
    private val onItemClickListener: (TaskItem) -> Unit
) : ListAdapter<TaskItem, TaskAdapter.TaskViewHolder>(object : DiffUtil.ItemCallback<TaskItem>() {

    override fun areItemsTheSame(oldItem: TaskItem, newItem: TaskItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TaskItem, newItem: TaskItem): Boolean {
        return oldItem == newItem
    }
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position) // استفاده از getItem به جای tasks[position]
        with(holder) {
            title.text = task.title
            description.text = task.description
            deadline.text = itemView.context.getString(
                R.string.task_deadline_value,
                task.deadline.convertToDisplayText()
            )
            deadlineStatus.setColorFilter(
                when (task.deadline.checkTimeRelation()) {
                    TimeRelation.BEFORE_NOW -> Color.Red
                    TimeRelation.AFTER_NOW -> Color.Blue
                    TimeRelation.NEAR_NOW -> Color.Yellow
                }.toArgb()
            )
            itemView.setOnClickListener { onItemClickListener(task) }
        }
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_title)
        val description: TextView = itemView.findViewById(R.id.tv_description)
        val deadline: TextView = itemView.findViewById(R.id.tv_deadline)
        val deadlineStatus: ShapeableImageView = itemView.findViewById(R.id.iv_deadline_status)
    }
}