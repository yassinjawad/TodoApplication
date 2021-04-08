package com.example.todoapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * This function gets called when the addButton is clicked. It
     * adds a todo into the database.
     * @param view MainActivity view
     */
    fun addTodo(view: View?){

    }

    /**
     * This function gets called when the Delete Button is clicked. It
     * delete selected todo feom the database.
     * @param view MainActivity view
     */
    fun deleteTodo(view: View?){

    }
}