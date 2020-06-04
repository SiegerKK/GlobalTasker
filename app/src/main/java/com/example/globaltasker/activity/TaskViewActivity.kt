package com.example.globaltasker.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.globaltasker.GlobalTaskerApplication
import com.example.globaltasker.R
import com.example.globaltasker.persistence.model.DEFAULT_TASK_ID
import com.example.globaltasker.persistence.model.Task
import kotlinx.android.synthetic.main.activity_task_view.*

class TaskViewActivity : AppCompatActivity() {
    lateinit var task: Task

    companion object{
        const val TASK_ID = "TASK_ID"

        fun startActivity(context: Context, id: Long? = -1){
            val intent = Intent(context, TaskViewActivity::class.java)
            intent.putExtra(TASK_ID, id)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_view)

        // Action bar
        supportActionBar?.title = "Task"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()

        val taskId = intent.getLongExtra(TASK_ID, DEFAULT_TASK_ID)
        task = getTask(taskId)
        initTaskViews()
    }

    private fun getTask(id: Long): Task{
        return GlobalTaskerApplication.getDatabase().taskDao().getById(id)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initTaskViews(){
        tvTaskName.text = task.name
        tvTaskDescription.text = task.description

        tvTaskName.setOnClickListener { TaskEditActivity.startActivity(this, task.id) }
        tvTaskDescription.setOnClickListener { TaskEditActivity.startActivity(this, task.id) }
    }
}