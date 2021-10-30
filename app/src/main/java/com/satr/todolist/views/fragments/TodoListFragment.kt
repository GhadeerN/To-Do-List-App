package com.satr.todolist.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.satr.todolist.R
import com.satr.todolist.database.model.TodoDataModel
import com.satr.todolist.views.TodoAdapter
import com.satr.todolist.views.TodoViewModel

class TodoListFragment : Fragment() {
    val todoViewModel: TodoViewModel by activityViewModels()
    var tasks = mutableListOf<TodoDataModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO delete this line after you finish
//        todoViewModel.deleteAll()
        val addFloatButton: FloatingActionButton = view.findViewById(R.id.add_floatingButton)
        val taskRecyclerView: RecyclerView = view.findViewById(R.id.tasks_recyclerView)
        val completedTasksRecyclerView: RecyclerView = view.findViewById(R.id.completed_tasks_recyclerView)

        // Setting up the tasks recycler view content
        val tasksAdapter = TodoAdapter(tasks, todoViewModel)
        taskRecyclerView.adapter = tasksAdapter

        todoViewModel.getAllTasks.observe(viewLifecycleOwner, Observer {
            it?.let {
                tasks.clear()
                tasks.addAll(it)
                tasksAdapter.notifyDataSetChanged()
            }
        })
        // Add task button event
        addFloatButton.setOnClickListener {
            val sheetButton = ModalSheetBottomFragment()
            sheetButton.show(requireActivity().supportFragmentManager, "sheetButton")
        }
    }

}