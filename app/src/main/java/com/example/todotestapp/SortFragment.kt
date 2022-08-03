package com.example.todotestapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todotestapp.data.repository.Constants.IS_USER_LOGGED_IN
import com.example.todotestapp.data.repository.Constants.ORDER_STRING
import com.example.todotestapp.data.repository.Constants.PRIORITY_STRING
import com.example.todotestapp.data.repository.Constants.SELECTED_SORT
import com.example.todotestapp.data.repository.Constants.SHARED_PREFERENCES
import com.example.todotestapp.data.repository.ToDoRepositoryImpl
import com.example.todotestapp.databinding.FragmentSortListDialogBinding
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import com.example.todotestapp.domain.usecase.ListToDoUseCase
import com.example.todotestapp.presentation.listToDo.viewmodel.ListViewModel
import com.example.todotestapp.presentation.listToDo.viewmodel.ListViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class SortFragment : DaggerFragment() {



      @Inject
      lateinit var viewModelFactory: ListViewModelFactory




     private var _binding: FragmentSortListDialogBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var sortByStored: Int = -1
    private var sortByString: String? = ""
    private val viewModel: ListViewModel by activityViewModels {
        viewModelFactory
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSortListDialogBinding.inflate(inflater, container, false)

        Log.v("sortByCheck",viewModel.sortByStored.value.toString())
        when (viewModel.sortByStored.value) {
            1 -> {
                val radioButtonSafe: RadioButton = binding.root.findViewById(R.id.textView4)
                radioButtonSafe.isChecked = false
                val radioButton: RadioButton = binding.root.findViewById(R.id.textView3)
                radioButton.isChecked = true
            }
            2 -> {
                val radioButtonSafe: RadioButton = binding.root.findViewById(R.id.textView3)
                radioButtonSafe.isChecked = false
                val radioButton: RadioButton = binding.root.findViewById(R.id.textView4)
                radioButton.isChecked = true
            }
            else -> {
                val radioButton1: RadioButton = binding.root.findViewById(R.id.textView3)
                radioButton1.isChecked = false
                val radioButton2: RadioButton = binding.root.findViewById(R.id.textView4)
                radioButton2.isChecked = false
            }
        }
        setUpClickListeners()
        return binding.root
    }

    private fun setUpClickListeners() {

        binding.clearAllSortby.setOnClickListener{
            binding.sortRadio.clearCheck()
        }

        binding.selectedSortButton.setOnClickListener{
            var stringToPass:String? = null
            when (binding.sortRadio.checkedRadioButtonId) {
                R.id.textView3 ->{
                    stringToPass = "DESC"
                    viewModel.setSortByStoredFromFrag(1)
                }
                R.id.textView4 -> {
                    stringToPass = "ASC"
                    viewModel.setSortByStoredFromFrag(2)
                }
                else -> {
                    viewModel.setSortByStoredFromFrag(0)
                }
            }
            findNavController().navigate(SortFragmentDirections.actionSortFragmentToListTaskFragment(sortBySelected = stringToPass))
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}