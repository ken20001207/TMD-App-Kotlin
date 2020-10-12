package com.yuanlin.tmd_app_native.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.yuanlin.tmd_app_native.R
import com.yuanlin.tmd_app_native.R.layout.todo_list_item
import com.yuanlin.tmd_app_native.models.TodoItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.todo_list_item.view.*
import okhttp3.*
import java.io.IOException

lateinit var todoListAdapter: TodoListAdapter

class MainActivity : AppCompatActivity() {

    fun refresh() {

        val request = Request.Builder()
            .url("https://tmd.linyuanlin.com/api/todos")
            .build()

        OkHttpClient()
            .newCall(request)
            .enqueue(object : Callback {

                override fun onResponse(call: Call, response: Response) {
                    runOnUiThread {
                        todoListAdapter.todoItems = Gson().fromJson<Array<TodoItem>>(
                            response.body!!.string(),
                            Array<TodoItem>::class.java
                        )
                        todoListAdapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call, e: IOException) {

                }

            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        todoListAdapter = TodoListAdapter(this, arrayOf<TodoItem>())
        displayTodoList.adapter = todoListAdapter
        displayTodoList.layoutManager = LinearLayoutManager(this)
        displayTodoList.addOnItemTouchListener(object: RecyclerView.OnItemTouchListener {
            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
                Log.i("a", rv.todoTitle.text.toString())
            }

            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                Log.i("c", "d")
                return true
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
                Log.i("e", "f")
            }

        })

        refresh()
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    lateinit var bg:LinearLayout
    lateinit var titleTextView:TextView

}

class TodoListAdapter(private var context: Context, var todoItems: Array<TodoItem>) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cell = LayoutInflater.from(context).inflate(todo_list_item, parent, false)
        val viewHolder = ViewHolder(cell)
        viewHolder.bg = cell.findViewById(R.id.todoBackground)
        viewHolder.titleTextView = cell.findViewById(R.id.todoTitle)
        return viewHolder
    }

    override fun getItemCount(): Int {
        Log.i("debug", todoItems.size.toString())
        return todoItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleTextView.text = todoItems[position].title
    }

}