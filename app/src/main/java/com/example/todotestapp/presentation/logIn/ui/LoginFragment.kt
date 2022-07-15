package com.example.todotestapp.presentation.logIn.ui

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.todotestapp.R
import com.example.todotestapp.data.db.BaseResponse
import com.example.todotestapp.data.db.LoginResponse
import com.example.todotestapp.data.repository.ToDoRepositoryImpl
import com.example.todotestapp.databinding.FragmentLoginBinding
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import com.example.todotestapp.presentation.listToDo.viewmodel.ListViewModel
import com.example.todotestapp.presentation.listToDo.viewmodel.ListViewModelFactory
import com.example.todotestapp.presentation.logIn.viewmodel.LoginViewModel
import com.example.todotestapp.presentation.logIn.viewmodel.LoginViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LoginFragment : BottomSheetDialogFragment() {


    private lateinit var viewModel: LoginViewModel
    private var binding: FragmentLoginBinding ?= null
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentLoginBinding.inflate(inflater)
        binding?.userInputLayout?.visibility = View.GONE
        val view = binding?.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val repository : ToDoRepository = ToDoRepositoryImpl()
        var loginResponseData: BaseResponse<LoginResponse>

        binding?.loginButton?.setOnClickListener {


            val email = binding?.emailInputEditText?.text.toString()
            if (!email.matches(emailPattern.toRegex())) {
                Toast.makeText(context, "Invalid email address", Toast.LENGTH_SHORT).show()
            }
//            else {
//                setUpUI()
//            }
            val viewModelFactory  = LoginViewModelFactory(repository)
            viewModel = ViewModelProvider(this,viewModelFactory).get(LoginViewModel::class.java)
            viewModel.loginUser(email)
            viewModel.myLoginResponse.observe(viewLifecycleOwner){ response ->
                    if(true){

                        Log.d("StatusCode",response?.statusCode.toString())
                        if(response?.statusCode != 404) {
//                                val loginResponseData = response?.userdata
                                Toast.makeText(
                                    context,
                                    getString(R.string.user_login_successful),
                                    Toast.LENGTH_SHORT
                                ).show()
//                            Log.d("Main",loginResponseData.toString())
//                            Log.d("Main",response.code().toString())
                            }
                            else
                            {
                                Toast.makeText(
                                    context,
                                    getString(R.string.please_sign_up),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
                    else if(response?.statusCode == 404)
                    {
                        Log.e("RETROFIT_ERROR", response.toString())
                        setUpUI()
                    }
//                    else if(response.code()){
//                        setUpUI()
//                    }
            }

        }

       }

    private fun setUpUI() {
        binding?.loginButton?.text = getString(R.string.sign_up)
        binding?.userInputLayout?.visibility = View.VISIBLE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}