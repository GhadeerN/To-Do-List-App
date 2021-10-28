package com.satr.todolist.repositories

import android.content.Context
import androidx.room.Room
import com.satr.todolist.database.TodoDatabase
import com.satr.todolist.database.model.TodoDataModel
import java.lang.Exception

private const val DATABASE_NAME = "todo-list-database"
class TodoRepository(val context: Context) {
    val database: TodoDatabase = Room.databaseBuilder(
        context,
        TodoDatabase::class.java,
        DATABASE_NAME
    ).fallbackToDestructiveMigration().build()

    val totoDao = database.todoDao()

    // Dao functions
    fun getTasks() = totoDao.getTodoTasks()
    fun getCompletedTasks() = totoDao.getCompletedTasks()

    suspend fun deleteTask(task: TodoDataModel) = totoDao.deleteTask(task)
    suspend fun updateTask(task: TodoDataModel) = totoDao.updateTask(task)
    suspend fun addTask(task: TodoDataModel) = totoDao.addTodo(task)

    // Companion obj
    companion object {
        private var instance: TodoRepository? = null

        fun init(context: Context) {
            if (instance == null) {
                instance = TodoRepository(context)
            }
        }

        fun get(): TodoRepository {
            return instance ?: throw Exception("To do task Repository must be initialized")
        }
    }
}