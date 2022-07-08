package com.example.todotestapp.presentation.listtask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.findNavController
import com.example.todotestapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListTaskFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_list_task, container, false)
        val addT = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)//adding task view


        addT.setOnClickListener {
            findNavController().navigate(R.id.action_listTaskFragment_to_addTaskFragment)
        }

        val updT = view.findViewById<FloatingActionButton>(R.id.floatingActionButtonupdate)//adding task view


        updT.setOnClickListener {
            findNavController().navigate(R.id.action_listTaskFragment_to_updateTaskFragment)
        }




        return view
    }

}