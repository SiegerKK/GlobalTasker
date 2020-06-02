package com.example.globaltasker.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.globaltasker.GlobalTaskerApplication
import com.example.globaltasker.R
import com.example.globaltasker.model.Task

class TaskViewActivity : AppCompatActivity() {
    lateinit var task: Task

    lateinit var tvTaskName: TextView
    lateinit var tvTaskDescription: TextView

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

        // Init fields
        task = GlobalTaskerApplication.tasks[intent.getLongExtra(TASK_ID, -1L).toInt()]
        tvTaskName = findViewById<TextView>(R.id.tvTaskName)
        tvTaskDescription = findViewById<TextView>(R.id.tvTaskDescription)

        tvTaskName.setOnClickListener { TaskEditActivity.startActivity(this, task.id) }
        tvTaskDescription.setOnClickListener { TaskEditActivity.startActivity(this, task.id) }

        initTaskViews()
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
    }
}