package com.example.todoapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf

class DBHandler(context: Context?, cursorFactory: SQLiteDatabase.CursorFactory?) :
SQLiteOpenHelper(context, DATABASE_NAME, cursorFactory, DATABASE_VERSION ){

    /**
     * Vreates database table
     * @param db reference to the todoapp database
     */
    override fun onCreate(db: SQLiteDatabase) {
        // define create statement for todo table
        val query = "CREATE TABLE " + TABLE_TODO_LIST + "(" +
                COLUMN_TODO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TODO_ISCHECKED + " TEXT, " +
                COLUMN_TODO_NAME + " TEXT);"

        // execute create statement
        db.execSQL(query)
    }

    /**
     * Creates a new version of the database.
     * @param db reference todoapp database.
     * @param oldVersion old version of the database
     * @param newVersion new version of the database
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // define drop statement for the todo table
        val query = "DROP TABLE IF EXISTS " + TABLE_TODO_LIST

        // execute the drop statement
        db.execSQL(query)

        // call method that create the table
        onCreate(db)
    }

    /**
     * This method gets called when the add button of the MainActivity
     * gets clicked. It insert a new row in the todo table
     * @param todoName todo name
     */
    fun addTodo(todoName: String?){
        // get reference to todoapp database
        val db = writableDatabase

        // initialize a ContentValues object
        val values = ContentValues()

        // put data into the ContentValues object
        values.put(COLUMN_TODO_NAME, todoName)
        values.put(COLUMN_TODO_ISCHECKED, "false")

        // insert data in ContentValues object into todo table
        db.insert(TABLE_TODO_LIST, null, values)

        // close database connection
        db.close()
    }

    /**
     * This method gets clled when the MainActivity is created and
     * when the add and delete buttons get clicked
     * @return MutableList of ToDos that contains all of the data
     * in the todo table.
     */
    val todos: MutableList<ToDo>
        get() {
            // get a reference to the todoapp database
            val db = writableDatabase

            // define select statement and store its return in an
            // immutable Cursor
            val query = "SELECT * FROM " + TABLE_TODO_LIST

            // execute the select statement and store its return in an
            // immutable Cursor
            val c = db.rawQuery(query, null)

            // create MutableList of ToDos that will be
            // returned by the method
            val list: MutableList<ToDo> = ArrayList()

            // Loop through the rows in the Cursor
            while (c.moveToNext()) {
                // create an immutable ToDo using the data in the current
                // row in the Cursor
                val todo: ToDo = ToDo(c.getInt(c.getColumnIndex("_id")),
                    c.getString(c.getColumnIndex("name")),
                    c.getString(c.getColumnIndex("is_checked")).toBoolean());
                // add the ToDo that was just created into the MutableList
                // of ToDos
                list.add(todo)
            }

            // close database reference
            db.close()

            // return the MutableList of ToDos
            return  list
        }

    companion object{
        // initialize constants for the DB name and version
        private const val DATABASE_NAME = "todoapp.db"
        private const val DATABASE_VERSION = 1

        // initialize constants for the todo table
        private const val TABLE_TODO_LIST = "todo"
        private const val COLUMN_TODO_ID = "_id"
        private const val COLUMN_TODO_NAME = "name"
        private const val COLUMN_TODO_ISCHECKED = "is_checked"
    }

}