package com.example.interactivestandarttest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.interactivestandarttest.presentation.main.MainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, MainFragment())
                .commit()
        }
    }
}