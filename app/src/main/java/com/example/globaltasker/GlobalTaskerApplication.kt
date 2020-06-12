package com.example.globaltasker

import android.app.Application
import androidx.room.Room
import com.example.globaltasker.persistence.AppDatabase
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes

class GlobalTaskerApplication : Application() {
    companion object {
        private lateinit var database: AppDatabase

        fun getDatabase(): AppDatabase{
            return database
        }
    }

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init(){
        // AppCenter crashlitics
        AppCenter.start(this, "be232023-6ffa-4ff8-8e6c-e6ad616f846b", Analytics::class.java, Crashes::class.java)

        // Database init
        database = Room.databaseBuilder(this, AppDatabase::class.java, "database")
            .addMigrations(AppDatabase.MIGRATION_1_2, AppDatabase.MIGRATION_2_3)
            .allowMainThreadQueries()
            .build()
    }
}