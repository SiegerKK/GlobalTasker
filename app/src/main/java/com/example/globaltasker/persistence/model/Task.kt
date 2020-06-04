package com.example.globaltasker.persistence.model

import androidx.room.Entity
import androidx.room.PrimaryKey

const val DEFAULT_TASK_ID = 0L

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Long = DEFAULT_TASK_ID,
    var name: String,
    var description: String
)