package com.example.globaltasker.persistence.dao


import androidx.room.*
import com.example.globaltasker.persistence.model.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun getAll(): List<Task>

    @Query("SELECT * FROM task WHERE id = :id")
    fun getById(id: Long): Task

    // IGNORE: return -1 if object already exist
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(task: Task): Long

    @Update
    fun update(task: Task)

    @Delete
    fun delete(task: Task)


    fun upsert(task: Task) {
        val id = insert(task)
        // -1L -> ERROR CODE, NOT DEFAULT_TASK_ID
        if(id == -1L)
            update(task)
    }
}