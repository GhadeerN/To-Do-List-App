package com.satr.todolist.views.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.satr.todolist.R
import com.satr.todolist.database.model.SectionDataModel
import com.satr.todolist.databinding.StatusSectionRecyclerBinding
import com.satr.todolist.views.TodoViewModel

// SectionDataModel -> section, list of TodoDataModel
class ParentAdapter(val list: List<SectionDataModel>, val viewModel: TodoViewModel) :
    RecyclerView.Adapter<ParentAdapter.ViewHolder>() {
    // StatusSectionRecyclerBinding -> the data binding of fragment_todo_list.xml
    // StatusSectionRecyclerBinding is parent recycler view
    class ViewHolder(val viewDataBinding: StatusSectionRecyclerBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = StatusSectionRecyclerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var section = list[position]
        val sectionTextView = holder.viewDataBinding.sectionTextView

        sectionTextView.text = list[position].section
        when (section.section) {
            "Overdue" -> sectionTextView.setTextColor(Color.parseColor("#ED847E"))
            "Today" -> sectionTextView.setTextColor(R.color.primary_color)
            "Upcoming Tasks" -> sectionTextView.setTextColor(R.color.main_primary_color)
        }

        holder.viewDataBinding.inparentChildRecycler.apply {
            adapter = ChildAdapter(section.list, viewModel)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}