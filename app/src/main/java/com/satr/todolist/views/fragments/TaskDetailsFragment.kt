package com.satr.todolist.views.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
import com.satr.todolist.views.objects.DateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.properties.Delegates

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
        val markCompletedButton: Button = view.findViewById(R.id.mark_as_completed_Button)
        val markUncompletedButton: Button = view.findViewById(R.id.uncompleted_Button)

        // Set the data inside the edit text
        todoViewModel.taskLiveData.observe(viewLifecycleOwner, {
            it?.let {
                titleEditText.setText(it.title)
                detailsEditText.setText(it.details)
                dueDateEditText.setText(it.dueDate)

                creationDateTextView.text = "Created at ${it.creationDate}"
                selectedTask = it
                //If the task is completed -> stock on the title and hide mark completed button
                if (selectedTask.checked) {
                    titleEditText.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    markCompletedButton.visibility = View.GONE
                    markUncompletedButton.visibility = View.VISIBLE

                    dueDateEditText.apply {
                        keyListener = null
                        setTextColor(Color.GRAY)
                        isClickable = false
                        setEnabled(false);
                    }
                    titleEditText.apply {
                        keyListener = null
                        setTextColor(Color.GRAY)
                    }
                    detailsEditText.apply {
                        keyListener = null
                        setTextColor(Color.GRAY)
                    }
                }
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
                .setTitleText("Select date").setTheme(R.style.Theme_App)
                .build()
        dueDateEditText.setOnClickListener {
            datePicker.show(requireActivity().supportFragmentManager, "datePicker")
            datePicker.addOnPositiveButtonClickListener {
                val selected = datePicker.selection
                dueDateEditText.setText(DateFormat.dateFormatted(selected))
            }
        }

        // Mark completed button functionality
        markCompletedButton.setOnClickListener {
            selectedTask.checked = true
            todoViewModel.updateTask(selectedTask)
            findNavController().popBackStack()
        }

        // Uncompleted button
        markUncompletedButton.setOnClickListener {
            selectedTask.checked = false
            todoViewModel.updateTask(selectedTask)
            findNavController().popBackStack()
        }

        setOnChangeListener(
            titleEditText,
            titleEditText,
            detailsEditText,
            dueDateEditText,
            saveImageButton
        )
        setOnChangeListener(
            detailsEditText,
            titleEditText,
            detailsEditText,
            dueDateEditText,
            saveImageButton
        )
        setOnChangeListener(
            dueDateEditText,
            titleEditText,
            detailsEditText,
            dueDateEditText,
            saveImageButton
        )

    }

    // This function is to set a change listener to each edit text,
    private fun setOnChangeListener(
        changeIn: EditText,
        titleEditText: EditText,
        detailsEditText: EditText,
        dueDateEditText: EditText,
        saveImageButton: ImageButton
    ) {
        var counter1 = 0
        changeIn.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                counter1++
                // This condition is to omit the textChange from the database filling the edit text
                // to only consider the user changes on the edit text
                if (counter1 > 1) {
                    saveImageButton.setColorFilter(Color.parseColor("#66CC70"))
                    saveImageButton.setOnClickListener {
                        val title = titleEditText.text.toString()
                        val details = detailsEditText.text.toString()
                        val dueDate = dueDateEditText.text.toString()
                        selectedTask.title = title
                        selectedTask.details = details
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


