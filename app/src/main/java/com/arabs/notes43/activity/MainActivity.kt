package com.arabs.notes43.activity

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.arabs.notes43.R

import kotlinx.android.synthetic.main.activity_main.*
import com.arabs.notes43.model.Contact
import com.arabs.notes43.model.Tag
import com.arabs.notes43.support.DatabaseHandler
import com.arabs.notes43.support.DatabaseHelper
import com.arabs.notes43.model.Todo
import android.util.EventLog.getTagName
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }





        test.text = "0.0"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun testDB(): Unit {
        val db = DatabaseHelper(this)

        // Creating tags
        val tag1 = Tag("Shopping")
        val tag2 = Tag("Important")
        val tag3 = Tag("Watchlist")
        val tag4 = Tag("Androidhive")

        // Inserting tags in db
        val tag1_id = db.createTag(tag1)
        val tag2_id = db.createTag(tag2)
        val tag3_id = db.createTag(tag3)
        val tag4_id = db.createTag(tag4)

        Log.d("Tag Count", "Tag Count: " + db.allTags.count())

        // Creating ToDos
        val todo1 = Todo("iPhone 5S", 0)
        val todo2 = Todo("Galaxy Note II", 0)
        val todo3 = Todo("Whiteboard", 0)

        val todo4 = Todo("Riddick", 0)
        val todo5 = Todo("Prisoners", 0)
        val todo6 = Todo("The Croods", 0)
        val todo7 = Todo("Insidious: Chapter 2", 0)

        val todo8 = Todo("Don't forget to call MOM", 0)
        val todo9 = Todo("Collect money from John", 0)

        val todo10 = Todo("Post new Article", 0)
        val todo11 = Todo("Take database backup", 0)

        // Inserting todos in db
        // Inserting todos under "Shopping" Tag
        val todo1_id = db.createToDo(todo1, longArrayOf(tag1_id))
        val todo2_id = db.createToDo(todo2, longArrayOf(tag1_id))
        val todo3_id = db.createToDo(todo3, longArrayOf(tag1_id))

        // Inserting todos under "Watchlist" Tag
        val todo4_id = db.createToDo(todo4, longArrayOf(tag3_id))
        val todo5_id = db.createToDo(todo5, longArrayOf(tag3_id))
        val todo6_id = db.createToDo(todo6, longArrayOf(tag3_id))
        val todo7_id = db.createToDo(todo7, longArrayOf(tag3_id))

        // Inserting todos under "Important" Tag
        val todo8_id = db.createToDo(todo8, longArrayOf(tag2_id))
        val todo9_id = db.createToDo(todo9, longArrayOf(tag2_id))

        // Inserting todos under "Androidhive" Tag
        val todo10_id = db.createToDo(todo10, longArrayOf(tag4_id))
        val todo11_id = db.createToDo(todo11, longArrayOf(tag4_id))

        Log.e("Todo Count", "Todo count: " + db.toDoCount)

        // "Post new Article" - assigning this under "Important" Tag
        // Now this will have - "Androidhive" and "Important" Tags
        db.createTodoTag(todo10_id, tag2_id)

        // Getting all tag names
        Log.d("Get Tags", "Getting All Tags")

        val allTags = db.allTags
        for (tag in allTags) {
            Log.d("Tag Name", tag.tagName)
        }

        // Getting all Todos
        Log.d("Get Todos", "Getting All ToDos")

        val allToDos = db.allToDos
        for (todo in allToDos) {
            Log.d("ToDo", todo.note)
        }

        // Getting todos under "Watchlist" tag name
        Log.d("ToDo", "Get todos under single Tag name")

        val tagsWatchList = db.getAllToDosByTag(tag3.tagName)
        for (todo in tagsWatchList) {
            Log.d("ToDo Watchlist", todo.note)
        }

        // Deleting a ToDo
        Log.d("Delete ToDo", "Deleting a Todo")
        Log.d("Tag Count", "Tag Count Before Deleting: " + db.toDoCount)

        db.deleteToDo(todo8_id)

        Log.d("Tag Count", "Tag Count After Deleting: " + db.toDoCount)

        // Deleting all Todos under "Shopping" tag
        Log.d("Tag Count",
                "Tag Count Before Deleting 'Shopping' Todos: "
                        + db.toDoCount)

        db.deleteTag(tag1, true)

        Log.d("Tag Count",
                "Tag Count After Deleting 'Shopping' Todos: "
                        + db.toDoCount)

        // Updating tag name
        tag3.tagName = "Movies to watch"
        db.updateTag(tag3)

        // Don't forget to close database connection
        db.closeDB()
    }


}
