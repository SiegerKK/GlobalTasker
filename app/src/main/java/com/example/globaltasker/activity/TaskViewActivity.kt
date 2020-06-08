package com.example.globaltasker.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
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

        const val START_EDIT_ACTIVITY = 11

        const val RESULT_TASK_DELETED = 11

        fun startActivityForResult(activity: Activity, id: Long? = -1, requestCode: Int){
            val intent = Intent(activity, TaskViewActivity::class.java)
            intent.putExtra(TASK_ID, id)
            activity.startActivityForResult(intent, requestCode)
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
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(START_EDIT_ACTIVITY == requestCode){
            if(TaskEditActivity.RESULT_TASK_DELETED == resultCode){
                setResult(RESULT_TASK_DELETED, data)
                finish()
            }
        }
    }

    private fun getTask(id: Long): Task{
        return GlobalTaskerApplication.getDatabase().taskDao().getById(id)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_task_view, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_delete -> {
                deleteTask()
                intent.putExtra(TASK_ID, task)
                setResult(RESULT_TASK_DELETED, intent)
                finish()
                true
            }
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

        tvTaskName.setOnClickListener { TaskEditActivity.startActivityForResult(this, task.id, START_EDIT_ACTIVITY) }
        tvTaskDescription.setOnClickListener { TaskEditActivity.startActivityForResult(this, task.id, START_EDIT_ACTIVITY) }
    }

    private fun deleteTask(){
        GlobalTaskerApplication.getDatabase().taskDao().delete(task)
    }
}