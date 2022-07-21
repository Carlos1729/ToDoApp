package com.example.todotestapp.presentation.updateToDo.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todotestapp.R
import com.example.todotestapp.data.db.*
import com.example.todotestapp.data.repository.Constants
import com.example.todotestapp.data.repository.ToDoRepositoryImpl
import com.example.todotestapp.databinding.FragmentUpdateTodoBinding
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import com.example.todotestapp.presentation.MainActivity
import com.example.todotestapp.presentation.updateToDo.viewmodel.UpdateToDoViewModel
import com.example.todotestapp.presentation.updateToDo.viewmodel.UpdateToDoViewModelFactory
import retrofit2.Response


class UpdateToDoFragment : Fragment() {


    private val args by navArgs<UpdateToDoFragmentArgs>()
    private var todoItem: BaseListToDoResponse? = null
    private var title: String? = null
    private var description: String? = null
    private var titleTV: TextView? = null
    private var addToDoFlag:  Boolean = false
    private  var binding: FragmentUpdateTodoBinding ?= null
    private var descriptionTV: TextView? = null
    private var statusspin: Spinner? = null
    private var todostatus: String? = null
    private var idOfTask: Int? = null
    private var button: Button? = null
    private var deleteButton: Button? = null
    private lateinit var addToDoUserEmail :String
    private lateinit var viewModel: UpdateToDoViewModel




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateTodoBinding.inflate(inflater)
        binding?.statusSpinner?.visibility = View.GONE
        binding?.deleteTaskButton?.visibility = View.GONE
        binding?.statusHeading?.visibility = View.GONE


        val view = binding?.root

        if (view != null) {
            initWidgets(view)
        }
        fetchArguments()
        setUpUI()


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val repository : ToDoRepository = ToDoRepositoryImpl()

        button?.setOnClickListener {
            checkInput(view)

            val viewModelFactory  = UpdateToDoViewModelFactory(repository)
            viewModel = ViewModelProvider(this, viewModelFactory)[UpdateToDoViewModel::class.java]
            if(!addToDoFlag) {
                var updatedTitle: String = titleTV?.text.toString()
                var updatedDescription: String = descriptionTV?.text.toString()
                var updatedStatus: String = statusspin?.selectedItem.toString()
                var presentUpdateToDoRequest = UpdateToDoRequest(updatedTitle,updatedDescription,updatedStatus)
                viewModel.updateToDo(idOfTask,presentUpdateToDoRequest)
                observeUpdateToDoViewModel()
            }
            else
            {
                var addTitle: String = titleTV?.text.toString()
                var addDescription: String = descriptionTV?.text.toString()
                var addStatus : String = "pending"
                loadData()
                var addUserEmail: String = addToDoUserEmail
                var presentAddToDoRequest = AddToDoRequest(addUserEmail,addTitle,addDescription,addStatus)
                viewModel.addToDo(presentAddToDoRequest)
                observeAddToDoViewModel()
            }
        }

