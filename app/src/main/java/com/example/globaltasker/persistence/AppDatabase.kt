package com.example.globaltasker.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.globaltasker.persistence.dao.TaskDao
import com.example.globaltasker.persistence.model.Task

@Database(entities = [Task::class], version = 2)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object{
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE task ADD COLUMN isActive INTEGER NOT NULL DEFAULT '0'");
                database.execSQL("ALTER TABLE task ADD COLUMN date INTEGER NOT NULL DEFAULT '0'");
            }
        }
    }
}
