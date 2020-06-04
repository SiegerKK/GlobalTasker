package com.example.globaltasker.persistence.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long = -1L,
    var name: String,
    var description: String
)