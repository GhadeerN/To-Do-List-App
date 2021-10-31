package com.satr.todolist.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoDataModel(
    var title: String,
    var checked: Boolean,
    var details: String = "Add details",
    var dueDate: String = "Add due date",
    var status: String = "No due date",
    val creationDate: String = "",
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
