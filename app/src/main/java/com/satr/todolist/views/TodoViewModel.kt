package com.satr.todolist.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satr.todolist.database.model.TodoDataModel
import com.satr.todolist.repositories.TodoRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class TodoViewModel : ViewModel() {
    private val todoRepository = TodoRepository.get()

    var taskLiveData = MutableLiveData<TodoDataModel>()

    val getAllTasks = todoRepository.getTasks()
    val getAllCompletedTasks = todoRepository.getCompletedTasks()

    @RequiresApi(Build.VERSION_CODES.O)
    fun addTask(title: String, checked: Boolean, details: String?, dueDate: String?) {
        // Take the task creation date
        val currentDate = getDate()
        // Check the optional fields values
        // TODO maybe you don't need all this, check on run please!
        val checkedDetails = if (details.isNullOrEmpty())
            ""
        else details
        val checkedDueDate = if (dueDate.isNullOrEmpty())
            ""
        else dueDate
        val status = if (dueDate != null && dueDate.isNotEmpty())
            setTaskStatus(dueDate)
        else "No due date"

        viewModelScope.launch {
            todoRepository.addTask(
                TodoDataModel(title, currentDate, checked, checkedDetails, checkedDueDate, status)
            )
        }
    }

    fun deleteTask(task: TodoDataModel) {
        viewModelScope.launch {
            todoRepository.deleteTask(task)
        }
    }

    fun updateTask(task: TodoDataModel) {
        viewModelScope.launch {
            todoRepository.updateTask(task)
        }
    }
}

// Get current date
fun getDate(): String {
    val dateFormat = SimpleDateFormat("dd/M/yyyy")
    return dateFormat.format(Date())
}

// Get the tasks status based on selected due date
@RequiresApi(Build.VERSION_CODES.O)
fun setTaskStatus(dueDate: String): String {
    val currentDate = getDate()
    // Here I converted the date from string to local date to use date comparisons functions
    // Resource: https://www.ictdemy.com/kotlin/oop/date-and-time-in-kotlin-parsing-and-comparing
    val localDate = LocalDate.parse(currentDate, DateTimeFormatter.ofPattern("dd/M/yyyy"))
    val dueDateLocal = LocalDate.parse(dueDate, DateTimeFormatter.ofPattern("dd/M/yyyy"))
    return when {
        dueDateLocal.isEqual(localDate) -> {
            "Today"
        }
        dueDateLocal.isBefore(localDate) -> {
            "Past"
        }
        dueDateLocal.isAfter(localDate) -> "Upcoming Tasks"
        else -> "No due date"
    }
}
