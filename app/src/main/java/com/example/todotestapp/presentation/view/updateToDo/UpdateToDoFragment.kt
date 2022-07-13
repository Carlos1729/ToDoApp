package com.example.todotestapp.presentation.view.listToDo.updateToDo

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.todotestapp.MainActivity
import com.example.todotestapp.R
import com.example.todotestapp.data.db.ToDo


class UpdateToDoFragment : Fragment() {


    private val args by navArgs<UpdateToDoFragmentArgs>()
    private var todoItem: ToDo? = null
    private var title: String? = null
    private var description: String? = null
    private var titleTV: TextView? = null
    private var descriptionTV: TextView? = null
    private var titleRecieved: String? = null
    private var button: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_update_todo, container, false)
        initWidgets(view)
        fetchArguments()
        setUpUI()


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        button?.setOnClickListener { checkInput(view) }

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


    private fun setUpUI() {

        if (title != null) {
            titleTV?.text = title
        }
        if (description != null) {
            descriptionTV?.text = description
        }
        if (title != null || description != null) {
            button?.text = getString(R.string.update_button)
            (activity as MainActivity).supportActionBar?.title = "Update ToDo"

        } else {
            button?.text = getString(R.string.add_button)
            (activity as MainActivity).supportActionBar?.title = "Add ToDo"
        }
    }

    private fun fetchArguments() {

        if (arguments != null  && args.presentitem != null) {

        }
        todoItem = arguments?.getParcelable<ToDo>("presentitem")
        todoItem?.let {
            title = it.title
            description = it.description
        }
    }

    private fun initWidgets(view: View) {

        titleTV = view.findViewById<TextView>(R.id.update_title)
        descriptionTV = view.findViewById<TextView>(R.id.update_description)
        button = view.findViewById<Button>(R.id.updateTaskbutton)

    }
}