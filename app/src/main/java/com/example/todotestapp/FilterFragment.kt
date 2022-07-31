package com.example.todotestapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.navigation.fragment.findNavController
import com.example.todotestapp.databinding.FragmentFilterListDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class FilterFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentFilterListDialogBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFilterListDialogBinding.inflate(inflater, container, false)
        setUpClickListeners()
        return binding.root

    }

    private fun setUpClickListeners() {

            binding.selectedFilterButton.setOnClickListener{
                var stringToPass:String? = null
                val checkedSortId = binding.sortRadio.checkedRadioButtonId
                if(checkedSortId != View.NO_ID) {
                    val radioButton: RadioButton = binding.root.findViewById(checkedSortId)
                    if (radioButton.text.toString() == "Priority High To Low")
                        stringToPass = "DESC"
                    if (radioButton.text.toString() == "Priority Low To High")
                        stringToPass = "ASC"
                }
                var stringToPassStatus:String? = null
                val checkedStatusId = binding.statusRadio.checkedRadioButtonId
                if(checkedStatusId != View.NO_ID)
                {
                    val radioButton: RadioButton = binding.root.findViewById(checkedStatusId)
                    if(radioButton.text.toString() == "Completed")
                    {
                        stringToPassStatus = "completed"
                    }
                    if(radioButton.text.toString() == "Pending")
                    {
                        stringToPassStatus = "pending"
                    }
                }
                var stringToPassPriority:String? = null
                val checkedPriorityId = binding.priorityRadio.checkedRadioButtonId
                if(checkedPriorityId != View.NO_ID)
                {
                    val radioButton: RadioButton = binding.root.findViewById(checkedPriorityId)
                    if(radioButton.text.toString() == "High Priority")
                    {
                        stringToPassPriority = "high"
                    }
                    if(radioButton.text.toString() == "Low Priority")
                    {
                        stringToPassPriority = "low"
                    }
                    if(radioButton.text.toString() == "Medium Priority")
                    {
                        stringToPassPriority = "medium"
                    }
                }
                findNavController().navigate(FilterFragmentDirections.actionFilterFragmentToListTaskFragment(sortBySelected = stringToPass, statusSelected = stringToPassStatus, prioritySelected = stringToPassPriority))
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}