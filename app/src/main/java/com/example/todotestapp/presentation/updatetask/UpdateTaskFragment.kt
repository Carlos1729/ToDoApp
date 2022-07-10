package com.example.todotestapp.presentation.updatetask

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import com.example.todotestapp.R


class UpdateTaskFragment : Fragment() {

    private val args by navArgs<UpdateTaskFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_update_task, container, false)

        view.findViewById<TextView>(R.id.update_title).setText(args.presentitem.stringTitleId)
        view.findViewById<TextView>(R.id.update_description).setText(args.presentitem.stringDescriptionId)


        return view
    }

}