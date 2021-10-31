package com.satr.todolist.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.satr.todolist.database.model.TodoDataModel

@Database(entities = [TodoDataModel::class], version = 4)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun todoDao(): TodoDao
}