package com.stuffshuf.checkbox_sqlite

interface ItemListClick {
    fun checkBoxClick(task:TableTask.Task, position:Int)
    fun delbuttonClick(task: TableTask.Task, position: Int)
}
