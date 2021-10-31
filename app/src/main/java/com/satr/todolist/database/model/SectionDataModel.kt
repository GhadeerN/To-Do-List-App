package com.satr.todolist.database.model

// This section data model is to section our tasks based on their status (today, past, no due date, and upcoming tasks)
// There is no need to add it to our database, cause it's just to organize our sections data. (the sections data will get changed every 24h)
data class SectionDataModel(
    val section: String,
    val list: List<TodoDataModel>
)
