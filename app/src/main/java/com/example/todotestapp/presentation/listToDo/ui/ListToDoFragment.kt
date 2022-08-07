package com.example.todotestapp.presentation.listToDo.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.forEach
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todotestapp.R
import com.example.todotestapp.data.db.*
import com.example.todotestapp.data.repository.Constants.ID
import com.example.todotestapp.data.repository.Constants.ROLE
import com.example.todotestapp.data.repository.Constants.SHARED_PREFERENCES
import com.example.todotestapp.data.repository.GlobalVariable
import com.example.todotestapp.data.repository.GlobalVariable.hashMapOrder
import com.example.todotestapp.data.repository.GlobalVariable.hashMapPriority
import com.example.todotestapp.data.repository.GlobalVariable.hashMapStatus
import com.example.todotestapp.databinding.FragmentListTodoBinding
import com.example.todotestapp.presentation.MainActivity
import com.example.todotestapp.presentation.listToDo.viewmodel.ListViewModel
import com.example.todotestapp.presentation.listToDo.viewmodel.ListViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.android.support.DaggerFragment
import retrofit2.Response
import javax.inject.Inject


class ListToDoFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ListViewModelFactory

    @Inject
    lateinit var sharedPreferences : SharedPreferences

    private val viewModel: ListViewModel by activityViewModels {
        viewModelFactory
    }
    private  var binding: FragmentListTodoBinding ?= null
    private var listToDoUserId: Int = -1
    private var listToDoUserRole: String = ""
    private var timecount: Int = -1
    private var addToDoButton : FloatingActionButton? =null
    private val myAdapter by lazy { ListToDoAdapter() }

    private var loading = true
    private var firstVisibleItemPosition: Int = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0

    private var pageNumber = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListTodoBinding.inflate(inflater)
        val view = binding?.root
        (activity as MainActivity).supportActionBar?.title = "ToDo's"
        setUpUI()
        binding?.bottomNavigation?.menu?.forEach { it.isEnabled = true}
        setUpClickListeners()
        checkForUserLocalData()
        observeLiveData()
        GlobalVariable.INACTIVEFLAG = false
//        viewModel.getAllTasksPagination(listToDoUserRole,listToDoUserId,1,null,null,null,null)
//        order = ListToDoFragmentArgs.fromBundle(requireArguments()).sortBySelected.toString()
        val order = viewModel.sortByStored.value
        val selectedStatus = viewModel.statusStored.value
        val selectedPriority = viewModel.priorityStored.value
