package com.example.globaltasker.persistence.dao


import androidx.room.*
import com.example.globaltasker.persistence.model.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun getAll(): List<Task>

    @Query("SELECT * FROM task WHERE id = :id")
    fun getByID(id: Long): Task

    // IGNORE: return -1 if object already exist
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(task: Task): Long

    @Update
    fun update(task: Task)

    @Delete
    fun delete(task: Task)


    fun upsert(task: Task) {
        val id = insert(task)
        if(id == -1L)
            update(task)
    }
}