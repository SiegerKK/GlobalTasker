package com.example.globaltasker

import android.app.Application
import androidx.room.Room
import com.example.globaltasker.persistence.AppDatabase
import com.example.globaltasker.persistence.model.Task
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes

class GlobalTaskerApplication : Application() {
    companion object{
        val tasks: ArrayList<Task> = ArrayList()
    }
    private lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init(){
        // AppCenter crashlitics
        AppCenter.start(this, "be232023-6ffa-4ff8-8e6c-e6ad616f846b", Analytics::class.java, Crashes::class.java)

        // Database init
        database = Room.databaseBuilder(this, AppDatabase::class.java, "database")
            .allowMainThreadQueries()
            .build()

    }

    fun getDatabase(): AppDatabase{
        return database
    }
}