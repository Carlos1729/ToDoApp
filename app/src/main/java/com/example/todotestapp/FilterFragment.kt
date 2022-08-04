package com.example.todotestapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.todotestapp.data.repository.Constants
import com.example.todotestapp.databinding.FragmentFilterListDialogBinding
import com.example.todotestapp.presentation.listToDo.viewmodel.ListViewModel
import com.example.todotestapp.presentation.listToDo.viewmodel.ListViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class FilterFragment : BottomSheetDialogFragment() {


    @Inject
    lateinit var viewModelFactory: ListViewModelFactory


    override fun onAttach(context: Context) {
                super.onAttach(context)
                AndroidSupportInjection.inject(this)
    }

    private var _binding: FragmentFilterListDialogBinding? = null

    private val viewModel: ListViewModel by activityViewModels {
        viewModelFactory
    }

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
//        checkForUserLocalData()
        when (viewModel.statusStored.value) {
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
        when (viewModel.priorityStored.value) {
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

    private fun setUpClickListeners() {

            binding.clearAllPriority.setOnClickListener{
                binding.priorityRadio.clearCheck()
            }
            binding.clearAllStatus.setOnClickListener{
                binding.statusRadio.clearCheck()
            }

            binding.selectedFilterButton.setOnClickListener{
                var stringToPassStatus:String? = null
                when (binding.statusRadio.checkedRadioButtonId) {
                    R.id.status_completed ->{
                        stringToPassStatus = "completed"
                        viewModel.setStatusFromFrag(1)
                    }
                    R.id.status_pending -> {
                        stringToPassStatus = "pending"
                        viewModel.setStatusFromFrag(2)
                    }
                    else -> {
                        viewModel.setStatusFromFrag(0)
                    }
                }
                var stringToPassPriority:String? = null
                when (binding.priorityRadio.checkedRadioButtonId) {
                    R.id.priority_high ->{
                        stringToPassPriority = "high"
                        viewModel.setPriorityFromFrag(1)
                    }
                    R.id.priority_medium -> {
                        stringToPassPriority = "medium"
                        viewModel.setPriorityFromFrag(2)
                    }
                    R.id.priority_low -> {
                        stringToPassPriority = "low"
                        viewModel.setPriorityFromFrag(3)
                    }
                    else -> {
                        viewModel.setPriorityFromFrag(0)
                    }
                }
                findNavController().navigate(FilterFragmentDirections.actionFilterFragmentToListTaskFragment())
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}