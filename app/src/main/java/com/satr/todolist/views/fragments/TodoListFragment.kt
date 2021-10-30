package com.satr.todolist.views.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.satr.todolist.R
import com.satr.todolist.database.model.SectionDataModel
import com.satr.todolist.database.model.TodoDataModel
import com.satr.todolist.databinding.FragmentTodoListBinding
import com.satr.todolist.views.TodoViewModel
import com.satr.todolist.views.adapters.ParentAdapter

class TodoListFragment : Fragment() {
    private var _binding: FragmentTodoListBinding? = null
    private val binding get() = _binding!!
    private val todoViewModel: TodoViewModel by activityViewModels()
    var tasks = mutableListOf<TodoDataModel>()
    private var sectionData = mutableListOf<SectionDataModel>()
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
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
