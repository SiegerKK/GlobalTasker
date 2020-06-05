package com.example.globaltasker.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.globaltasker.GlobalTaskerApplication
import com.example.globaltasker.R
import com.example.globaltasker.adapter.TaskListAdapter
import com.example.globaltasker.persistence.model.Task
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var rvTaskList: RecyclerView

    companion object {
        const val START_EDIT_ACTIVITY = 11
        const val START_VIEW_ACTIVITY = 12
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Tasks"

        initRvTaskList()

        // Plus button
        fab.setOnClickListener {
            TaskEditActivity.startActivityForResult(this, requestCode = START_EDIT_ACTIVITY)
        }
    }

    override fun onResume() {
        super.onResume()
        (rvTaskList.adapter as TaskListAdapter).replaceList(getTaskListFromDb())
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

    private fun initRvTaskList(){
        rvTaskList = findViewById<RecyclerView>(R.id.rvTaskList)
        rvTaskList.layoutManager = LinearLayoutManager(this)
        rvTaskList.adapter = TaskListAdapter(getTaskListFromDb()){
            TaskViewActivity.startActivityForResult(this, it.id, START_VIEW_ACTIVITY)
        }
    }

    private fun getTaskListFromDb(): List<Task>{
        return GlobalTaskerApplication.getDatabase().taskDao().getAll()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(START_VIEW_ACTIVITY == requestCode || START_EDIT_ACTIVITY == requestCode){
            if(TaskEditActivity.RESULT_TASK_DELETED == resultCode){
                val snackbar = Snackbar.make(rvTaskList, "Revert deleting?", Snackbar.LENGTH_SHORT)
                snackbar.duration = 4000
                snackbar.setAction(R.string.undo, View.OnClickListener { fun onClick(){
//                    TODO: revert deleting
                } })
                snackbar.show()
            }
        }
    }
}