package com.satr.todolist.views

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// This function is needed in completedTaskAdapter & ChildAdapter so DRY :)
object DateFormat {
    @RequiresApi(Build.VERSION_CODES.O)
    fun dueDateCardFormatter(dueDate: String): String {
        return if (dueDate.isNotEmpty()) {
            val localDate = LocalDate.parse(dueDate, DateTimeFormatter.ofPattern("E, MMM d, y"))
            localDate.format(DateTimeFormatter.ofPattern("MMM d"))
        } else ""
    }
}