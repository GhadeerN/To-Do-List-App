package com.satr.todolist.database.model

data class SectionDataModel(
    val section: String,
    val list: List<TodoDataModel>
)
