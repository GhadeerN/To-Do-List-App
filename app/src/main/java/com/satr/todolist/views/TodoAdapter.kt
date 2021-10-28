package com.satr.todolist.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.satr.todolist.R
import com.satr.todolist.database.model.TodoDataModel

class TodoAdapter(val tasks: List<TodoDataModel>, val viewModel: TodoViewModel) :
    RecyclerView.Adapter<TodoAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.task_title_textView)
        val detailsTextView: TextView = view.findViewById(R.id.task_details_textView)
        val dueDateTextView: TextView = view.findViewById(R.id.duedate_textView)
        val checkBox: CheckBox = view.findViewById(R.id.checkBox)
        val statusTextView: TextView = view.findViewById(R.id.status_textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}