package com.satr.todolist.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.satr.todolist.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        // To direct the user from Splash screen to the main activity
        val intent = Intent(this, MainActivity::class.java)
        object : CountDownTimer(1500, 1000) {
            override fun onTick(p0: Long) {}

            override fun onFinish() {
                startActivity(intent)
            }
        }.start()
    }
}