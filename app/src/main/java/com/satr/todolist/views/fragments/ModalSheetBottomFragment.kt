package com.satr.todolist.views.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
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
import com.google.android.material.textfield.TextInputLayout
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

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titleEditText: TextInputEditText = view.findViewById(R.id.title_editText_input)
        // InputEditText
        val detailsEditText: TextInputEditText = view.findViewById(R.id.details_editText)
        val dateEditText: TextInputEditText = view.findViewById(R.id.date_editText)

        //Input layout
        val detailsLayout: TextInputLayout = view.findViewById(R.id.outlinedTextField)
        val dateLayout: TextInputLayout = view.findViewById(R.id.due_date_editText)

        val addButton: Button = view.findViewById(R.id.add_todo_Button)
        val cancelButton: Button = view.findViewById(R.id.cancel_Button)
        val descriptionImg: ImageView = view.findViewById(R.id.description_imageView)
        val calenderImg: ImageView = view.findViewById(R.id.calender_imageView)

        // Show the date picker in Focus action
        // To customize the calender colors: https://stackoverflow.com/questions/66958999/how-to-change-the-material-date-time-picker-background-color-in-android
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date").setTitleText("Select date").setTheme(R.style.Theme_App)
                .build()

        // date picker and editText will appears after clicking on the calender img
        calenderImg.setOnClickListener {
            datePicker.show(requireActivity().supportFragmentManager, "datePicker")
            datePicker.addOnPositiveButtonClickListener {
                val selected = datePicker.selection
                dateLayout.visibility = View.VISIBLE
                dateEditText.setText(dateFormatted(selected))

            }
        }

        // Show the details editText on click event
        descriptionImg.setOnClickListener {
            detailsLayout.visibility = View.VISIBLE
        }

        // add button click event
//        addButton.apply {
//            isEnabled = false
//            setTextColor(Color.GRAY)
//            setBackgroundColor(Color.WHITE)
//        }
//        if (titleEditText.text.toString().isNotEmpty()) {
//            addButton.apply {
//                isEnabled = true
//                setTextColor(Color.WHITE)
//                setBackgroundColor(R.color.main_primary_color)
//            }
//        }

        addButton.setOnClickListener {
            val title = titleEditText.text.toString()
            if(title.isNotEmpty()) {
                val details = detailsEditText.text.toString()
                val dueDate = dateEditText.text.toString()
                todoViewModel.addTask(title, false, details, dueDate)
                dismiss()
            } else
                Toast.makeText(requireContext(), "Please write your new task to add it", Toast.LENGTH_SHORT).show()
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