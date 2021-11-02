package com.satr.todolist.views.fragments

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.satr.todolist.R
import com.satr.todolist.database.model.SectionDataModel
import com.satr.todolist.database.model.TodoDataModel
import com.satr.todolist.databinding.FragmentTodoListBinding
import com.satr.todolist.views.TodoViewModel
import com.satr.todolist.views.adapters.CompletedTasksAdapter
import com.satr.todolist.views.adapters.ParentAdapter
import com.satr.todolist.views.objects.DateFormat

class TodoListFragment : Fragment() {
    // For the fragment binding
    private var _binding: FragmentTodoListBinding? = null
    private val binding get() = _binding!!

    // For tasks recycler views
    private val todoViewModel: TodoViewModel by activityViewModels()

    // List for the uncompleted tasks & for the sections data
    private var tasks = mutableListOf<TodoDataModel>()
    private var sectionData = mutableListOf<SectionDataModel>()

    // List for completed recyclerView
    private var completedTasks = mutableListOf<TodoDataModel>()

    // Notification builder object
    private val CHANNEL_ID = "notification-01"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodoListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO delete this line after you finish
 //todoViewModel.deleteAll()
        val addFloatButton: FloatingActionButton = view.findViewById(R.id.add_floatingButton)
        val completedTasksRecyclerView: RecyclerView =
            view.findViewById(R.id.completed_tasks_recyclerView)

        // Get the IDs for the start up image and textViews
        val startUpImg: ImageView = view.findViewById(R.id.start_imageView)
        val startUpText1: TextView = view.findViewById(R.id.textView)
        val startUpText2: TextView = view.findViewById(R.id.textView2)
        todoViewModel.getAllTasks.observe(viewLifecycleOwner, {
//            startUpImg.visibility = View.INVISIBLE
//            startUpText1.visibility = View.INVISIBLE
//            startUpText2.visibility = View.INVISIBLE
            it?.let {
                tasks.clear()
                tasks.addAll(it)
//                startUp(tasks, view)
//                if (tasks.isNullOrEmpty()) {
//                    startUpImg.visibility = View.VISIBLE
//                    startUpText1.visibility = View.VISIBLE
//                    startUpText2.visibility = View.VISIBLE
//                } else {
//                    startUpImg.visibility = View.GONE
//                    startUpText1.visibility = View.GONE
//                    startUpText2.visibility = View.GONE
//                }
                sectionData = todoViewModel.createSectionData(tasks)
                // Notifications
                // Filter the "Past" tasks and assign it to pastTasks variable
                val pastTasks = DateFormat.isDueDateApproaching(tasks)
                var counter = 0
                // To go over the past due date and send a notifier for it
                pastTasks.forEach { todoDataModel ->
                    //TODO Change the message to user friendly one :)
                        buildNotification(
                            "To do List",
                            "${todoDataModel.title} due date is approaching!",
                            counter ++
                        )
                }

                // Setting our adapter
                binding.mainRecyclerView.apply {
                    adapter = ParentAdapter(sectionData, todoViewModel)
                }
            }
        })


        // Add task button event
        addFloatButton.setOnClickListener {
            val sheetButton = ModalSheetBottomFragment()
            sheetButton.show(requireActivity().supportFragmentManager, "sheetButton")
        }

        // Completed Tasks recyclerView
        val completedTextView: TextView = view.findViewById(R.id.completed_textView)
        val divider: View = view.findViewById(R.id.divider4)

        val completedTasksAdapter = CompletedTasksAdapter(completedTasks, todoViewModel)
        completedTasksRecyclerView.adapter = completedTasksAdapter

        todoViewModel.getAllCompletedTasks.observe(viewLifecycleOwner, {
//            startUpImg.visibility = View.INVISIBLE
//            startUpText1.visibility = View.INVISIBLE
//            startUpText2.visibility = View.INVISIBLE
            it?.let {
                completedTasks.clear()
                completedTasks.addAll(it)
                completedTasksAdapter.notifyDataSetChanged()
//                startUp(completedTasks, view)
                // Show the number of completed tasks & the divider if there was completed tasks
                if(completedTasks.size > 0) {
                    completedTextView.text = getString(R.string.completed_text, completedTasks.size)
                    divider.visibility = View.VISIBLE
                    completedTextView.visibility = View.VISIBLE
                } else {
                    divider.visibility = View.GONE
                    completedTextView.visibility = View.GONE
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    // Change the start up images & text visibility
    fun startUp(tasks: List<TodoDataModel>, view: View) {
        // Get the IDs for the start up image and textViews
        val startUpImg: ImageView = view.findViewById(R.id.start_imageView)
        val startUpText1: TextView = view.findViewById(R.id.textView)
        val startUpText2: TextView = view.findViewById(R.id.textView2)

        startUpImg.visibility = View.INVISIBLE
        startUpText1.visibility = View.INVISIBLE
        startUpText2.visibility = View.INVISIBLE
        // Change the visibility of start up based on the tasks number
        if (tasks.isNullOrEmpty()) {
            startUpImg.visibility = View.VISIBLE
            startUpText1.visibility = View.VISIBLE
            startUpText2.visibility = View.VISIBLE
        } else {
            startUpImg.visibility = View.GONE
            startUpText1.visibility = View.GONE
            startUpText2.visibility = View.GONE
        }
    }

    // Setting notification functions
    fun buildNotification(title: String, contentText: String, notificationId: Int) {
        createNotificationChannel()
        var builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_calendar)
            .setContentTitle(title)
            .setContentText(contentText)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(requireContext())) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, builder.build())
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "notificationChannel"
            val descriptionText = "due date notification channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getActivity()?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
