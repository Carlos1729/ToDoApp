package com.example.todotestapp

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.todotestapp.databinding.FragmentSortListDialogBinding

class SortFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentSortListDialogBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSortListDialogBinding.inflate(inflater, container, false)
        setUpClickListeners()
        return binding.root

    }

    private fun setUpClickListeners() {
        binding.selectedSortButton.setOnClickListener{
            var stringToPass:String? = null
            val checkedSortId = binding.sortRadio.checkedRadioButtonId
            if(checkedSortId != View.NO_ID) {
                val radioButton: RadioButton = binding.root.findViewById(checkedSortId)
                if (radioButton.text.toString() == "Priority High To Low")
                    stringToPass = "DESC"
                if (radioButton.text.toString() == "Priority Low To High")
                    stringToPass = "ASC"
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