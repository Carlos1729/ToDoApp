package com.example.todotestapp.presentation.updateToDo.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todotestapp.R
import com.example.todotestapp.data.db.*
import com.example.todotestapp.data.repository.Constants
import com.example.todotestapp.databinding.FragmentUpdateTodoBinding
import com.example.todotestapp.presentation.MainActivity
import com.example.todotestapp.presentation.updateToDo.viewmodel.UpdateToDoViewModel
import com.example.todotestapp.presentation.updateToDo.viewmodel.UpdateToDoViewModelFactory
import dagger.android.support.DaggerFragment
import retrofit2.Response
import javax.inject.Inject


class UpdateToDoFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: UpdateToDoViewModelFactory

    @Inject
    lateinit var sharedPreferences : SharedPreferences


    private val args by navArgs<UpdateToDoFragmentArgs>()
    private var todoItem: BaseListToDoResponse? = null
    private var title: String? = null
    private var description: String? = null
    private var titleTV: TextView? = null
    private var addToDoFlag:  Boolean = false
    private  var binding: FragmentUpdateTodoBinding ?= null
    private var descriptionTV: TextView? = null
    private var statusspin: Spinner? = null
    private var priorityspin: AutoCompleteTextView? = null
    private var todostatus: String? = null
    private var idOfTask: Int? = null
    private var button: Button? = null
    private var deleteButton: Button? = null
    private lateinit var addToDoUserEmail :String
    private lateinit var viewModel: UpdateToDoViewModel
    private var deleteflag = false

    override fun onResume() {
        super.onResume()
        val priorities = resources.getStringArray(R.array.priorities)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, priorities)
        binding?.priorityDropdownEdittext?.setAdapter(arrayAdapter)
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

        viewModel = ViewModelProvider(this, viewModelFactory)[UpdateToDoViewModel::class.java]
        setUpClickListeners(view)
        observeViewModel()
    }

    private fun setUpClickListeners(view :  View) {
        button?.setOnClickListener {
            if(checkInput()) {
                if (!addToDoFlag) {
                    val updatedTitle: String = titleTV?.text.toString()
                    val updatedDescription: String = descriptionTV?.text.toString()
                    val updatedStatus: String = statusspin?.selectedItem.toString()
                    val hashMap: HashMap<String, String> = HashMap<String, String>()
                    hashMap["High Priority"] = "high"
                    hashMap["Medium Priority"] = "medium"
                    hashMap["Low Priority"] = "low"
                    val updatedPriority: String? = hashMap[priorityspin?.text.toString()]
                    val presentUpdateToDoRequest = UpdateToDoRequest(
                        updatedTitle,
                        updatedDescription,
                        updatedStatus,
                        updatedPriority!!
                    )
                    viewModel.updateToDo(idOfTask, presentUpdateToDoRequest)
                } else {
                    val addTitle: String = titleTV?.text.toString()
                    val addDescription: String = descriptionTV?.text.toString()
                    val addStatus: String = "pending"
                    val hashMap: HashMap<String, String> = HashMap<String, String>()
                    hashMap["High Priority"] = "high"
                    hashMap["Medium Priority"] = "medium"
                    hashMap["Low Priority"] = "low"
                    val addPriority: String? = hashMap[priorityspin?.text.toString()]
                    loadData()
                    val addUserEmail: String = addToDoUserEmail
                    val presentAddToDoRequest = AddToDoRequest(
                        addUserEmail,
                        addTitle,
                        addDescription,
                        addStatus,
                        addPriority!!
                    )
                    viewModel.addToDo(presentAddToDoRequest)
                }
            }
        }

        deleteButton?.setOnClickListener {
            val updatedTitle: String = titleTV?.text.toString()
            val updatedDescription: String = descriptionTV?.text.toString()
            val updatedStatus: String = "inactive"
            val hashMap: HashMap<String, String> = HashMap<String, String>()
            hashMap["High Priority"] = "high"
            hashMap["Medium Priority"] = "medium"
            hashMap["Low Priority"] = "low"
            val updatedPriority: String? = hashMap[priorityspin?.text.toString()]
            val presentUpdateToDoRequest = UpdateToDoRequest(
                updatedTitle,
                updatedDescription,
                updatedStatus,
                updatedPriority!!
            )
            viewModel.updateToDo(idOfTask, presentUpdateToDoRequest)
            deleteflag = true
        }
    }

    private fun handleResponseDelete(mydtr: StateData<Response<BaseResponse>>?) {
        when (mydtr?.status) {
            StateData.DataStatus.LOADING -> {
                binding?.updateProgressBar?.visibility = View.VISIBLE
            }
            StateData.DataStatus.SUCCESS -> {
                binding?.updateProgressBar?.visibility = View.GONE
                if (mydtr.data?.body() != null) {
                    Toast.makeText(context, getString(R.string.mtds), Toast.LENGTH_SHORT)
                        .show()
                    findNavController().navigate(R.id.action_updateTaskFragment_to_listTaskFragment)
                } else if (mydtr.data?.code() == 400) {
                    Toast.makeText(
                        context,
                        getString(R.string.swrpta),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            else -> {}
        }
    }

    private fun observeViewModel() {
        viewModel.myUpdateToDoResponse.observe(viewLifecycleOwner) {
            handleResponseUpdate(it)
        }

        viewModel.myAddToDoResponse.observe(viewLifecycleOwner) {
            handleResponse(it)
        }

        viewModel.myDeleteToDoResponse.observe(viewLifecycleOwner) {
            handleResponseDelete(it)
        }
    }

    private fun handleResponseUpdate(myutr: StateData<Response<UpdateToDoResponse>>?) {
        when (myutr?.status) {
            StateData.DataStatus.LOADING -> {
                binding?.updateProgressBar?.visibility = View.VISIBLE
            }
            StateData.DataStatus.SUCCESS -> {
                binding?.updateProgressBar?.visibility = View.GONE
                if (myutr.data?.body() != null) {
                    if(deleteflag)
                    {
                        Toast.makeText(context, getString(R.string.mtds), Toast.LENGTH_SHORT).show()
                        deleteflag = false
                    }
                    else {
                        Toast.makeText(context, getString(R.string.utds), Toast.LENGTH_SHORT).show()
                    }
                    findNavController().navigate(R.id.action_updateTaskFragment_to_listTaskFragment)
                } else if (myutr.data?.code() == 400) {
                    Toast.makeText(
                        context,
                        getString(R.string.duplititle),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            else -> {}
        }
    }


    private fun handleResponse(myatr: StateData<Response<AddToDoResponse>>?) {
        when (myatr?.status) {
            StateData.DataStatus.LOADING -> {
                binding?.updateProgressBar?.visibility = View.VISIBLE
            }
            StateData.DataStatus.SUCCESS -> {
                binding?.updateProgressBar?.visibility = View.GONE
                if (myatr.data?.body() != null) {
                    Toast.makeText(context, getString(R.string.todoadds), Toast.LENGTH_SHORT)
                        .show()             //dismiss sheet and load todolist fragment
                    findNavController().navigateUp()
                } else if (myatr.data?.code() == 400 && descriptionTV?.text?.isBlank() == false && titleTV?.text?.isBlank() == false) {
                    Toast.makeText(
                        context,
                        getString(R.string.duplititle),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            else -> {}
        }
    }

    private fun loadData() {
            val savedEmail = sharedPreferences.getString(Constants.EMAIL,null)
            if (savedEmail != null) {
                addToDoUserEmail = savedEmail
            }
    }

    private fun checkInput():Boolean {

        if(titleTV?.text?.isBlank() == true && descriptionTV?.text?.isBlank() == true)
        {
            Toast.makeText(context, getString(R.string.ntndce), Toast.LENGTH_SHORT)
                .show()
            return false
        }
        else if (titleTV?.text?.isBlank() == true) {
            Toast.makeText(context, getString(R.string.title_cant_be_empty), Toast.LENGTH_SHORT)
                .show()
            return false
        } else if (descriptionTV?.text?.isBlank() == true) {
            Toast.makeText(context, getString(R.string.description_cant_be_empty), Toast.LENGTH_SHORT).show()
            return false
        }
        else if(priorityspin?.text.toString().isBlank()){
            Toast.makeText(context, getString(R.string.psootp), Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun parseStatus(status: String?): Int{
        return when(status) {
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
        todoItem = arguments?.getParcelable<BaseListToDoResponse>("presentitem")
        todoItem?.let {
            title = it.title
            description = it.description
            todostatus = it.status
            idOfTask = it.taskId
            val hashMapReverse : HashMap<String, String> = HashMap<String, String> ()
            hashMapReverse["high"] = "High Priority"
            hashMapReverse["medium"] = "Medium Priority"
            hashMapReverse["low"] = "Low Priority"
            val editable: Editable = SpannableStringBuilder(hashMapReverse[it.priority])
            priorityspin?.text = editable
        }
    }

    private fun initWidgets(view: View) {
        titleTV = binding?.updateTitle
        descriptionTV = binding?.updateDescription
        button = binding?.updateTaskbutton
        statusspin = binding?.statusSpinner
        priorityspin = binding?.priorityDropdownEdittext
        deleteButton = binding?.deleteTaskButton
    }
}