package com.stuffshuf.checkbox_sqlite

import android.content.ClipData
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import kotlinx.android.synthetic.main.list_items.view.*

class TaskAdapter(var tasks: ArrayList<TableTask.Task>) : BaseAdapter() {

    var itemListClick:ItemListClick?=null


    fun updataTask(newTasks: ArrayList<TableTask.Task>) {
        tasks.clear()
        tasks.addAll(newTasks)
        notifyDataSetChanged()
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val li = parent!!.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = li.inflate(R.layout.list_items, parent, false)
        //view.findViewById<TextView>(R.id.etNewItem).text = getItem(position).task
        view.etNewItem.text=getItem(position).task

        view.checkbox.setOnClickListener {
            itemListClick?.checkBoxClick(getItem(position), position)

        }
        view.del.setOnClickListener {
            itemListClick?.delbuttonClick(getItem(position), position)
        }

        if (getItem(position).done) {
            view.findViewById<TextView>(R.id.etNewItem).setTextColor(Color.RED)
            view.findViewById<CheckBox>(R.id.checkbox).isChecked=true
        }


        return view
    }



    override fun getItem(position: Int):TableTask.Task= tasks[position]


    override fun getItemId(position: Int): Long = 0

    override fun getCount(): Int =  tasks.size

}
