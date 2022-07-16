package com.example.todotestapp.presentation.listToDo.ui

import android.inputmethodservice.Keyboard
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todotestapp.R
import com.example.todotestapp.data.db.BaseListToDoResponse
import com.example.todotestapp.data.db.ListToDoResponse
import com.example.todotestapp.data.db.ToDo
import com.example.todotestapp.databinding.FragmentListTodoBinding

class ListToDoAdapter : RecyclerView.Adapter<ListToDoAdapter.MyListHolder>() {

     private var myList = emptyList<BaseListToDoResponse>()



    //    var listData = emptyList<ToDoData>()
    class MyListHolder(itemView: View) : RecyclerView.ViewHolder(itemView){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyListHolder {
            val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.layout_row,parent,false)
            return MyListHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: MyListHolder, position: Int) {
            holder.itemView.findViewById<TextView>(R.id.title_txt).text = myList[position].title
            holder.itemView.findViewById<TextView>(R.id.description_txt).text = myList[position].description


        when(myList[position].status)
             {
                 "completed"-> holder.itemView.findViewById<androidx.cardview.widget.CardView>(R.id.priority_indicator).setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.green))
                 "pending"-> holder.itemView.findViewById<androidx.cardview.widget.CardView>(R.id.priority_indicator).setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.Red))
             }

////        holder.itemView.findViewById<TextView>(R.id.title_txt).text = context.resources.getString(dataset[position].stringTitleId)
////            holder.itemView.findViewById<TextView>(R.id.description_txt).text = context.resources.getString(dataset[position].stringDescriptionId)
            holder.itemView.findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.row_background).setOnClickListener {
                val action = ListToDoFragmentDirections.actionListTaskFragmentToUpdateTaskFragment(myList[position])
                holder.itemView.findNavController().navigate(action)
            }
    }

    override fun getItemCount(): Int {
        return myList.size
//        return dataset.size
    }

    fun setData(newList: ListToDoResponse)
    {
        myList = newList.tasks!!
        notifyDataSetChanged()
    }
}