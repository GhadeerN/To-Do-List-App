package com.satr.todolist.views.adapters

import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.satr.todolist.R
import com.satr.todolist.database.model.TodoDataModel
import com.satr.todolist.views.DateFormat
import com.satr.todolist.views.TodoViewModel

// This adapter is for completed tasks recycler view
class CompletedTasksAdapter(private val completed: List<TodoDataModel>, val viewModel: TodoViewModel) :
    RecyclerView.Adapter<CompletedTasksAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.task_title_textView)
        val detailsTextView: TextView = view.findViewById(R.id.task_details_textView)
        val dueDateTextView: TextView = view.findViewById(R.id.duedate_textView)
        val checkBox: CheckBox = view.findViewById(R.id.checkBox)
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = completed[position]
        holder.apply {
            titleTextView.text = task.title
            titleTextView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            titleTextView.setTextColor(Color.GRAY)
            detailsTextView.text = task.details
            dueDateTextView.text = DateFormat.dueDateCardFormatter(task.dueDate)
            checkBox.isChecked = task.checked
        }

        holder.checkBox.setOnClickListener {
            task.checked = holder.checkBox.isChecked
            viewModel.updateTask(task)
        }

        // Clicks on card -> go to details fragment
        holder.itemView.setOnClickListener {
            // Send data to the details fragment
            viewModel.taskLiveData.postValue(task)
            it.findNavController().navigate(R.id.action_todoListFragment_to_taskDetailsFragment2)
        }
    }

    override fun getItemCount(): Int {
        return completed.size
    }
}