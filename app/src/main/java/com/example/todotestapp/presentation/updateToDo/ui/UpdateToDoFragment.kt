package com.example.todotestapp.presentation.updateToDo.ui

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.todotestapp.MainActivity
import com.example.todotestapp.R
import com.example.todotestapp.data.db.ToDo
import com.example.todotestapp.databinding.FragmentLoginBinding
import com.example.todotestapp.databinding.FragmentUpdateTodoBinding


class UpdateToDoFragment : Fragment() {


    private val args by navArgs<UpdateToDoFragmentArgs>()
    private var todoItem: ToDo? = null
    private var title: String? = null
    private var description: String? = null
    private var titleTV: TextView? = null
    private var addToDoFlag:  Boolean = false
    private  var binding: FragmentUpdateTodoBinding ?= null
    private var descriptionTV: TextView? = null
    private var statusspin: Spinner? = null
    private var todostatus: String? = null
    private var button: Button? = null


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

        button?.setOnClickListener {
            checkInput(view)
            if(!addToDoFlag) {
                var updatedTitle: String = titleTV?.text.toString()
                var updatedDescription: String = descriptionTV?.text.toString()

                //Call the API for updation
                //if the response is success

                Toast.makeText(context, "ToDo Updated Successfully", Toast.LENGTH_SHORT)
                    .show()
            }
            else
            {
                var addTitle: String = titleTV?.text.toString()
                var addDescription: String = descriptionTV?.text.toString()
                //Call the API for updation
                //if the response is success
                Toast.makeText(context, "ToDo Added Successfully", Toast.LENGTH_SHORT)
                    .show()
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
        todoItem = arguments?.getParcelable<ToDo>("presentitem")
        todoItem?.let {
            title = it.title
            description = it.description
            todostatus = it.status
        }
    }

    private fun initWidgets(view: View) {

        titleTV = binding?.updateTitle
        descriptionTV = binding?.updateDescription
        button = binding?.updateTaskbutton
        statusspin = binding?.statusSpinner
    }
}