package com.example.globaltasker.adapter

import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.example.globaltasker.GlobalTaskerApplication
import com.example.globaltasker.R
import com.example.globaltasker.persistence.model.Task
import kotlinx.android.synthetic.main.item_task.view.*

class TaskListAdapter(var tasks: List<Task>, private val listener: (Task) -> Unit) : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>(){

    override fun getItemCount(): Int = tasks.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder{
        val tmpViewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(tmpViewHolder)
    }
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position], listener)
    }
    fun replaceList(list: List<Task>){
        tasks = list
        // DONT FORGET !!!
        notifyDataSetChanged()
    }

    //-------------------- Local class --------------------//
    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener{
        lateinit var task: Task

        init {
            itemView.setOnCreateContextMenuListener(this)
        }

        fun bind(task: Task, listener: (Task) -> Unit) {
            this.task = task

            itemView.tvTaskName.text = task.name
            itemView.tvTaskDescription.text = task.description

            itemView.setOnClickListener { listener(task) }
        }

        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            val contextMenuEdit: MenuItem = menu!!.add(R.string.edit)
            val contextMenuDelete: MenuItem = menu.add(R.string.delete)

            contextMenuDelete.setOnMenuItemClickListener {
                // Delete task
                GlobalTaskerApplication.getDatabase().taskDao().delete(task)

                // TODO: undo button
                // Restore task
                /*val snackbar = Snackbar.make(rvTaskList, "Revert deleting?", Snackbar.LENGTH_SHORT)
                snackbar.duration = 4000
                snackbar.setAction(R.string.undo,
                    MainActivity.RestoreTaskOnClickListener(this, task)
                )
                snackbar.show()*/
                true
            }
            // TODO: edit menuItem
//            contextMenuEdit.setOnMenuItemClickListener {}
        }
    }
}