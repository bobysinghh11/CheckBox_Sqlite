package com.stuffshuf.checkbox_sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.etNewItem
import kotlinx.android.synthetic.main.list_items.*
import kotlinx.android.synthetic.main.list_items.view.*

class MainActivity : AppCompatActivity() {

    var tasks = arrayListOf<TableTask.Task>()
    val dbHelper = MyDbHelper(this)
     lateinit var taskDb:SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        taskDb = dbHelper.writableDatabase

        tasks = TableTask.getAllTasks(taskDb)

        val taskadapter = TaskAdapter(tasks)

        lvTodolist.adapter = taskadapter


        btnAdd.setOnClickListener {

            if (etNewItem.text.length > 3) {

                TableTask.insertTask(
                    taskDb, TableTask.Task(
                        null,
                        etNewItem.text.toString(),
                        false
                    )
                )
                tasks = TableTask.getAllTasks(taskDb)

                // lvTodolist.adapter=taskadapter
                taskadapter.updataTask(tasks)
                etNewItem.setText("")
                // taskadapter.tasks
            } else {
                Toast.makeText(this, "Please Enter Character Above 3", Toast.LENGTH_LONG).show()
            }

        }

        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val itemss = newText
                // val items= searchview
                tasks = TableTask.searchTask(taskDb, itemss!!)
                taskadapter.updataTask(tasks)
                return true
            }

        })


        /*   tvSearch.addTextChangedListener(object :TextWatcher{
               override fun afterTextChanged(s: Editable?) {

               }

               override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

               }

               override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                   val items= s
                   tasks=TableTask.searchTask(taskDb, items.toString())
                   taskadapter.updataTask(tasks)
               }

           })*/



        btnsort.setOnClickListener {

            tasks = TableTask.sortTask(taskDb)
            taskadapter.updataTask(tasks)

        }


        btnDel.setOnClickListener {

                TableTask.deleteTask(taskDb)
                tasks = TableTask.getAllTasks(taskDb)
                taskadapter.updataTask(tasks)

        }



//    lvTodolist.onItemClickListener = object : AdapterView.OnItemClickListener {
//            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//
//                val thisTask = taskadapter.getItem(position)
//                thisTask.done = !thisTask.done
//                checkbox.isChecked =thisTask.done
//                TableTask.updateTask(taskDb, thisTask)
//                tasks = TableTask.getAllTasks(taskDb)
//                taskadapter.updataTask(tasks)
//
//
//            }
//
//        }

        taskadapter.itemListClick=object :ItemListClick{
            override fun delbuttonClick(task: TableTask.Task, position: Int) {
                val masks=taskadapter.getItem(position).id
                TableTask.delTask(taskDb, masks)
                tasks=TableTask.getAllTasks(taskDb)
                taskadapter.updataTask(tasks)
            }

            override fun checkBoxClick(task: TableTask.Task, position: Int) {
                task.done=!task.done
                TableTask.updateTask(taskDb, task)
                tasks=TableTask.getAllTasks(taskDb)
                taskadapter.updataTask(tasks)
            }

        }




    }

    }