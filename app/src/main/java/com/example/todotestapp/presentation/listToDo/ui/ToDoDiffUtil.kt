package com.example.todotestapp.presentation.listToDo.ui

import androidx.recyclerview.widget.DiffUtil
import com.example.todotestapp.data.db.BaseListToDoResponse

class ToDoDiffUtil( private val oldList:List<BaseListToDoResponse>,  private val newList:List<BaseListToDoResponse>) : DiffUtil.Callback()
{
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].taskId == newList[newItemPosition].taskId
                && oldList[oldItemPosition].title == newList[newItemPosition].title
                && oldList[oldItemPosition].description == newList[newItemPosition].description
                && oldList[oldItemPosition].status == newList[newItemPosition].status
    }
}
