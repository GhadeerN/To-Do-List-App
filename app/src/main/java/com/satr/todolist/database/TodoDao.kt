package com.satr.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.satr.todolist.database.model.TodoDataModel

@Dao
interface TodoDao {
    @Insert
    suspend fun addTodo(task: TodoDataModel)

    @Query("SELECT * FROM tododatamodel WHERE checked = 0")
    fun getTodoTasks(): LiveData<List<TodoDataModel>>

    @Query("SELECT * FROM tododatamodel WHERE checked = 1")
    fun getCompletedTasks(): LiveData<List<TodoDataModel>>

    @Delete
    suspend fun deleteTask(task: TodoDataModel)

    // This function is for dev seek
    @Query("DELETE FROM tododatamodel")
    suspend fun deleteAll()

    @Update
    suspend fun updateTask(task: TodoDataModel)
}