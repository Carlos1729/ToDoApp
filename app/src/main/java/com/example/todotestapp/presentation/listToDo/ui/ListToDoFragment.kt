package com.example.todotestapp.presentation.listToDo.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todotestapp.R
import com.example.todotestapp.data.db.BaseResponse
import com.example.todotestapp.data.db.ListToDoResponse
import com.example.todotestapp.data.db.LoginResponse
import com.example.todotestapp.data.db.StateData
import com.example.todotestapp.data.repository.Constants.ID
import com.example.todotestapp.data.repository.Constants.SHARED_PREFERENCES
import com.example.todotestapp.data.repository.ToDoRepositoryImpl
import com.example.todotestapp.databinding.FragmentListTodoBinding
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import com.example.todotestapp.presentation.MainActivity
import com.example.todotestapp.presentation.listToDo.viewmodel.ListViewModel
import com.example.todotestapp.presentation.listToDo.viewmodel.ListViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Response


class ListToDoFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private  var binding: FragmentListTodoBinding ?= null
    private var listToDoUserId: Int = -1
    private var timecount: Int = -1
    private var addToDoButton : FloatingActionButton? =null
    private val myAdapter by lazy { ListToDoAdapter() }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListTodoBinding.inflate(inflater)
        val view = binding?.root
        (activity as MainActivity).supportActionBar?.title = "ToDo's"
        setUpUI()
        setUpClickListeners()
        val repository: ToDoRepository = ToDoRepositoryImpl()
        val viewModelFactory = ListViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[ListViewModel::class.java]
        checkForUserLocalData()
        viewModel.getTasks(listToDoUserId)
        if(timecount == -1) {
            observeLiveData()
        }

        return view
    }

    private fun setUpClickListeners() {
        addToDoButton?.setOnClickListener {
            findNavController().navigate(
                R.id.action_listTaskFragment_to_updateTaskFragment,
                null
            )
        }
    }

    private fun setUpUI() {
        addToDoButton = binding?.floatingActionButton
        setupRecyclerView()
        setUpFilterMenu()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setUpFilterMenu() {
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

                        viewModel.getTasksByStatus(listToDoUserId, "completed")
                        timecount = 1
                        observeLiveDataStatus("completed")
                        true
                    }
                    R.id.menu_pending -> {
                        viewModel.getTasksByStatus(listToDoUserId, "pending")
                        timecount = 2
                        observeLiveDataStatus("pending")
                        true
                    }
                    R.id.menu_all -> {
                        viewModel.getTasks(listToDoUserId)
                        observeLiveData()
                        timecount = 3
                        true
                    }
                    R.id.logout -> {
                        logoutUser()
                        activity?.finish()
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

    private fun checkForUserLocalData() {
        val sharedPreferences = activity?.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        sharedPreferences?.let {
            if (sharedPreferences.contains(ID)) {
                listToDoUserId = sharedPreferences.getInt(ID,-1)
            }
        }
    }

    private fun swipeToDelete(recyclerView: RecyclerView?) {
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemToDelete = myAdapter.myList[viewHolder.adapterPosition]
                viewModel.deleteToDo(itemToDelete.taskId)
                observeDelete()

//                        viewModel.deleteToDoItemLiveData.observe(viewLifecycleOwner,Observer{
//            //when getting success
//            viewModel.getTasks(listToDoUserId)
//        })
                Toast.makeText(context, "ToDo Deleted Successfully", Toast.LENGTH_SHORT).show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun observeDelete() {
        viewModel.deleteToDoItemLiveData.observe(viewLifecycleOwner, Observer{
                handleResponseDelete(it)
        })
    }

    private fun handleResponseDelete(mydlr: StateData<Response<BaseResponse>>?) {

        when(mydlr?.status)
        {
            StateData.DataStatus.LOADING ->{
                binding?.listProgressBar?.visibility = View.VISIBLE
            }
            StateData.DataStatus.SUCCESS -> {
                binding?.listProgressBar?.visibility = View.GONE
                Log.v("TIMECOUNT",timecount.toString())
                if(timecount == -1 || timecount == 3)
                {
                    viewModel.getTasks(listToDoUserId)
                    observeLiveData()
                }
                if(timecount == 1)
                {
                    viewModel.getTasksByStatus(listToDoUserId,"completed")
                    observeLiveDataStatus("completed")
                }
                if(timecount == 2)
                {
                    viewModel.getTasksByStatus(listToDoUserId,"pending")
                    observeLiveDataStatus("pending")
                }

                }

        }
    }

    private fun observeLiveData() {
        viewModel.myToDoList.observe(viewLifecycleOwner, Observer{
            handleResponse(it)
        })

//        viewModel.deleteToDoItemLiveData.observe(viewLifecycleOwner,Observer{
//            //when getting success
//            viewModel.getTasks(listToDoUserId)
//        })
    }

    private fun observeLiveDataStatus(status:String){
        viewModel.myToDoListStatus.observe(viewLifecycleOwner,Observer{
            handleResponseStatus(it)
        })

//        viewModel.deleteToDoItemLiveData.observe(viewLifecycleOwner,Observer{
//            //when getting success
//            viewModel.getTasksByStatus(listToDoUserId, status)
//        })
    }

    private fun handleResponseStatus(mylbs: StateData<Response<ListToDoResponse>>?) {

        when(mylbs?.status)
        {
            StateData.DataStatus.LOADING ->{
                binding?.listProgressBar?.visibility = View.VISIBLE
            }
            StateData.DataStatus.SUCCESS -> {
                binding?.listProgressBar?.visibility = View.GONE
                if (mylbs.data?.body() != null) {
                    if ((mylbs.data?.body()!!.tasks?.size) == 0)
                    {
                        binding?.loginNoResultsTv?.visibility = View.VISIBLE
                    }
                    else {
                        binding?.loginNoResultsTv?.visibility = View.GONE
                        mylbs.data?.body().let { myAdapter.setData(mylbs?.data?.body()!!).let { it } }
                    }
                }

            }
        }
    }


    private fun handleResponse(mlistr: StateData<Response<ListToDoResponse>>?) {
        when(mlistr?.status)
        {
            StateData.DataStatus.LOADING ->{
                binding?.listProgressBar?.visibility = View.VISIBLE
                binding?.loginNoResultsTv?.visibility = View.GONE
            }
            StateData.DataStatus.SUCCESS -> {
                binding?.listProgressBar?.visibility = View.GONE
                if (mlistr.data?.body() != null) {
                    if ((mlistr.data?.body()!!.tasks?.size) == 0)
                    {
                        binding?.loginNoResultsTv?.visibility = View.VISIBLE
                        //Show Empty Icon
                    }
                    else {
                        binding?.loginNoResultsTv?.visibility = View.GONE
                        mlistr.data?.body().let { myAdapter.setData(mlistr?.data?.body()!!).let { it } }
                    }
                }
            }
        }

    }


    private fun setupRecyclerView() {
        val recyclerView = binding?.recyclerViewId
        recyclerView?.adapter = myAdapter
        recyclerView?.layoutManager = LinearLayoutManager(requireActivity())
        swipeToDelete(recyclerView)
    }



}