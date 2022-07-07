package com.example.todotestapp.presentation.listtask

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todotestapp.R

class ListTaskAdapter : RecyclerView.Adapter<ListTaskAdapter.MyListHolder>() {

//    var listData = emptyList<ToDoData>()
    class MyListHolder(itemView: View) : RecyclerView.ViewHolder(itemView){



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyListHolder {
            val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.layout_row,parent,false)
            return MyListHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: MyListHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


}