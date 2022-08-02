package com.example.todotestapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.navigation.fragment.findNavController
import com.example.todotestapp.data.repository.Constants.IS_USER_LOGGED_IN
import com.example.todotestapp.data.repository.Constants.ORDER_STRING
import com.example.todotestapp.data.repository.Constants.PRIORITY_STRING
import com.example.todotestapp.data.repository.Constants.SELECTED_SORT
import com.example.todotestapp.data.repository.Constants.SHARED_PREFERENCES
import com.example.todotestapp.databinding.FragmentSortListDialogBinding

class SortFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentSortListDialogBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var sortByStored: Int = -1
    private var sortByString: String? = ""

//    val sharedPreferences = activity?.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
//    private val editor = sharedPreferences?.edit()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSortListDialogBinding.inflate(inflater, container, false)
        checkForUserLocalData()
        Log.v("sortByStored",sortByStored.toString())
        when (sortByStored) {
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
//            val checkedSortId = binding.sortRadio.checkedRadioButtonId
//            val radioButton: RadioButton = binding.root.findViewById(checkedSortId)
//            radioButton.isChecked = false
            binding.sortRadio.clearCheck()
        }

        binding.selectedSortButton.setOnClickListener{
            var stringToPass:String? = null
            val checkedSortId = binding.sortRadio.checkedRadioButtonId
//            val radioButton: RadioButton = binding.root.findViewById(checkedSortId)
//            Log.v("sortByStoredCheck",radioButton.text.toString())
            if(checkedSortId != View.NO_ID) {
                val radioButton: RadioButton = binding.root.findViewById(checkedSortId)
                if (radioButton.text.toString() == "Priority High To Low")//Remember this to change if the text is changed and try to avoid this rather find and approach that includes buttons
                {
                    stringToPass = "DESC"
                    sortByStored = 1
                    val sharedPreferences = activity?.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
                    val editor = sharedPreferences?.edit()
                    editor?.putInt(SELECTED_SORT,sortByStored)
                    editor?.putString(ORDER_STRING,"DESC")
                    editor?.apply()
                }
                if (radioButton.text.toString() == "Priority Low To High") {
                    stringToPass = "ASC"
                    sortByStored = 2
                    val sharedPreferences = activity?.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
                    val editor = sharedPreferences?.edit()
                    editor?.putInt(SELECTED_SORT,sortByStored)
                    editor?.putString(ORDER_STRING,"ASC")
                    editor?.apply()
                }
            }
            else
            {
                sortByStored = 0
                val sharedPreferences = activity?.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
                val editor = sharedPreferences?.edit()
                editor?.putInt(SELECTED_SORT,sortByStored)
                editor?.putString(ORDER_STRING,null)
                editor?.apply()
            }
            findNavController().navigate(SortFragmentDirections.actionSortFragmentToListTaskFragment(sortBySelected = stringToPass))
        }
    }

    private fun checkForUserLocalData() {
        val sharedPreferences = activity?.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        sharedPreferences.let {
            if (sharedPreferences != null) {
                if (sharedPreferences.contains(SELECTED_SORT)) {
                    sortByStored = sharedPreferences.getInt(SELECTED_SORT,10)
                    sortByString = sharedPreferences.getString(ORDER_STRING,"")
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}