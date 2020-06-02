package com.example.globaltasker

import android.app.Application
import com.example.globaltasker.model.Task
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes

class GlobalTaskerApplication : Application() {
    companion object{
        val tasks: ArrayList<Task> = ArrayList()
    }

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init(){
        // AppCenter crashlitics
        AppCenter.start(this, "be232023-6ffa-4ff8-8e6c-e6ad616f846b", Analytics::class.java, Crashes::class.java)
        for (i in 0..9L)
            tasks.add(Task(i, "Task $i", "Task $i description."))
    }
}