package com.satr.todolist.views.objects

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.satr.todolist.database.model.TodoDataModel
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateFormat {
    // This function is needed in completedTaskAdapter & ChildAdapter so DRY :)
    @RequiresApi(Build.VERSION_CODES.O)
    fun dueDateCardFormatter(dueDate: String): String {
        return if (dueDate.isNotEmpty()) {
            val localDate = LocalDate.parse(dueDate, DateTimeFormatter.ofPattern("E, MMM d, y"))
            localDate.format(DateTimeFormatter.ofPattern("MMM d"))
        } else ""
    }

    // The following function is to check if a task day is approaching (day before the task)
    @RequiresApi(Build.VERSION_CODES.O)
    fun isDueDateApproaching(list: MutableList<TodoDataModel>): MutableList<TodoDataModel> {
        val currentDay = LocalDate.now()
        val tasks = mutableListOf<TodoDataModel>()
        list.forEach {
            val dueDate = it.dueDate
            if (dueDate.isNotEmpty()) {
                val parsedDueDate =
                    LocalDate.parse(dueDate, DateTimeFormatter.ofPattern("E, MMM d, y"))
                val numOfDayBetween =
                    Duration.between(currentDay.atStartOfDay(), parsedDueDate.atStartOfDay())
                        .toDays()

                if (numOfDayBetween == 1L) {
                    tasks.add(it)
                }
            }
        }
        Log.d("DayBefore", tasks.toString())
        return tasks
    }
}