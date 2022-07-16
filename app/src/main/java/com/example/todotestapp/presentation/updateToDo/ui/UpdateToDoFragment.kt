package com.example.todotestapp.presentation.updateToDo.ui

import android.app.ProgressDialog.show
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
import androidx.navigation.fragment.navArgs
import com.example.todotestapp.MainActivity
import com.example.todotestapp.R
import com.example.todotestapp.data.db.AddToDoRequest
import com.example.todotestapp.data.db.BaseListToDoResponse
import com.example.todotestapp.data.db.ToDo
import com.example.todotestapp.data.db.UpdateToDoRequest
import com.example.todotestapp.data.repository.ToDoRepositoryImpl
import com.example.todotestapp.databinding.FragmentLoginBinding
import com.example.todotestapp.databinding.FragmentUpdateTodoBinding
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import com.example.todotestapp.presentation.SharedViewModel
import com.example.todotestapp.presentation.logIn.viewmodel.LoginViewModel
import com.example.todotestapp.presentation.logIn.viewmodel.LoginViewModelFactory
import com.example.todotestapp.presentation.updateToDo.viewmodel.UpdateToDoViewModel
import com.example.todotestapp.presentation.updateToDo.viewmodel.UpdateToDoViewModelFactory


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
    private lateinit var addToDoUserEmail :String
    private lateinit var viewModel: UpdateToDoViewModel


    private val sharedViewModel:SharedViewModel by activityViewModels()


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

//                var addStatus : String =
                //Call the API for updation
                //if the response is success

                Toast.makeText(context, "ToDo Updated Successfully", Toast.LENGTH_SHORT)
                    .show()
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
                Log.v("signDownFlagTest",addUserEmail)
                //Call the API for updation
                //if the response is success
            }
        }

    }

    private fun observeUpdateToDoViewModel() {
        viewModel.myUpdateToDoResponse.observe(viewLifecycleOwner) {
            if (it.body() != null) {
                Toast.makeText(context, "ToDo Updated Successfully", Toast.LENGTH_SHORT)
                    .show()             //dismiss sheet and load todolist fragment
            }
            else if(it.code()==404){
                Toast.makeText(
                    context,
                    "Something Went Wrong Please Try Again",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun loadData() {
            val sharedPreferences = activity!!.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            val savedEmail = sharedPreferences.getString("EMAILID",null)
            val savedUsername = sharedPreferences.getString("USERNAME",null)
            val savedId = sharedPreferences.getInt("ID",-1)
            if (savedEmail != null) {
                addToDoUserEmail = savedEmail
            }
    }

    private fun observeAddToDoViewModel() {

            viewModel.myAddToDoResponse.observe(viewLifecycleOwner) {
                if (it.body() != null) {
                    Toast.makeText(context, "ToDo Added Successfully", Toast.LENGTH_SHORT)
                        .show()             //dismiss sheet and load todolist fragment
                }
                else if(it.code()==404){
                    Toast.makeText(
                        context,
                        "Something Went Wrong Please Try Again",
                        Toast.LENGTH_SHORT
                    ).show()
                }
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
            statusspin?.setSelection(parseStatus(todostatus))
            (activity as MainActivity).supportActionBar?.title = "Update ToDo"

        } else {
            button?.text = getString(R.string.add_button)
            (activity as MainActivity).supportActionBar?.title = "Add ToDo"
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
    }
}