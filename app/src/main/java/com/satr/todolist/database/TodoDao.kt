package com.satr.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.satr.todolist.database.model.TodoDataModel

@Dao
interface TodoDao {
    @Insert
    suspend fun addTodo(task: TodoDataModel)

    @Query("SELECT * FROM tododatamodel WHERE checked = 'False'")
    fun getTodoTasks(): LiveData<List<TodoDataModel>>

    @Query("SELECT * FROM tododatamodel WHERE checked = 'True'")
    fun getCompletedTasks(): LiveData<List<TodoDataModel>>

    @Delete
    suspend fun deleteTask(task: TodoDataModel)

    @Update
    suspend fun updateTask(task: TodoDataModel)
}