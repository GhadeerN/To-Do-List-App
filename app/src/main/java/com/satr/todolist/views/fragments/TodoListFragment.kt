package com.satr.todolist.views.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.satr.todolist.R
import com.satr.todolist.database.model.SectionDataModel
import com.satr.todolist.database.model.TodoDataModel
import com.satr.todolist.databinding.FragmentTodoListBinding
import com.satr.todolist.views.TodoViewModel
import com.satr.todolist.views.adapters.CompletedTasksAdapter
import com.satr.todolist.views.adapters.ParentAdapter

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
//        todoViewModel.deleteAll()
        val addFloatButton: FloatingActionButton = view.findViewById(R.id.add_floatingButton)
        val completedTasksRecyclerView: RecyclerView =
            view.findViewById(R.id.completed_tasks_recyclerView)

        todoViewModel.getAllTasks.observe(viewLifecycleOwner, {
            it?.let {
                tasks.clear()
                tasks.addAll(it)
                sectionData = todoViewModel.createSectionData(tasks)
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
            it?.let {
                completedTasks.clear()
                completedTasks.addAll(it)
                completedTasksAdapter.notifyDataSetChanged()
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
}
