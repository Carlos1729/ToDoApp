package com.example.todotestapp.presentation.logIn.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.todotestapp.R
import com.example.todotestapp.data.db.SignUpUserRequest
import com.example.todotestapp.data.repository.ToDoRepositoryImpl
import com.example.todotestapp.databinding.FragmentLoginBinding
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import com.example.todotestapp.presentation.logIn.viewmodel.LoginViewModel
import com.example.todotestapp.presentation.logIn.viewmodel.LoginViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.json.JSONObject

class LoginFragment : BottomSheetDialogFragment() {


    private lateinit var viewModel: LoginViewModel
    private var binding: FragmentLoginBinding ?= null
    private var signUpFlag = false
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentLoginBinding.inflate(inflater)
        binding?.userInputLayout?.visibility = View.GONE
        val view = binding?.root
        getDialog()?.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val repository : ToDoRepository = ToDoRepositoryImpl()
     //   var loginResponseData: Response<LoginResponse>

        signUpFlag = false

        binding?.loginButton?.setOnClickListener {


            val viewModelFactory  = LoginViewModelFactory(repository)
            viewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]
            val useremail = binding?.emailInputEditText?.text.toString()
            if(!signUpFlag) {
                if (isValidEmail(useremail)) {
                    viewModel.loginUser(useremail)
                    observeLoginViewModel()
                } else {
                    Toast.makeText(
                        context,
                        getString(R.string.invalid_email_address),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            else{
                val username = binding?.usernameInputEditText?.text.toString()
                var presentUser  = SignUpUserRequest(useremail,username)
                viewModel.signUpUser(presentUser)
                observeSignUpViewModel()
            }
        }

    }

    private fun isValidEmail(email: String): Boolean {
        if (!email.matches(emailPattern.toRegex())) {
            Toast.makeText(context, "Invalid email address", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun observeSignUpViewModel() {

        viewModel.mySignupResponse.observe(viewLifecycleOwner) {
            if (it.body() != null) {
                Toast.makeText(
                    context,"User Signed Up Successfully",
                    Toast.LENGTH_SHORT
                ).show()
                dismiss()
                //dismiss sheet and load todolist fragment
            }
            else if(it.code()==404){
                Toast.makeText(
                    context,
                    "Something Went Wrong Please Try Again",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun observeLoginViewModel() {
        viewModel.myLoginResponse.observe(viewLifecycleOwner) {
            Log.v("signUpFlagTest",it.code().toString())
            if (it.body() != null) {
                Toast.makeText(
                    context,
                    getString(R.string.user_login_successful),
                    Toast.LENGTH_SHORT
                ).show()
                dismiss()
                //dismiss sheet and load todolist fragment
            }
            else if(it.code()==404){
                Toast.makeText(
                    context,
                    getString(R.string.please_sign_up),
                    Toast.LENGTH_SHORT
                ).show()
                showSignUpUI()
                signUpFlag = true
            }
            else{
                //show Toast something went wrong.
            }
        }
    }

    private fun showSignUpUI() {
        binding?.loginButton?.text = getString(R.string.sign_up)
        binding?.userInputLayout?.visibility = View.VISIBLE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}