        deleteButton?.setOnClickListener {
            val viewModelFactory  = UpdateToDoViewModelFactory(repository)
            viewModel = ViewModelProvider(this, viewModelFactory)[UpdateToDoViewModel::class.java]
            viewModel.deleteToDo(idOfTask)
            observeDeleteToDoViewModel()
        }

    }

    private fun observeDeleteToDoViewModel() {
        viewModel.myDeleteToDoResponse.observe(viewLifecycleOwner) {

            handleResponseDelete(it)
        }
    }

    private fun handleResponseDelete(mydtr: StateData<Response<BaseResponse>>?) {
           when(mydtr?.status)
           {
               StateData.DataStatus.LOADING ->{
                   //Add Loading Progress bar
//                binding?.loginProgressBar?.visibility = View.VISIBLE
               }
               StateData.DataStatus.SUCCESS -> {
//                binding?.loginProgressBar?.visibility = View.GONE


                   if(mydtr.data?.body() != null)
                   {
                                Toast.makeText(context, getString(R.string.mtds), Toast.LENGTH_SHORT)
                                    .show()             //dismiss sheet and load todolist fragment
                                findNavController().navigate(R.id.action_updateTaskFragment_to_listTaskFragment)
                   }
                   else if(mydtr.data?.code() == 400)
                   {
                       Toast.makeText(
                           context,
                           getString(R.string.swrpta),
                           Toast.LENGTH_SHORT
                       ).show()
                   }
               }
           }
    }

    private fun observeUpdateToDoViewModel() {

        viewModel.myUpdateToDoResponse.observe(viewLifecycleOwner) {
            handleResponseUpdate(it)
        }
    }

    private fun handleResponseUpdate(myutr: StateData<Response<UpdateToDoResponse>>?) {

        when(myutr?.status)
        {
            StateData.DataStatus.LOADING ->{
                //Add Loading Progress bar
//                binding?.loginProgressBar?.visibility = View.VISIBLE
            }
            StateData.DataStatus.SUCCESS -> {
//                binding?.loginProgressBar?.visibility = View.GONE
                if(myutr.data?.body() != null)
                {
                    Toast.makeText(context, getString(R.string.utds), Toast.LENGTH_SHORT).show()             //dismiss sheet and load todolist fragment
                    findNavController().navigate(R.id.action_updateTaskFragment_to_listTaskFragment)
                }
                else if(myutr.data?.code() == 400)
                {
                    Toast.makeText(
                        context,
                        getString(R.string.swrpta),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

    private fun observeAddToDoViewModel() {

            viewModel.myAddToDoResponse.observe(viewLifecycleOwner) {
                handleResponse(it)
            }
    }

    private fun handleResponse(myatr: StateData<Response<AddToDoResponse>>?) {
        when(myatr?.status)
        {
            StateData.DataStatus.LOADING ->{
                //Add Loading Progress bar
//                binding?.loginProgressBar?.visibility = View.VISIBLE
            }
            StateData.DataStatus.SUCCESS -> {
//                binding?.loginProgressBar?.visibility = View.GONE
                if(myatr.data?.body() != null)
                {
                    Toast.makeText(context, getString(R.string.todoadds), Toast.LENGTH_SHORT).show()             //dismiss sheet and load todolist fragment
                    findNavController().navigate(R.id.action_updateTaskFragment_to_listTaskFragment)
                }
                else if(myatr.data?.code() == 400)
                {
                    Toast.makeText(
                        context,
                        getString(R.string.swrpta),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

    private fun loadData() {
        val sharedPreferences = activity!!.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val savedEmail = sharedPreferences.getString(Constants.EMAIL,null)
        val savedUsername = sharedPreferences.getString(Constants.USER_NAME,null)
        val savedId = sharedPreferences.getInt(Constants.ID,-1)
        if (savedEmail != null) {
            addToDoUserEmail = savedEmail
        }
    }

    private fun checkInput(view: View) {


        if (titleTV?.text?.isBlank() == true) {
            Toast.makeText(context, getString(R.string.title_cant_be_empty), Toast.LENGTH_SHORT)
                .show()
        } else if (descriptionTV?.text?.isBlank() == true) {
            Toast.makeText(
                context,
                getString(R.string.description_cant_be_empty),
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    private fun parseStatus(status: String?): Int{
        return when(status)
        {
            "completed" -> 1
            "pending"->0
            else -> {-1}
        }
    }


    private fun setUpUI() {

        if (title != null) {
            titleTV?.text = title
        }
        if (description != null) {
            descriptionTV?.text = description
        }
        if (title != null || description != null) {
            button?.text = getString(R.string.update_button)
            binding?.statusSpinner?.visibility = View.VISIBLE
//            binding?.statusSpinner?.onItemSelectedListener
            binding?.deleteTaskButton?.visibility = View.VISIBLE
            binding?.statusHeading?.visibility = View.VISIBLE
            statusspin?.setSelection(parseStatus(todostatus))
            (activity as MainActivity).supportActionBar?.title = "Update Item"

        } else {
            button?.text = getString(R.string.add_button)
            (activity as MainActivity).supportActionBar?.title = "Add Item"
            addToDoFlag = true
        }
    }


    private fun fetchArguments() {

        if (arguments != null  && args.presentitem != null) {

        }
        todoItem = arguments?.getParcelable<BaseListToDoResponse>("presentitem")
        todoItem?.let {
            title = it.title
            description = it.description
            todostatus = it.status
            idOfTask = it.taskId
        }
    }

    private fun initWidgets(view: View) {

        titleTV = binding?.updateTitle
        descriptionTV = binding?.updateDescription
        button = binding?.updateTaskbutton
        statusspin = binding?.statusSpinner
        deleteButton = binding?.deleteTaskButton
    }
}