//        Toast.makeText(context, order.toString(), Toast.LENGTH_SHORT).show()
//        Toast.makeText(context, selectedStatus.toString(), Toast.LENGTH_SHORT).show()
//        Toast.makeText(context, selectedPriority.toString(), Toast.LENGTH_SHORT).show()
//        if(selectedStatus != null || selectedPriority != null)
//        {
////            val unwrappedDrawable = AppCompatResources.getDrawable(
////                context!!, R.drawable.ic_icon_filter
////            )
////            val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
////            DrawableCompat.setTint(wrappedDrawable, R.color.purple_400)
////            DrawableCompat.setTint(AppCompatResources.getDrawable(context!!,R.drawable.ic_icon_filter)!!,R.color.purple_400)
//        }
        if(order == null || order == 0)
        {
//            Toast.makeText(context, "In This", Toast.LENGTH_SHORT).show()//change this after getting status and priority thing
            binding?.sortSelectedCard?.visibility = View.GONE
            if((selectedPriority == null && selectedStatus == null) || (selectedPriority == 0 && selectedStatus == 0) || (selectedPriority == null && selectedStatus == 0) || (selectedPriority == 0 && selectedStatus == null))
            {
//                Log.v("test123", selectedPriority.toString())
//                Log.v("test123", selectedStatus.toString())
                binding?.filtersSelectedCard?.visibility = View.GONE
            }
            else
            {
                binding?.filtersSelectedCard?.visibility = View.VISIBLE
            }
            if(GlobalVariable.ADMINOWNTASKS)
            {
                viewModel.getAllTasksPagination("author",listToDoUserId,1, hashMapPriority[selectedPriority],null,null)
            }
            else {
                viewModel.getAllTasksPagination(listToDoUserRole,listToDoUserId,1, hashMapPriority[selectedPriority],null,null)
            }
        }
        else
        {
//            Toast.makeText(context, "In That", Toast.LENGTH_SHORT).show()//change this after getting status and priority thing
//            binding?.bottomNavigation?.menu?.getItem(0)?.isChecked = true
            binding?.sortSelectedCard?.visibility = View.VISIBLE
            if((selectedPriority == null && selectedStatus == null) || (selectedPriority == 0 && selectedStatus == 0) || (selectedPriority == null && selectedStatus == 0) || (selectedPriority == 0 && selectedStatus == null))
            {
//                Log.v("test123", selectedPriority.toString())
//                Log.v("test123", selectedStatus.toString())
                binding?.filtersSelectedCard?.visibility = View.GONE
            }
            else
            {
                binding?.filtersSelectedCard?.visibility = View.VISIBLE
            }
            if(GlobalVariable.ADMINOWNTASKS)
            {
                viewModel.getAllTasksPagination("author",listToDoUserId,1,hashMapPriority[selectedPriority],"priority",hashMapOrder[order])
            }
            else{
                viewModel.getAllTasksPagination(listToDoUserRole,listToDoUserId,1,hashMapPriority[selectedPriority],"priority",hashMapOrder[order])
            }
        }

        return view
    }

    private fun observeLiveData() {
        observeLiveDataPaginationList()
        observeUpdateToDoInList()
    }

    private fun setUpClickListeners() {
        addToDoButton?.setOnClickListener {
            findNavController().navigate(
                R.id.action_listTaskFragment_to_updateTaskFragment,
                null
            )
        }
        binding?.bottomNavigation?.menu?.getItem(0)?.isCheckable = false
        binding?.bottomNavigation?.menu?.getItem(1)?.isCheckable = false
        binding?.bottomNavigation?.setOnItemSelectedListener{item ->
            when(item.itemId) {
                R.id.page_1 -> {
//                    Toast.makeText(context, "Filter Selected", Toast.LENGTH_SHORT).show()
                    // Respond to navigation item 1 click
//                    binding?.bottomNavigation?.menu?.getItem(1)?.isCheckable = false
//                    binding?.bottomNavigation?.menu?.getItem(0)?.isCheckable = true
                    findNavController().navigate(R.id.action_listTaskFragment_to_filterFragment)
                    true
                }
                R.id.page_2 -> {
//                    Toast.makeText(context, "Sort Selected", Toast.LENGTH_SHORT).show()
                    // Respond to navigation item 2 click
//                    binding?.bottomNavigation?.menu?.getItem(0)?.isCheckable = false
//                    binding?.bottomNavigation?.menu?.getItem(1)?.isCheckable = true
                    findNavController().navigate(R.id.action_listTaskFragment_to_sortFragment)
                    true
                }
                else -> false
            }
        }

    }

    private fun setUpUI() {
        addToDoButton = binding?.floatingActionButton
        setupRecyclerView()
        setUpFilterMenu()
    }

    private fun setUpFilterMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.list_fragment_menu, menu)
            }

            override fun onPrepareMenu(menu: Menu) {
                val register = menu.findItem(R.id.menu_grant_permission)
                register.isVisible = (listToDoUserRole == "admin")
                val viewAdminOwnTasks = menu.findItem(R.id.menu_view_your_tasks)
                viewAdminOwnTasks.isVisible = (listToDoUserRole == "admin")
                val viewAdminDeletedTasks = menu.findItem(R.id.menu_view_your_deleted_tasks)
                viewAdminDeletedTasks.isVisible = (listToDoUserRole == "admin")
            }


            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.menu_all -> {
                        GlobalVariable.INACTIVEFLAG = false
                        GlobalVariable.ADMINOWNTASKS = false
                        binding?.bottomNavigation?.menu?.forEach { it.isEnabled = true}
                        myAdapter.clearData()
                        pageNumber = 0
                        viewModel.status = null
                        viewModel.getAllTasksPagination(listToDoUserRole,listToDoUserId,1,null,null,null)
                        viewModel.setStatusFromFrag(0)
                        viewModel.setPriorityFromFrag(0)
                        viewModel.setSortByStoredFromFrag(0)
                        timecount = 3
                        true
                    }
                    R.id.menu_view_your_tasks -> {
                        GlobalVariable.INACTIVEFLAG = false
                        binding?.bottomNavigation?.menu?.forEach { it.isEnabled = true }
                        GlobalVariable.ADMINOWNTASKS = true
                        myAdapter.clearData()
                        pageNumber = 0
                        viewModel.status = null
                        viewModel.getAllTasksPagination(
                            "author",
                            listToDoUserId,
                            1,
                            null,
                            null,
                            null
                        )
                        true
                    }
                    R.id.deleted_tasks -> {
                        GlobalVariable.INACTIVEFLAG = true
                        binding?.bottomNavigation?.menu?.forEach { it.isEnabled = false }
                        myAdapter.clearData()
                        pageNumber = 0
                        viewModel.status = "inactive"
                        viewModel.getAllTasksPagination(listToDoUserRole,listToDoUserId,1,null,null,null)
                        true
                    }
                    R.id.menu_view_your_deleted_tasks -> {
                        GlobalVariable.INACTIVEFLAG = true
                        binding?.bottomNavigation?.menu?.forEach { it.isEnabled = false }
                        myAdapter.clearData()
                        viewModel.status = "inactive"
                        viewModel.getAllTasksPagination("author",listToDoUserId,1,null,null,null)
                        true
                    }
                    R.id.menu_grant_permission -> {
                        GlobalVariable.INACTIVEFLAG = false
                        GlobalVariable.ADMINOWNTASKS = false
                        binding?.bottomNavigation?.menu?.forEach { it.isEnabled = true }
                        findNavController().navigate(R.id.action_listTaskFragment_to_grantPermissionsFragment)
                        true
                    }
                    R.id.logout -> {
                        binding?.bottomNavigation?.menu?.forEach { it.isEnabled = true}
                        GlobalVariable.INACTIVEFLAG = false
                        GlobalVariable.ADMINOWNTASKS = false
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
                val updatedTitle: String = itemToDelete.title.toString()
                val updatedDescription: String = itemToDelete.description.toString()
                val updatedStatus: String = "inactive"
                val updatedPriority: String = itemToDelete.priority.toString()
                val presentUpdateToDoRequest = UpdateToDoRequest(
                    updatedTitle,
                    updatedDescription,
                    updatedStatus,
                    updatedPriority
                )
                viewModel.updateToDoInList(itemToDelete.taskId!!, presentUpdateToDoRequest)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }






    private fun observeUpdateToDoInList() {
        viewModel.myUpdateToDoInListResponse.observe(viewLifecycleOwner){
            handleResponseUpdateInList(it)
        }
    }

    private fun handleResponseUpdateInList(updateInListResponse: StateData<Response<UpdateToDoResponse>>?) {

        when (updateInListResponse?.status) {
            StateData.DataStatus.LOADING -> {
                binding?.listProgressBar?.visibility = View.VISIBLE
            }
            StateData.DataStatus.SUCCESS -> {
                binding?.listProgressBar?.visibility = View.GONE
                if (updateInListResponse.data?.body() != null) {
                    Toast.makeText(context, getString(R.string.mtds), Toast.LENGTH_SHORT).show()
                    val order = viewModel.sortByStored.value
                    val selectedStatus = viewModel.statusStored.value
                    val selectedPriority = viewModel.priorityStored.value
                    if(order != null || selectedStatus != null || selectedPriority != null)
                    {
                        timecount = 0
                    }
                    Log.v("checkingValues",timecount.toString())
                    if (timecount == -1 || timecount == 3) {
                        viewModel.getAllTasksPagination(listToDoUserRole,listToDoUserId,1,null,null,null)
                    }
                    else
                    {
                        if(order == null || order == 0)
                        {
//            Toast.makeText(context, "In This", Toast.LENGTH_SHORT).show()//change this after getting status and priority thing
                            binding?.sortSelectedCard?.visibility = View.GONE
                            if((selectedPriority == null && selectedStatus == null) || (selectedPriority == 0 && selectedStatus == 0) || (selectedPriority == null && selectedStatus == 0) || (selectedPriority == 0 && selectedStatus == null))
                            {
//                Log.v("test123", selectedPriority.toString())
//                Log.v("test123", selectedStatus.toString())
                                binding?.filtersSelectedCard?.visibility = View.GONE
                            }
                            else
                            {
                                binding?.filtersSelectedCard?.visibility = View.VISIBLE
                            }
                            if(GlobalVariable.ADMINOWNTASKS)
                            {
                                viewModel.getAllTasksPagination("author",listToDoUserId,1, hashMapPriority[selectedPriority],null,null)
                            }
                            else {
                                viewModel.getAllTasksPagination(listToDoUserRole,listToDoUserId,1, hashMapPriority[selectedPriority],null,null)
                            }
                        }
                        else
                        {
//            Toast.makeText(context, "In That", Toast.LENGTH_SHORT).show()//change this after getting status and priority thing
//            binding?.bottomNavigation?.menu?.getItem(0)?.isChecked = true
                            binding?.sortSelectedCard?.visibility = View.VISIBLE
                            if((selectedPriority == null && selectedStatus == null) || (selectedPriority == 0 && selectedStatus == 0) || (selectedPriority == null && selectedStatus == 0) || (selectedPriority == 0 && selectedStatus == null))
                            {
//                Log.v("test123", selectedPriority.toString())
//                Log.v("test123", selectedStatus.toString())
                                binding?.filtersSelectedCard?.visibility = View.GONE
                            }
                            else
                            {
                                binding?.filtersSelectedCard?.visibility = View.VISIBLE
                            }
                            if(GlobalVariable.ADMINOWNTASKS)
                            {
                                viewModel.getAllTasksPagination("author",listToDoUserId,1,hashMapPriority[selectedPriority],"priority",hashMapOrder[order])
                            }
                            else{
                                viewModel.getAllTasksPagination(listToDoUserRole,listToDoUserId,1,hashMapPriority[selectedPriority],"priority",hashMapOrder[order])
                            }
                        }
                    }
                }
                else if (updateInListResponse.data?.code() == 400) {
                    Toast.makeText(
                        context,
                        "Something Went Wrong Please Try Again",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            else -> {}
        }
    }

    private fun observeLiveDataPaginationList(){
        viewModel.myToDoAllPaginationList.observe(viewLifecycleOwner, Observer{
           totalItemCount = it?.data?.body()?.totalPage ?: 0
            handleResponseForAllPaginationList(it)
        })
    }


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
                            allListToDoPaginationResponse.data?.body().let {
                                myAdapter.setData(allListToDoPaginationResponse.data?.body()!!).let { it }
                            }
                        } else{
                            binding?.loginNoResultsTv?.visibility = View.GONE
                            binding?.emptyIcon?.visibility = View.GONE
                            allListToDoPaginationResponse.data?.body().let {
                                myAdapter.setData(allListToDoPaginationResponse.data?.body()!!).let { it }
                            }
                        }
                    }
                }
                else -> {}
            }
        Handler().postDelayed({
            loading = true
        },1000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }




    private fun setupRecyclerView() {
        val recyclerView = binding?.recyclerViewId
        recyclerView?.adapter = myAdapter
        recyclerView?.layoutManager = LinearLayoutManager(requireActivity())
        swipeToDelete(recyclerView)

        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) { //check for scroll down
                    totalItemCount = recyclerView.layoutManager?.itemCount ?:0
                    visibleItemCount = recyclerView.layoutManager?.childCount ?:0
                    firstVisibleItemPosition =
                        (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (loading) {
                        if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                            loading = false
                            viewModel.getAllTasksPagination(
                                listToDoUserRole,
                                listToDoUserId,
                                pageNumber++,
                                hashMapPriority[viewModel.priorityStored.value],
                                "priority",
                                hashMapOrder[viewModel.sortByStored.value]
                            )
                        }
                    }
                }
            }
        })
    }



}