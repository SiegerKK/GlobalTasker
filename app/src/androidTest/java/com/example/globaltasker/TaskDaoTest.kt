package com.example.globaltasker

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.globaltasker.persistence.AppDatabase
import com.example.globaltasker.persistence.dao.TaskDao
import com.example.globaltasker.persistence.model.Task
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class TaskDaoTest {
    private lateinit var db: AppDatabase
    private lateinit var taskDao: TaskDao

    @Before
    fun createDB(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        taskDao = db.taskDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDB(){
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeTaskAndReadInList(){
        val task = Task(name = "Job 1", description =  "Job 1 description")
        val id = taskDao.insert(task)
        val taskById = taskDao.getByID(id)
        assertTrue(task == taskById)
    }

}