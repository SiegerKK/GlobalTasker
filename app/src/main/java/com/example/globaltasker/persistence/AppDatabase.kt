package com.example.globaltasker.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.globaltasker.persistence.dao.TaskDao
import com.example.globaltasker.persistence.model.Task

@Database(entities = [Task::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}