package com.example.todotestapp.presentation.listToDo.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todotestapp.R
import com.example.todotestapp.data.db.BaseListToDoResponse
import com.example.todotestapp.data.db.ListToDoResponse
import com.example.todotestapp.databinding.LayoutRowBinding
import java.text.SimpleDateFormat
import java.util.*

class ListToDoAdapter : RecyclerView.Adapter<ListToDoAdapter.MyListHolder>() {

    private var myList = emptyList<BaseListToDoResponse>()
    var sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    var output: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")



   inner class MyListHolder(val binding: LayoutRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyListHolder {
        val adapterLayout =  LayoutRowBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return MyListHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: MyListHolder, position: Int) {

            holder.itemView.findViewById<TextView>(R.id.title_txt).text = myList[position].title
            holder.itemView.findViewById<TextView>(R.id.description_txt).text = myList[position].description

           val time1: String = getDateTime(myList[position].creationTime).toString()
           val time2: String = getDateTime(myList[position].lastModificationTime).toString()
           val oldDate: Date = output.parse(time1) as Date
           val newDate: Date = output.parse(time2) as Date
           val presDate = Date()

           val diff: Long = presDate.time - oldDate.time
           val seconds = diff / 1000

            if(seconds > 86400)
            {
                holder.binding.createdAt.text  = "Created : " + time1.subSequence(0,10)
            }
            else
            {
                holder.itemView.findViewById<TextView>(R.id.created_at).text = "Created : " + time1.subSequence(11,16)
            }

            val diffa: Long = presDate.time - newDate.time
            val secondsa = diffa / 1000

            if(secondsa > 86400)
            {
                holder.itemView.findViewById<TextView>(R.id.modifited_at).text = "Modified : " + time2.subSequence(0,10)
            }
            else
            {
                holder.itemView.findViewById<TextView>(R.id.modifited_at).text = "Modified : " + time2.subSequence(11,16)
            }





        when(myList[position].status){
                "completed"-> holder.itemView.findViewById<androidx.cardview.widget.CardView>(R.id.priority_indicator).setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.green))
                "pending"-> holder.itemView.findViewById<androidx.cardview.widget.CardView>(R.id.priority_indicator).setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.Red))
             }

            holder.itemView.findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.row_background).setOnClickListener {
                val action = ListToDoFragmentDirections.actionListTaskFragmentToUpdateTaskFragment(myList[position])
                holder.itemView.findNavController().navigate(action)
            }
    }

    private fun getDateTime(creationTime: String?): CharSequence? {
        val d = sdf.parse(creationTime)
        val formattedTime = output.format(d)
        return formattedTime
    }

    override fun getItemCount(): Int {
        return myList.size
//        return dataset.size
    }

    fun setData(newList: ListToDoResponse)
    {
        val toDoDiffUtil = ToDoDiffUtil(myList,newList.tasks!!)
        val toDoDiffResult = DiffUtil.calculateDiff(toDoDiffUtil)
        myList = newList.tasks!!
        toDoDiffResult.dispatchUpdatesTo(this)
    }
}