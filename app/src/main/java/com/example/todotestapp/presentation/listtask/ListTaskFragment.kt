package com.example.todotestapp.presentation.listtask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todotestapp.R
import com.example.todotestapp.data.DataSource
import com.example.todotestapp.data.network.repository.Repository
import com.example.todotestapp.model.ListViewModel
import com.example.todotestapp.model.ListViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListTaskFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private val myAdapter by lazy { ListTaskAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view =  inflater.inflate(R.layout.fragment_list_task, container, false)

        val repository = Repository()
        val viewModelFactory  = ListViewModelFactory(repository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(ListViewModel::class.java)
        viewModel.getTask(1 )
        viewModel.mytasks.observe(viewLifecycleOwner, Observer{ response ->
                   if(response.isSuccessful){
                       response.body()?.let { myAdapter.setData(it) }
                   }
        })
//        val myDataset = DataSource().loadTasks()
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewId)
        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        val addT = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)//adding task view


        addT.setOnClickListener {
            findNavController().navigate(R.id.action_listTaskFragment_to_addTaskFragment)
        }

//        val updT = view.findViewById<FloatingActionButton>(R.id.floatingActionButtonupdate)//adding task view
//
//
//        updT.setOnClickListener {
//            findNavController().navigate(R.id.action_listTaskFragment_to_updateTaskFragment)
//        }




        return view
    }



}