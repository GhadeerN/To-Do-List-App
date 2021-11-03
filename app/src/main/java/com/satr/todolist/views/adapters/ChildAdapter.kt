package com.satr.todolist.views.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.satr.todolist.R
import com.satr.todolist.database.model.TodoDataModel
import com.satr.todolist.databinding.ItemLayoutBinding
import com.satr.todolist.views.objects.DateFormat
import com.satr.todolist.views.TodoViewModel

class ChildAdapter(private val list: List<TodoDataModel>, val viewModel: TodoViewModel) :
    RecyclerView.Adapter<ChildAdapter.ViewHolder>() {
    class ViewHolder(val viewDataBinding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = list[position]

        // Setting the data in our item layout components
        holder.viewDataBinding.apply {
            taskTitleTextView.text = task.title
            duedateTextView.text = DateFormat.dueDateCardFormatter(task.dueDate)
            taskDetailsTextView.text = task.details
            checkBox.isChecked = task.checked
        }

        holder.itemView.setOnClickListener {
            // Send data to the details fragment
            viewModel.taskLiveData.postValue(task)
            it.findNavController().navigate(R.id.action_todoListFragment_to_taskDetailsFragment2)
        }

        holder.viewDataBinding.checkBox.setOnClickListener {
            task.checked = holder.viewDataBinding.checkBox.isChecked
            viewModel.updateTask(task)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}