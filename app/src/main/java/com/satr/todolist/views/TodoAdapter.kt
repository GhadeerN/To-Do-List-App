package com.satr.todolist.views

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.navigation.findNavController
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

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]
        var past = 0
        var today = 0
        var upcoming = 0
        // Conditions for showing the status for first card only
        if (task.status == "Past" && past == 0) {
            holder.apply {
                past++
                statusTextView.setTextColor(R.color.past_status_text)
                statusTextView.text = task.status
                // Change the status visibility to gone on other cards
                statusTextView.visibility = View.GONE
            }
        }

        if (task.status == "Today" && today == 0) {
            holder.apply {
                today++
                statusTextView.setTextColor(R.color.main_primary_color)
                statusTextView.text = task.status
                statusTextView.visibility = View.GONE
            }
        }

        if (task.status == "Upcoming" && upcoming == 0) {
            holder.apply {
                upcoming++
                statusTextView.setTextColor(R.color.black)
                statusTextView.text = task.status
                statusTextView.visibility = View.GONE
            }
        }

        // Setting up the values for other views
        holder.apply {
            titleTextView.text = task.title
            detailsTextView.text = task.details
            //TODO format the due date to month name and day only
            dueDateTextView.text = task.dueDate
            checkBox.isChecked = task.checked
        }

        // View card details on click event
        holder.itemView.setOnClickListener {
            // Send data to the details fragment
            viewModel.taskLiveData.postValue(task)
            it.findNavController().navigate(R.id.action_todoListFragment_to_taskDetailsFragment2)
        }

        holder.checkBox.setOnClickListener {
            task.checked = holder.checkBox.isChecked
            viewModel.updateTask(task)
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}