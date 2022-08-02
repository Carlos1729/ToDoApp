package com.example.todotestapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.navigation.fragment.findNavController
import com.example.todotestapp.data.repository.Constants
import com.example.todotestapp.databinding.FragmentFilterListDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class FilterFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentFilterListDialogBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var statusStored: Int = -1
    private var priorityStored: Int = -1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFilterListDialogBinding.inflate(inflater, container, false)
        checkForUserLocalData()
        when (statusStored) {
            1 -> {
                val radioButtonSafe1: RadioButton = binding.root.findViewById(R.id.status_pending)
                radioButtonSafe1.isChecked = false
                val radioButtonSafe2: RadioButton = binding.root.findViewById(R.id.status_inactive)
                radioButtonSafe2.isChecked = false
                val radioButton: RadioButton = binding.root.findViewById(R.id.status_completed)
                radioButton.isChecked = true
            }
            2 -> {
                val radioButtonSafe1: RadioButton = binding.root.findViewById(R.id.status_completed)
                radioButtonSafe1.isChecked = false
                val radioButtonSafe2: RadioButton = binding.root.findViewById(R.id.status_inactive)
                radioButtonSafe2.isChecked = false
                val radioButton: RadioButton = binding.root.findViewById(R.id.status_pending)
                radioButton.isChecked = true
            }
            3 -> {
                val radioButtonSafe1: RadioButton = binding.root.findViewById(R.id.status_completed)
                radioButtonSafe1.isChecked = false
                val radioButtonSafe2: RadioButton = binding.root.findViewById(R.id.status_pending)
                radioButtonSafe2.isChecked = false
                val radioButton: RadioButton = binding.root.findViewById(R.id.status_inactive)
                radioButton.isChecked = true
            }
            else -> {
                    val radioButtonSafe1: RadioButton = binding.root.findViewById(R.id.status_completed)
                    radioButtonSafe1.isChecked = false
                    val radioButtonSafe2: RadioButton = binding.root.findViewById(R.id.status_inactive)
                    radioButtonSafe2.isChecked = false
                    val radioButton: RadioButton = binding.root.findViewById(R.id.status_pending)
                    radioButton.isChecked = false

            }
        }
        when (priorityStored) {
            1 -> {
                val radioButtonSafe1: RadioButton = binding.root.findViewById(R.id.priority_medium)
                radioButtonSafe1.isChecked = false
                val radioButtonSafe2: RadioButton = binding.root.findViewById(R.id.priority_low)
                radioButtonSafe2.isChecked = false
                val radioButton: RadioButton = binding.root.findViewById(R.id.priority_high)
                radioButton.isChecked = true
            }
            2 -> {
                val radioButtonSafe1: RadioButton = binding.root.findViewById(R.id.priority_high)
                radioButtonSafe1.isChecked = false
                val radioButtonSafe2: RadioButton = binding.root.findViewById(R.id.priority_low)
                radioButtonSafe2.isChecked = false
                val radioButton: RadioButton = binding.root.findViewById(R.id.priority_medium)
                radioButton.isChecked = true
            }
            3 -> {
                val radioButtonSafe1: RadioButton = binding.root.findViewById(R.id.priority_medium)
                radioButtonSafe1.isChecked = false
                val radioButtonSafe2: RadioButton = binding.root.findViewById(R.id.priority_high)
                radioButtonSafe2.isChecked = false
                val radioButton: RadioButton = binding.root.findViewById(R.id.priority_low)
                radioButton.isChecked = true
            }
            else -> {
                val radioButtonSafe1: RadioButton = binding.root.findViewById(R.id.priority_medium)
                radioButtonSafe1.isChecked = false
                val radioButtonSafe2: RadioButton = binding.root.findViewById(R.id.priority_high)
                radioButtonSafe2.isChecked = false
                val radioButton: RadioButton = binding.root.findViewById(R.id.priority_low)
                radioButton.isChecked = false
            }
        }
        setUpClickListeners()
        return binding.root

    }

    private fun checkForUserLocalData() {
        val sharedPreferences = activity?.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        sharedPreferences.let {
            if (sharedPreferences != null) {
                if (sharedPreferences.contains(Constants.SELECTED_STATUS)) {
                    statusStored = sharedPreferences.getInt(Constants.SELECTED_STATUS,10)
                    Log.v("sortByStored",statusStored.toString())
                }
                if (sharedPreferences.contains(Constants.SELECTED_PRIORITY)) {
                    priorityStored = sharedPreferences.getInt(Constants.SELECTED_PRIORITY,10)
                    Log.v("sortByStored",priorityStored.toString())
                }
            }
        }
    }

    private fun setUpClickListeners() {

            binding.clearAllPriority.setOnClickListener{
                binding.priorityRadio.clearCheck()
            }
            binding.clearAllStatus.setOnClickListener{
                binding.statusRadio.clearCheck()
            }

            binding.selectedFilterButton.setOnClickListener{
                var stringToPassStatus:String? = null
                val checkedStatusId = binding.statusRadio.checkedRadioButtonId
                if(checkedStatusId != View.NO_ID)
                {
                    val radioButton: RadioButton = binding.root.findViewById(checkedStatusId)
                    if(radioButton.text.toString() == "Completed")
                    {
                        stringToPassStatus = "completed"
                        statusStored = 1
                        val sharedPreferences = activity?.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE)
                        val editor = sharedPreferences?.edit()
                        editor?.putInt(Constants.SELECTED_STATUS,statusStored)
                        editor?.apply()
                    }
                    if(radioButton.text.toString() == "Pending")
                    {
                        stringToPassStatus = "pending"
                        statusStored = 2
                        val sharedPreferences = activity?.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE)
                        val editor = sharedPreferences?.edit()
                        editor?.putInt(Constants.SELECTED_STATUS,statusStored)
                        editor?.apply()
                    }
                }
                else{
                    statusStored = 0
                    val sharedPreferences = activity?.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE)
                    val editor = sharedPreferences?.edit()
                    editor?.putInt(Constants.SELECTED_STATUS,statusStored)
                    editor?.apply()
                }
                var stringToPassPriority:String? = null
                val checkedPriorityId = binding.priorityRadio.checkedRadioButtonId
                if(checkedPriorityId != View.NO_ID)
                {
                    val radioButton: RadioButton = binding.root.findViewById(checkedPriorityId)
                    if(radioButton.text.toString() == "High Priority")
                    {
                        stringToPassPriority = "high"
                        priorityStored = 1
                        val sharedPreferences = activity?.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE)
                        val editor = sharedPreferences?.edit()
                        editor?.putInt(Constants.SELECTED_PRIORITY,priorityStored)
                        editor?.apply()
                    }
                    if(radioButton.text.toString() == "Low Priority")
                    {
                        stringToPassPriority = "low"
                        priorityStored = 3
                        val sharedPreferences = activity?.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE)
                        val editor = sharedPreferences?.edit()
                        editor?.putInt(Constants.SELECTED_PRIORITY,priorityStored)
                        editor?.apply()
                    }
                    if(radioButton.text.toString() == "Medium Priority")
                    {
                        stringToPassPriority = "medium"
                        priorityStored = 2
                        val sharedPreferences = activity?.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE)
                        val editor = sharedPreferences?.edit()
                        editor?.putInt(Constants.SELECTED_PRIORITY,priorityStored)
                        editor?.apply()
                    }
                }
                else{
                    priorityStored = 0
                    val sharedPreferences = activity?.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE)
                    val editor = sharedPreferences?.edit()
                    editor?.putInt(Constants.SELECTED_PRIORITY,priorityStored)
                    editor?.apply()
                }
                findNavController().navigate(FilterFragmentDirections.actionFilterFragmentToListTaskFragment(statusSelected = stringToPassStatus, prioritySelected = stringToPassPriority))
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}