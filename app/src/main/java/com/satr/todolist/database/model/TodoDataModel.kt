package com.satr.todolist.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoDataModel(
    var title: String,
    val creationDate: String,
    var checked: Boolean,
    var details: String = "",
    var dueDate: String = "",
    var status: String = "No due date",
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
