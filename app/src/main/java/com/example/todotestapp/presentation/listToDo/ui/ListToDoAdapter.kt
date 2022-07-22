package com.example.todotestapp.presentation.listToDo.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
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

    var myList = emptyList<BaseListToDoResponse>()
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

            holder.binding.titleTxt.text = myList[position].title
            holder.binding.descriptionTxt.text = myList[position].description

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
                val thtime = timecheck(time1.subSequence(11, 16).toString())

                holder.binding.createdAt.text = "Created : " + thtime
            }

            val diffa: Long = presDate.time - newDate.time
            val secondsa = diffa / 1000

            if(secondsa > 86400)
            {
                holder.binding.modifitedAt.text = "Updated : " + time2.subSequence(0,10)
            }
            else
            {
                val thtime = timecheck(time2.subSequence(11, 16).toString())
                holder.binding.modifitedAt.text = "Updated : " + thtime
            }

        when(myList[position].status){
                "completed"-> holder.binding.priorityIndicator.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.green))
                "pending"-> holder.binding.priorityIndicator.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.Red))
             }

            holder.binding.rowBackground.setOnClickListener{
                val action = ListToDoFragmentDirections.actionListTaskFragmentToUpdateTaskFragment(myList[position])
                holder.itemView.findNavController().navigate(action)
            }

    }

    private fun timecheck(subSequence: String): String {

        Log.v("Madhukartestfinal",subSequence)
        var timth : String = ""
        val h1 = subSequence[0] - '0'
        val h2 = subSequence[1] - '0'
        Log.v("Madhukartestfinal",subSequence)
        Log.v("Madhukartestfinal",h2.toString())

        var hh = h1 * 10 + h2
        Log.v("Madhukartestfinal",hh.toString())


        val Meridien: String

        if(hh < 12)
        {
            Meridien = "AM"
        }
        else
        {
            Meridien = "PM"
        }

            hh %= 12

            timth += hh.toString()
            // Printing minutes and seconds
            for (i in 2..4) {
                timth+=subSequence[i]
            }


        timth = timth + " " + Meridien

        return timth
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

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun setData(newList: ListToDoResponse) {

        val toDoDiffUtil = ToDoDiffUtil(myList,newList.tasks!!)
        val toDoDiffResult = DiffUtil.calculateDiff(toDoDiffUtil)
        myList = newList.tasks
        toDoDiffResult.dispatchUpdatesTo(this)
    }
}