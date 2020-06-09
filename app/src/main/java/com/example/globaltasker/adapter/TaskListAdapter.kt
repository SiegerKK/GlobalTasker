package com.example.globaltasker.adapter

import android.graphics.Color
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.example.globaltasker.GlobalTaskerApplication
import com.example.globaltasker.R
import com.example.globaltasker.activity.MainActivity
import com.example.globaltasker.activity.TaskEditActivity
import com.example.globaltasker.persistence.model.Task
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.item_task.view.*

class TaskListAdapter(var activity: MainActivity, var tasks: List<Task>, private val listener: (Task) -> Unit) : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>(){

    override fun getItemCount(): Int = tasks.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder{
        val tmpViewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(activity, tmpViewHolder)
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
    class TaskViewHolder(private var activity: MainActivity, itemView: View) : RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener{
        lateinit var task: Task

        fun bind(task: Task, listener: (Task) -> Unit) {
            this.task = task

            // Init name, description view
            itemView.tvTaskName.text = task.name
            itemView.tvTaskDescription.text = task.description

            // Init deadline view
            if(!task.deadline.isActive)
                itemView.tvTaskDeadline.visibility = View.INVISIBLE
            else
                itemView.tvTaskDeadline.visibility = View.VISIBLE

            itemView.tvTaskDeadline.text = task.deadline.toSimpleString()
            when{
                task.isCompleted -> itemView.tvTaskDeadline.setBackgroundColor(Color.GREEN)
                task.deadline.isOut() -> itemView.tvTaskDeadline.setBackgroundColor(Color.RED)
                task.deadline.isLastDay() -> itemView.tvTaskDeadline.setBackgroundColor(Color.YELLOW)
                else -> itemView.tvTaskDeadline.setBackgroundColor(Color.TRANSPARENT)
            }
            // Init complete button
            itemView.ibComplete.setOnClickListener {
                task.isCompleted = !task.isCompleted
                GlobalTaskerApplication.getDatabase().taskDao().update(task)
                // TODO: is it necessary?
                activity.updateTaskList()
            }

            itemView.setOnClickListener { listener(task) }
            itemView.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            val contextMenuEdit: MenuItem = menu!!.add(R.string.edit)
            val contextMenuDelete: MenuItem = menu.add(R.string.delete)

            contextMenuDelete.setOnMenuItemClickListener {
                // Delete task
                GlobalTaskerApplication.getDatabase().taskDao().delete(task)
                activity.updateTaskList()

                // Undo snackbar
                val snackbar = Snackbar.make(activity.rvTaskList, R.string.revert_deleting, Snackbar.LENGTH_SHORT)
                snackbar.duration = 4000
                snackbar.setAction(R.string.undo,
                    MainActivity.RestoreTaskOnClickListener(activity, task)
                )
                snackbar.show()
                true
            }
            contextMenuEdit.setOnMenuItemClickListener {
                TaskEditActivity.startActivityForResult(activity, task.id, requestCode = MainActivity.START_EDIT_ACTIVITY)
                true
            }
        }
    }
}