package com.example.todotestapp.presentation.listToDo.ui

import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.os.Bundle
import android.util.Log
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
import com.example.todotestapp.databinding.FragmentListTodoBinding
import com.example.todotestapp.databinding.FragmentLoginBinding
import com.example.todotestapp.presentation.listToDo.viewmodel.ListViewModel
import com.example.todotestapp.presentation.listToDo.viewmodel.ListViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListToDoFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private  var binding: FragmentListTodoBinding ?= null
    private val myAdapter by lazy { ListToDoAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentListTodoBinding.inflate(inflater)
        val view = binding?.root


//        val repository : ToDoRepository = ToDoRepositoryImpl()
//        val viewModelFactory  = ListViewModelFactory(repository)
//        viewModel = ViewModelProvider(this,viewModelFactory).get(ListViewModel::class.java)
//        viewModel.getTask(1 )
//        viewModel.mytasks.observe(viewLifecycleOwner, Observer{ response ->
//                   if(response.isSuccessful){
//                       Log.d("Main",response.body().toString())
//                       response.body()?.let { myAdapter.setData(it) }
//                   }
//        })

//        val myTask = (1,11,"Play PUBG","Winner Winner Chicken Dinner")
//        viewModel.pushTask(myTask)
//        viewModel.myresponse.observe(viewLifecycleOwner, Observer{  response->
//            if(response.isSuccessful)
//            {
//                Log.d("Hey Hii",response.body().toString())
//            }
//        })
//        val myDataset = DataSource().loadTasks()
        val recyclerView = binding?.recyclerViewId
        recyclerView?.adapter = myAdapter
        recyclerView?.layoutManager = LinearLayoutManager(requireActivity())




        val addtodo = binding?.floatingActionButton

        addtodo?.setOnClickListener {
            findNavController().navigate(R.id.action_listTaskFragment_to_updateTaskFragment,null)
        }





        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }



}