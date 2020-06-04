package com.example.globaltasker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.globaltasker.R
import com.example.globaltasker.persistence.model.Task
import kotlinx.android.synthetic.main.item_task.view.*

class TaskListAdapter(private val tasks: List<Task>, private val listener: (Task) -> Unit) : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    override fun getItemCount(): Int = tasks.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder{
        val tmpViewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(tmpViewHolder)
    }
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position], listener)
    }

    //-------------------- Local class --------------------//
    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(task: Task, listener: (Task) -> Unit) {
            itemView.tvTaskName.text = task.name
            itemView.tvTaskDescription.text = task.description

            itemView.setOnClickListener { listener(task) }
        }
    }
}