package com.example.todotestapp.presentation.listToDo.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todotestapp.R
import com.example.todotestapp.data.db.BaseResponse
import com.example.todotestapp.data.db.ListToDoPaginationResponse
import com.example.todotestapp.data.db.ListToDoResponse
import com.example.todotestapp.data.db.StateData
import com.example.todotestapp.data.repository.Constants.ID
import com.example.todotestapp.data.repository.Constants.ROLE
import com.example.todotestapp.data.repository.Constants.SHARED_PREFERENCES
import com.example.todotestapp.databinding.FragmentListTodoBinding
import com.example.todotestapp.presentation.MainActivity
import com.example.todotestapp.presentation.listToDo.viewmodel.ListViewModel
import com.example.todotestapp.presentation.listToDo.viewmodel.ListViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.android.support.DaggerFragment
import javax.inject.Inject

import retrofit2.Response

class ListToDoFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ListViewModelFactory

    @Inject
    lateinit var sharedPreferences : SharedPreferences

    private lateinit var viewModel: ListViewModel
    private  var binding: FragmentListTodoBinding ?= null
    private var listToDoUserId: Int = -1
    private var listToDoUserRole: String = ""
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
        viewModel = ViewModelProvider(this, viewModelFactory)[ListViewModel::class.java]
        checkForUserLocalData()
        observeLiveData()
        viewModel.getAllTasksPagination(listToDoUserRole,listToDoUserId,null,null,null)
//        viewModel.getAllTasks(listToDoUserId)
        return view
    }

    private fun observeLiveData() {
//        observeLiveDataForComletedList()
//        observeLiveDataByStatus()
        observeLiveDataPaginationList()
        observeDelete()
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
                        viewModel.getAllTasksPagination(listToDoUserRole,listToDoUserId,1,"completed",null)
//                        viewModel.getTasksByStatus(listToDoUserId, "completed")
                        timecount = 1
                        true
                    }
                    R.id.menu_pending -> {
                        viewModel.getAllTasksPagination(listToDoUserRole,listToDoUserId,1,"pending",null)
//                        viewModel.getTasksByStatus(listToDoUserId, "pending")
                        timecount = 2
                        true
                    }
                    R.id.menu_all -> {
                        viewModel.getAllTasksPagination(listToDoUserRole,listToDoUserId,1,null,null)
//                        viewModel.getAllTasks(listToDoUserId)
                        timecount = 3
                        true
                    }
                    R.id.high_priority -> {
                        viewModel.getAllTasksPagination(listToDoUserRole,listToDoUserId,1,null,"high")
                        true
                    }
                    R.id.medium_priority -> {
                        viewModel.getAllTasksPagination(listToDoUserRole,listToDoUserId,1,null,"medium")
                        true
                    }
                    R.id.low_priority -> {
                        viewModel.getAllTasksPagination(listToDoUserRole,listToDoUserId,1,null,"low")
                        true
                    }
                    R.id.menu_sort_by_priority -> {
                        findNavController().navigate(R.id.action_listTaskFragment_to_filterFragment)
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
        sharedPreferences.let {
            if (sharedPreferences.contains(ID)) {
                listToDoUserId = sharedPreferences.getInt(ID,-1)
                listToDoUserRole = sharedPreferences.getString(ROLE,"").toString()
            }
        }
    }

    private fun swipeToDelete(recyclerView: RecyclerView?) {
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemToDelete = myAdapter.myList[viewHolder.adapterPosition]
                viewModel.deleteToDo(itemToDelete.taskId)
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
        when (mydlr?.status) {
            StateData.DataStatus.LOADING -> {
                binding?.listProgressBar?.visibility = View.VISIBLE
            }
            StateData.DataStatus.SUCCESS -> {
                binding?.listProgressBar?.visibility = View.GONE
                Log.v("TIMECOUNT", timecount.toString())
                if (timecount == -1 || timecount == 3) {
                    viewModel.getAllTasksPagination(listToDoUserRole,listToDoUserId,1,null,null)
//                    viewModel.getAllTasks(listToDoUserId)
                }
                if (timecount == 1) {
                    viewModel.getTasksByStatus(listToDoUserId, "completed")
                }
                if (timecount == 2) {
                    viewModel.getTasksByStatus(listToDoUserId, "pending")
                }
            }

            else -> {}
        }
    }

