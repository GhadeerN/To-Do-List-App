package com.satr.todolist.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.satr.todolist.R

class TodoListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val addFloatButton: FloatingActionButton = view.findViewById(R.id.add_floatingButton)
        addFloatButton.setOnClickListener {
            val sheetButton = ModalSheetBottomFragment()
            sheetButton.show(requireActivity().supportFragmentManager, "sheetButton")
        }
    }

}