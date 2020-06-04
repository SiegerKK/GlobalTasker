package com.example.globaltasker.persistence.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var name: String,
    var description: String
)