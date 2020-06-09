package com.example.globaltasker.persistence.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

const val DEFAULT_TASK_ID = 0L

@Entity
@Parcelize
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Long = DEFAULT_TASK_ID,
    var name: String,
    var description: String,
    var isCompleted: Boolean = false,

    @Embedded
    var deadline: Deadline = Deadline()
) : Parcelable