package com.example.todoapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    // declare EditText as mutable field using null safety
    var todoEditText: EditText? = null

    // declare RecyclerVie as mutable field using null safety
    var todoRecyclerView: RecyclerView? = null

    // declare DBHandler as mutable field using null safety
    var dbHandler: DBHandler? = null

    // declare a ToDoAdapter as mutable field
    //
    lateinit var toDoAdapter: ToDoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // make EditText refer to actual EditText in activity_main layout resource
        todoEditText = findViewById<View>(R.id.todoEditText) as EditText

        // make EditText refer to actual EditText in activity_main layout resource
        todoRecyclerView = findViewById<View>(R.id.todoRecyclerView) as RecyclerView

        // fully initialize dbHandler
        dbHandler = DBHandler(this, null)

        // initialize the toDoAdapter
        toDoAdapter = ToDoAdapter(dbHandler!!.todos)

        todoRecyclerView!!.adapter = toDoAdapter

        todoRecyclerView!!.layoutManager = LinearLayoutManager(this)
    }

    /**
     * This function gets called when the addButton is clicked. It
     * adds a todo into the database.
     * @param view MainActivity view
     */
    fun addTodo(view: View?){
        val todoName = todoEditText!!.text.toString()

        // trim variable and check if it's equal to an empty String

        if (todoName.trim() == "") {
            // display "Please Enter a Todo Toast
            Toast.makeText(this, "Please Enter a Todo!", Toast.LENGTH_LONG).show()
        } else {
            // ask Kotlin to check if the dbHandler is null
            // if it isn't, apply all of the code in the let block to it
            dbHandler?.let {
                // call method in toDoAdapter that will add ToDo
                // into the database
                toDoAdapter.addToDo(it, todoName)
            }

            Toast.makeText(this, "Todo added!", Toast.LENGTH_LONG).show()

            todoEditText!!.text.clear()

            notifyAdapter()
        }


    }

    /**
     * This function gets called when the Delete Button is clicked. It
     * delete selected todo from the database.
     * @param view MainActivity view
     */
    fun deleteTodo(view: View?){

        // ask Kotlin to check if the dbHandler is null
        // if it isn't null, pass it to the deleteToDos method in
        // the toDoAdapter
        dbHandler?.let {
            toDoAdapter.deleteTodos(it)
        }

        // call notifyAdapter method
        notifyAdapter()
    }

    private fun notifyAdapter(){
        toDoAdapter.todos = dbHandler!!.todos
    }
}