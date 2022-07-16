package com.example.todotestapp.presentation.listToDo.ui

import android.content.Context
import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
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
import com.example.todotestapp.presentation.SharedViewModel
import com.example.todotestapp.presentation.listToDo.viewmodel.ListViewModel
import com.example.todotestapp.presentation.listToDo.viewmodel.ListViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListToDoFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private  var binding: FragmentListTodoBinding ?= null
    lateinit var listToDoUserEmail: String
    private val myAdapter by lazy { ListToDoAdapter() }

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(


        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        binding = FragmentListTodoBinding.inflate(inflater)
        val view = binding?.root




        val repository : ToDoRepository = ToDoRepositoryImpl()
        val viewModelFactory  = ListViewModelFactory(repository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(ListViewModel::class.java)
        loadData()
        viewModel.getTasks(listToDoUserEmail)
        Log.v("ThisisTest",listToDoUserEmail)
        viewModel.myToDoList.observe(viewLifecycleOwner, Observer{ response ->
                   if(response.isSuccessful){
                       Log.d("Main",response.body().toString())
                       response.body()?.let { myAdapter.setData(it) }
                   }
        })

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

    private fun loadData() {
        val sharedPreferences = activity!!.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedEmail = sharedPreferences.getString("EMAILID",null)
        val savedUsername = sharedPreferences.getString("USERNAME",null)
        val savedId = sharedPreferences.getInt("ID",-1)
        if (savedEmail != null) {
            listToDoUserEmail = savedEmail
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }



}