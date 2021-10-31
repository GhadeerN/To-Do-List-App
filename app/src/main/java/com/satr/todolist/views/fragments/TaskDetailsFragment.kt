package com.satr.todolist.views.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.internal.TextWatcherAdapter
import com.google.android.material.textfield.TextInputEditText
import com.satr.todolist.R
import com.satr.todolist.database.model.TodoDataModel
import com.satr.todolist.views.TodoViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.properties.Delegates

// Flag
private lateinit var selectedTask: TodoDataModel
class TaskDetailsFragment : Fragment() {
    private val todoViewModel: TodoViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_details, container, false)
    }

    @SuppressLint("RestrictedApi")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titleEditText: EditText = view.findViewById(R.id.details_task_title_editText)
        val detailsEditText: EditText =
            view.findViewById(R.id.details_task_detail_editText)
        val dueDateEditText: EditText =
            view.findViewById(R.id.details_task_dateTime_editText)
        val creationDateTextView: TextView =
            view.findViewById(R.id.details_creation_date_textView)
        val deleteImageButton: ImageButton = view.findViewById(R.id.delete_imageButton)
        val saveImageButton: ImageButton = view.findViewById(R.id.save_imageButton)

        // Set the data inside the edit text
        todoViewModel.taskLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                titleEditText.setText(it.title)
                detailsEditText.setText(it.details)
                dueDateEditText.setText(it.dueDate)
                creationDateTextView.text = "Created at ${it.creationDate}"
                selectedTask = it
            }
        })
        // Delete task
        deleteImageButton.setOnClickListener {
            todoViewModel.deleteTask(selectedTask)
            findNavController().popBackStack()
        }
        // Update the values
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date").setTitleText("Select date").setTheme(R.style.Theme_App)
                .build()
        dueDateEditText.setOnFocusChangeListener { view, b ->
            datePicker.show(requireActivity().supportFragmentManager, "datePicker")
            datePicker.addOnPositiveButtonClickListener {
                val selected = datePicker.selection
                dueDateEditText.setText(dateFormatted(selected))
            }
        }
        var counter1 = 0
        var counter2 = 0
        var counter3 = 0

        setOnChangeListener(titleEditText, titleEditText, detailsEditText, dueDateEditText, saveImageButton)
        setOnChangeListener(detailsEditText, titleEditText, detailsEditText, dueDateEditText, saveImageButton)
        setOnChangeListener(dueDateEditText, titleEditText, detailsEditText, dueDateEditText, saveImageButton)

    }

    private fun setOnChangeListener(
        changeIn: EditText,
        titleEditText: EditText,
        detailsEditText: EditText,
        dueDateEditText: EditText,
        saveImageButton: ImageButton) {
        var counter1 = 0
        changeIn.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                counter1++
                // This condition is to omit the textChange from the database filling the edit text
                if (counter1 > 1) {
                    saveImageButton.setColorFilter(Color.parseColor("#66CC70"))
                    saveImageButton.setOnClickListener {
                        Log.d("InsideSave", "It work!")
                        // TODO don't forget completed button!
                        val title = titleEditText.text.toString()
                        val details = detailsEditText.text.toString()
                        val dueDate = dueDateEditText.text.toString()
                        selectedTask.title = title
                        selectedTask.details= details
                        selectedTask.dueDate = dueDate
                        todoViewModel.updateTask(
                            selectedTask
                        )
                        findNavController().popBackStack()
                    }
                }
            }
        })
    }
}

// The following function will format the date selected in the date picker
@RequiresApi(Build.VERSION_CODES.O)
fun dateFormatted(dateSelected: Long?): String {
    val dateTime: LocalDateTime =
        LocalDateTime.ofInstant(
            dateSelected?.let { Instant.ofEpochMilli(it) },
            ZoneId.systemDefault()
        )
    // The pattern letters means: E -> day name, L -> Month name, d -> day of month number, y -> the year
    // Resource: https://developer.android.com/reference/kotlin/java/time/format/DateTimeFormatter
    // TODO L don't show the month name, it shows only a number!
    return dateTime.format(DateTimeFormatter.ofPattern("E, MMM d, y"))
}

// Change the save imageButton color
fun changeImageBtnColor(view: ImageButton, counter: Int) {
    if (counter > 1)
        view.setColorFilter(Color.parseColor("#66CC70"))
}


