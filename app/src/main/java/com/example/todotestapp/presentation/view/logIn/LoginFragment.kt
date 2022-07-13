package com.example.todotestapp.presentation.view.listToDo.logIn

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todotestapp.databinding.FragmentLoginBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LoginFragment : BottomSheetDialogFragment() {



    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentLoginBinding.inflate(inflater)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        emailFocusListener()
    }

    private fun emailFocusListener() {
            binding.emailInputEditText.setOnFocusChangeListener { _, focused ->
                if(!focused)
                {
                   binding.emailInputLayout.helperText = validEmail()
                }
            }
    }

    private fun validEmail(): String? {

        val emailText = binding.emailInputEditText.text.toString()
        if(Patterns.EMAIL_ADDRESS.matcher(emailText).matches())
        {
            return "Invalid Email Address"
        }

        return null
    }
}