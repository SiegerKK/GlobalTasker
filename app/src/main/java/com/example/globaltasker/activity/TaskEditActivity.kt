package com.example.globaltasker.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.globaltasker.GlobalTaskerApplication
import com.example.globaltasker.R
import com.example.globaltasker.persistence.model.DEFAULT_TASK_ID
import com.example.globaltasker.persistence.model.Task
import kotlinx.android.synthetic.main.activity_task_edit.*

class TaskEditActivity : AppCompatActivity() {
    lateinit var task: Task

    companion object{
        const val TASK_ID = "TASK_ID"

        fun startActivity(context: Context, id: Long? = DEFAULT_TASK_ID){
            val intent = Intent(context, TaskEditActivity::class.java)
            intent.putExtra(TASK_ID, id)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_edit)
        supportActionBar?.title = "Edit task"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Test code
//        TODO: get from DB
        val taskID = intent.getLongExtra(TASK_ID, DEFAULT_TASK_ID)
        task = if(taskID != DEFAULT_TASK_ID) getTask(taskID)
                else Task(name = "", description = "")
        initTaskViews()
        //--------//
    }

    override fun onBackPressed() {
        saveTask()
        super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_task_edit, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_close -> {
                finish()
                true
            }
            android.R.id.home -> {
                saveTask()
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveTask(){
        task.name = teTaskName.text.toString()
        task.description = teTaskDescription.text.toString()
        GlobalTaskerApplication.getDatabase().taskDao().upsert(task)
//        Snackbar.make(findViewById(R.id.baseLayoutTaskEdit), "Saved", Snackbar.LENGTH_SHORT).show()
    }
    private fun getTask(id: Long): Task{
        return GlobalTaskerApplication.getDatabase().taskDao().getById(id)
    }

    private fun initTaskViews(){
        teTaskName.setText(task.name)
        teTaskDescription.setText(task.description)
    }
}