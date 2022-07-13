package com.example.todotestapp.presentation.listToDo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todotestapp.R
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import com.example.todotestapp.data.repository.ToDoRepositoryImpl
import com.example.todotestapp.presentation.listToDo.viewmodel.ListViewModel
import com.example.todotestapp.presentation.listToDo.viewmodel.ListViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListToDoFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private val myAdapter by lazy { ListToDoAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view =  inflater.inflate(R.layout.fragment_list_todo, container, false)

        val repository : ToDoRepository = ToDoRepositoryImpl()
        val viewModelFactory  = ListViewModelFactory(repository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(ListViewModel::class.java)
        viewModel.getTask(1 )
        viewModel.mytasks.observe(viewLifecycleOwner, Observer{ response ->
                   if(response.isSuccessful){
                       response.body()?.let { myAdapter.setData(it) }
                   }
        })

//        val myTask = ToDo(1,11,"Play PUBG","Winner Winner Chicken Dinner")
//        viewModel.pushTask(myTask)
//        viewModel.myresponse.observe(viewLifecycleOwner, Observer{  response->
//            if(response.isSuccessful)
//            {
//                Log.d("Hey Hii",response.body().toString())
//            }
//        })
//        val myDataset = DataSource().loadTasks()
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewId)
        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())




        val addT = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)//adding task view


        addT.setOnClickListener {
            findNavController().navigate(R.id.action_listTaskFragment_to_updateTaskFragment,null)
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