package com.example.globaltasker.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.globaltasker.GlobalTaskerApplication.Companion.tasks
import com.example.globaltasker.R
import com.example.globaltasker.adapter.TaskListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var rvTaskList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        rvTaskList = findViewById<RecyclerView>(R.id.rvTaskList)

        supportActionBar?.title = "Tasks"

        // Plus button
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            TaskEditActivity.startActivity(this)
        }
        rvTaskList.layoutManager = LinearLayoutManager(this)
        rvTaskList.adapter = TaskListAdapter(tasks){
            TaskViewActivity.startActivity(this, it.id)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}