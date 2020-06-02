package com.example.globaltasker.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.globaltasker.GlobalTaskerApplication
import com.example.globaltasker.R
import com.example.globaltasker.model.Task
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class TaskEditActivity : AppCompatActivity() {
    lateinit var task: Task

    companion object{
        const val TASK_ID = "TASK_ID"

        fun startActivity(context: Context, id: Long? = -1L){
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
        val taskID = intent.getLongExtra(TASK_ID, -1L).toInt()
        task = if(taskID != -1) GlobalTaskerApplication.tasks[taskID]
                else Task(-1, "", "")
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
//        TODO: save task to DB
        Snackbar.make(findViewById(R.id.baseLayoutTaskEdit), "Saved", Snackbar.LENGTH_SHORT).show()
    }

    private fun initTaskViews(){
        findViewById<TextInputEditText>(R.id.tfTaskName).setText(task.name)
        findViewById<TextInputEditText>(R.id.tfTaskDescription).setText(task.description)
    }
}