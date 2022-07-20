package com.example.todotestapp.presentation.listToDo.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todotestapp.R
import com.example.todotestapp.data.repository.Constants.EMAIL
import com.example.todotestapp.data.repository.Constants.ID
import com.example.todotestapp.data.repository.Constants.SHARED_PREFERENCES
import com.example.todotestapp.data.repository.Constants.USER_NAME
import com.example.todotestapp.data.repository.ToDoRepositoryImpl
import com.example.todotestapp.databinding.FragmentListTodoBinding
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import com.example.todotestapp.presentation.MainActivity
import com.example.todotestapp.presentation.listToDo.viewmodel.ListViewModel
import com.example.todotestapp.presentation.listToDo.viewmodel.ListViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class ListToDoFragment : Fragment() {

    private  var binding: FragmentListTodoBinding ?= null
    private var listToDoUserEmail: String = ""
    private var listToDoUserId: Int = -1
    private val myAdapter by lazy { ListToDoAdapter() }
    @Inject
    lateinit var listViewModelFactory: ListViewModelFactory

    private val viewModel : ListViewModel by activityViewModels { listViewModelFactory }


    override fun onCreateView(


        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        binding = FragmentListTodoBinding.inflate(inflater)
        val view = binding?.root

        (activity as MainActivity).supportActionBar?.title = "ToDo's"

        loadData()
        viewModel.getTasks(listToDoUserId)
        viewModel.myToDoList.observe(viewLifecycleOwner, Observer{ response ->
                   if(response.isSuccessful){
                       Log.d("Main",response.body().toString())
                       response.body()?.let { myAdapter.setData(it) }
                   }
        })


        setupRecyclerView()

        val addtodo = binding?.floatingActionButton
        addtodo?.setOnClickListener {
            findNavController().navigate(R.id.action_listTaskFragment_to_updateTaskFragment,null)//args is null here beacuse while creating a add to do no need to pass the data
        }

        return view
    }


    private fun setupRecyclerView()
    {
        val recyclerView = binding?.recyclerViewId
        recyclerView?.adapter = myAdapter
        recyclerView?.layoutManager = LinearLayoutManager(requireActivity())//to make sure it is visible in the linear fashion

        SwipeToDelete(recyclerView)
    }

    private fun SwipeToDelete(recyclerView: RecyclerView?)
    {
        val swipeToDeleteCallback = object : SwipeToDelete(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemToDelete = myAdapter.myList[viewHolder.adapterPosition]
                viewModel.deleteToDo(itemToDelete.taskId)
                viewModel.getTasks(listToDoUserId)
                viewModel.myToDoList.observe(viewLifecycleOwner, Observer{ response ->
                    if(response.isSuccessful){
                        response.body()?.let { myAdapter.setData(it) }
                    }
                })
                Toast.makeText(context,  "ToDo Deleted Successfully", Toast.LENGTH_SHORT).show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.list_fragment_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.menu_completed -> {

                        viewModel.getTasksByStatus(listToDoUserId,"completed")
                        viewModel.myToDoListStatus.observe(viewLifecycleOwner, Observer{ response ->
                            if(response.isSuccessful){
                                response.body()?.let { myAdapter.setData(it) }
                            }
                        })
                        true
                    }
                    R.id.menu_pending -> {
                        viewModel.getTasksByStatus(listToDoUserId,"pending")
                        viewModel.myToDoListStatus.observe(viewLifecycleOwner, Observer{ response ->
                            if(response.isSuccessful){
                                response.body()?.let { myAdapter.setData(it) }
                            }
                        })
                        true
                    }
                    R.id.menu_all ->{
                        viewModel.getTasks(listToDoUserId)
                        viewModel.myToDoList.observe(viewLifecycleOwner, Observer{ response ->
                            if(response.isSuccessful){
                                response.body()?.let { myAdapter.setData(it) }
                            }
                        })
                        true
                    }
                    R.id.logout ->{
                        logoutUser()
                        getActivity()?.finishAffinity()
                        startActivity(Intent(context, MainActivity::class.java))

                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun logoutUser() {
        val sharedPreferences = activity?.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        var editor = sharedPreferences?.edit()
        editor?.clear()
        editor?.apply()
    }


    private fun loadData() {
        val sharedPreferences = activity?.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        sharedPreferences?.let {
            if (sharedPreferences.contains(EMAIL)) {
                listToDoUserEmail = sharedPreferences.getString(EMAIL, "") ?:""
                listToDoUserId = sharedPreferences.getInt(ID,-1)
            }
            val savedUsername = sharedPreferences.getString(USER_NAME,"")
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }



}