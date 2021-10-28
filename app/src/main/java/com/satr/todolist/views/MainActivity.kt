package com.satr.todolist.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.satr.todolist.R
import com.satr.todolist.repositories.TodoRepository

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        TodoRepository.init(this)
    }
}