//    private fun observeLiveDataForComletedList() {
//        viewModel.myToDoAllList.observe(viewLifecycleOwner, Observer{
//            handleResponseForAllList(it)
//        })
//    }

    private fun observeLiveDataPaginationList(){
        viewModel.myToDoAllPaginationList.observe(viewLifecycleOwner, Observer{
            handleResponseForAllPaginationList(it)
        })
    }

//    private fun handleResponseForAllList(mlistr: StateData<Response<ListToDoResponse>>?) {
//        when (mlistr?.status) {
//            StateData.DataStatus.LOADING -> {
//                binding?.listProgressBar?.visibility = View.VISIBLE
//                binding?.loginNoResultsTv?.visibility = View.GONE
//                binding?.emptyIcon?.visibility = View.GONE
//            }
//            StateData.DataStatus.SUCCESS -> {
//                binding?.listProgressBar?.visibility = View.GONE
//                if (mlistr.data?.body() != null) {
//                    if ((mlistr.data?.body()!!.tasks?.size) == 0) {
//                        binding?.loginNoResultsTv?.visibility = View.VISIBLE
//                        binding?.emptyIcon?.visibility = View.VISIBLE
//                        //Show Empty Icon
//                    } else {
//                        binding?.loginNoResultsTv?.visibility = View.GONE
//                        binding?.emptyIcon?.visibility = View.GONE
//                        mlistr.data?.body()
//                            .let { myAdapter.setData(mlistr?.data?.body()!!).let { it } }
//                    }
//                }
//            }
//            else -> {}
//        }
//    }

    private fun handleResponseForAllPaginationList(allListToDoPaginationResponse: StateData<Response<ListToDoPaginationResponse>>?) {
            when(allListToDoPaginationResponse?.status) {
                StateData.DataStatus.LOADING -> {
                    binding?.listProgressBar?.visibility = View.VISIBLE
                    binding?.loginNoResultsTv?.visibility = View.GONE
                    binding?.emptyIcon?.visibility = View.GONE
                }
                StateData.DataStatus.SUCCESS -> {
                    binding?.listProgressBar?.visibility = View.GONE
                    if(allListToDoPaginationResponse.data?.body() != null)
                    {
                        if((allListToDoPaginationResponse.data?.body()!!.tasks?.size) == 0)
                        {
                            binding?.loginNoResultsTv?.visibility = View.VISIBLE
                            binding?.emptyIcon?.visibility = View.VISIBLE
                        } else{
                            binding?.loginNoResultsTv?.visibility = View.GONE
                            binding?.emptyIcon?.visibility = View.GONE
                            allListToDoPaginationResponse.data?.body().let {
                                myAdapter.setData(allListToDoPaginationResponse.data?.body()!!).let { it }
                            }
                        }
                    }
                }
            }
    }

//    private fun observeLiveDataByStatus(){
//        viewModel.myToDoListByStatus.observe(viewLifecycleOwner,Observer{
//            handleResponseStatus(it)
//        })
//    }

//    private fun handleResponseStatus(mylbs: StateData<Response<ListToDoResponse>>?) {
//        when (mylbs?.status) {
//            StateData.DataStatus.LOADING -> {
//                binding?.listProgressBar?.visibility = View.VISIBLE
//            }
//            StateData.DataStatus.SUCCESS -> {
//                binding?.listProgressBar?.visibility = View.GONE
//                if (mylbs.data?.body() != null) {
//                    if ((mylbs.data?.body()!!.tasks?.size) == 0) {
//                        binding?.loginNoResultsTv?.visibility = View.VISIBLE
//                        binding?.emptyIcon?.visibility = View.VISIBLE
//                        mylbs.data?.body()
//                            .let { myAdapter.setData(mylbs?.data?.body()!!).let { it } }
//
//                    } else {
//                        binding?.loginNoResultsTv?.visibility = View.GONE
//                        binding?.emptyIcon?.visibility = View.GONE
//                        mylbs.data?.body()
//                            .let { myAdapter.setData(mylbs?.data?.body()!!).let { it } }
//                    }
//                }
//
//            }
//            else -> {}
//        }
//    }





    private fun setupRecyclerView() {
        val recyclerView = binding?.recyclerViewId
        recyclerView?.adapter = myAdapter
        recyclerView?.layoutManager = LinearLayoutManager(requireActivity())
        swipeToDelete(recyclerView)
    }



}