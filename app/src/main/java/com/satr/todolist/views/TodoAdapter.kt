package com.satr.todolist.views

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.satr.todolist.R
import com.satr.todolist.database.model.TodoDataModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class TodoAdapter(val tasks: List<TodoDataModel>, val viewModel: TodoViewModel) :
    RecyclerView.Adapter<TodoAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.task_title_textView)
        val detailsTextView: TextView = view.findViewById(R.id.task_details_textView)
        val dueDateTextView: TextView = view.findViewById(R.id.duedate_textView)
        val checkBox: CheckBox = view.findViewById(R.id.checkBox)
        val statusTextView: TextView = view.findViewById(R.id.status_textView_element1)
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
    @SuppressLint("ResourceAsColor", "WrongConstant", "SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]
        var past = 0
        var today = 0
        var upcoming = 0
        var noDueDate = 0

        val statusWithTasks = groupByStatus(tasks)
        Log.d("statusAll", statusWithTasks.toString())
        if (task.status == "Past")
            holder.statusTextView.setTextColor(Color.RED)

        for (i in statusWithTasks) {
            holder.statusTextView.text = i.key
            Log.d("insideLoop", i.toString())
            for (r in 0 until i.value.size) {
                holder.titleTextView.text = i.value[r].title
                holder.detailsTextView.text = i.value[r].details
                holder.dueDateTextView.text = i.value[r].dueDate
                holder.checkBox.isChecked = i.value[r].checked
            }
        }
//        val singleStatusWithTask = statusWithTasks[task.status]
//        Log.d("singleStatus", singleStatusWithTask.toString())
//        holder.statusTextView.text = task.dueDate?.let { setStatus(it) } ?: "No due date"
//        if (singleStatusWithTask != null) {
//            for (i in singleStatusWithTask) {
////                holder.statusTextView.text = i.status
//                Log.d("CheckStatus", "Status: ${i.status}")
//                holder.apply {
////                    statusTextView.text = i.status
//                    statusTextView.visibility = View.GONE
//                    titleTextView.text = i.title
//                    detailsTextView.text = i.details
//                    dueDateTextView.text = i.dueDate?.let { dateFormat(it) }
//                    checkBox.isChecked = i.checked
//                }
//            }
//        }

        // Conditions for showing the status for first card only
//        if (task.status == "Past" && past == 0) {
//            holder.apply {
//                statusTextView.text = task.status
//                statusTextView.setTextColor(R.color.past_status_text)
//                statusTextView.visibility = if (statusTextView.visibility == View.VISIBLE)
//                    View.GONE
//                else
//                    View.VISIBLE
//            }
//            past++
//        }
        // Setting up the values for other views
//        holder.apply {
//            statusTextView.text = task.status
//            titleTextView.text = task.title
//            detailsTextView.text = task.details
//            //TODO format the due date to month name and day only
//            dueDateTextView.text = task.dueDate?.let { dateFormat(it) }
//            checkBox.isChecked = task.checked
//        }

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

    @RequiresApi(Build.VERSION_CODES.O)
    fun groupByStatus(list: List<TodoDataModel>): Map<String, List<TodoDataModel>> {
        var newList = mutableListOf<TodoDataModel>()
        for(i in list) {
            var currentStatus = if (i.dueDate.isNotEmpty())
                setStatus(i.dueDate)
            else "No due date"
//            else "No due date"
            i.status = currentStatus
            newList.add(i)
        }
        return newList.groupBy { it.status }.toSortedMap()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun dateFormat(date: String): String {
    return if (date.isNotEmpty()) {
        val localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("E, MMM d, y"))
        localDate.format(DateTimeFormatter.ofPattern("MMM d"))
    } else ""
//    val dateFormat = SimpleDateFormat("M d")
//    return dateFormat.format(Date(localDate))
}

// Get current date
fun getCurrentDate(): String {
    val dateFormat = SimpleDateFormat("E, MMM d, y")
    return dateFormat.format(Date())
}

// Get the tasks status based on selected due date
@RequiresApi(Build.VERSION_CODES.O)
fun setStatus(dueDate: String): String {
    val currentDate = getCurrentDate()
    // Here I converted the date from string to local date to use date comparisons functions
    // Resource: https://www.ictdemy.com/kotlin/oop/date-and-time-in-kotlin-parsing-and-comparing
    val localDate = LocalDate.parse(currentDate, DateTimeFormatter.ofPattern("E, MMM d, y"))
    val dueDateLocal = LocalDate.parse(dueDate, DateTimeFormatter.ofPattern("E, MMM d, y"))
    return when {
        dueDateLocal.isEqual(localDate) -> {
            "Today"
        }
        dueDateLocal.isBefore(localDate) -> {
            "Past"
        }
        dueDateLocal.isAfter(localDate) -> "Upcoming Tasks"
        else -> "No due date"
    }
}

