package com.satr.todolist.views.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.satr.todolist.R
import com.satr.todolist.views.TodoViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ModalSheetBottomFragment : BottomSheetDialogFragment() {
    private val todoViewModel: TodoViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Round shape style
        setStyle(DialogFragment.STYLE_NORMAL, R.style.ThemeOverlay_Demo_BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_modal_sheet_bottom, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titleEditText: TextInputEditText = view.findViewById(R.id.title_editText_input)
        val detailsEditText: TextInputEditText = view.findViewById(R.id.details_editText)
        val dateEditText: TextInputEditText = view.findViewById(R.id.date_editText)
        val addButton: Button = view.findViewById(R.id.add_todo_Button)
        val cancelButton: Button = view.findViewById(R.id.cancel_Button)

        // Show the date picker in Focus action
        // To customize the calender colors: https://stackoverflow.com/questions/66958999/how-to-change-the-material-date-time-picker-background-color-in-android
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date").setTitleText("Select date").setTheme(R.style.Theme_App)
                .build()

        // date edit text click event
        dateEditText.setOnFocusChangeListener { view, b ->
            datePicker.show(requireActivity().supportFragmentManager, "datePicker")
            datePicker.addOnPositiveButtonClickListener {
                val selected = datePicker.selection
                dateEditText.setText(dateFormatted(selected))

            }
        }

        // add button click event
        addButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val details = detailsEditText.text.toString()
            val dueDate = dateEditText.text.toString()
            todoViewModel.addTask(title, false, details, dueDate)
            dismiss()
        }

        // cancel event
        cancelButton.setOnClickListener {
            dismiss()
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
}