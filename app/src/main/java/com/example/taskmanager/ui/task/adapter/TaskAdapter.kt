package com.example.taskmanager.ui.task.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.databinding.ItemTaskBinding
import com.example.taskmanager.model.Task

class TaskAdapter(
    val onLongClickItem: (task: Task) -> Unit,
    val onClickItem: (task: Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private val list = arrayListOf<Task>()

    fun addTasks(tasks: List<Task>) {
        list.clear()
        list.addAll(tasks)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) = with(binding) {
            tvTaskTitle.text = task.title
            tvTaskDesc.text = task.desc



            itemView.setOnLongClickListener {
                onLongClickItem(task)
                true
            }
            itemView.setOnClickListener {
                onClickItem(task)
            }
        }

        private fun ItemTaskBinding.changeItemColor() {
            if (adapterPosition % 2 == 0) {
                cvTaskItem.setCardBackgroundColor(Color.WHITE)
                tvTaskTitle.setTextColor(Color.BLACK)
                tvTaskDesc.setTextColor(Color.BLACK)
            } else {
                cvTaskItem.setCardBackgroundColor(Color.BLACK)
                tvTaskTitle.setTextColor(Color.WHITE)
                tvTaskDesc.setTextColor(Color.WHITE)
            }
        }
    }
}