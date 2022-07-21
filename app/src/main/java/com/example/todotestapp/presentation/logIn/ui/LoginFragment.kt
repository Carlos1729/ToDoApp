package com.example.todotestapp.presentation.logIn.ui

import android.content.Context
import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todotestapp.R
import com.example.todotestapp.data.db.*
import com.example.todotestapp.data.db.StateData.DataStatus
import com.example.todotestapp.data.repository.Constants.EMAIL
import com.example.todotestapp.data.repository.Constants.ID
import com.example.todotestapp.data.repository.Constants.IS_USER_LOGGED_IN
import com.example.todotestapp.data.repository.Constants.SHARED_PREFERENCES
import com.example.todotestapp.data.repository.Constants.USER_NAME
import com.example.todotestapp.data.repository.ToDoRepositoryImpl
import com.example.todotestapp.databinding.FragmentLoginBinding
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import com.example.todotestapp.presentation.MainActivity
import com.example.todotestapp.presentation.logIn.viewmodel.LoginViewModel
import com.example.todotestapp.presentation.logIn.viewmodel.LoginViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import retrofit2.Response



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

        (activity as MainActivity).supportActionBar?.title = "Login ToDo"

        binding?.loginProgressBar?.visibility = View.GONE

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
//                (activity as MainActivity).supportActionBar?.title = "Sign Up"
                val username = binding?.usernameInputEditText?.text.toString()
                val presentUser  = SignUpUserRequest(useremail,username)
                viewModel.signUpUser(presentUser)
                binding?.userInputLayout?.helperText = ""
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
           handleResponseSignUp(it)
        }
    }

    private fun handleResponseSignUp(mysur: StateData<Response<SignUpUserResponse>>?) {
        when (mysur?.status) {
            DataStatus.LOADING ->{
                binding?.loginProgressBar?.visibility = View.VISIBLE
            }
            DataStatus.SUCCESS -> {
                binding?.loginProgressBar?.visibility = View.GONE
                if (mysur.data?.body() != null) {
                Toast.makeText(
                    context,"User Signed Up Successfully",
                    Toast.LENGTH_SHORT
                ).show()
                mysur.data!!.body()?.author?.let { user ->
                    savedata(user)
                }
                dismiss()
                findNavController().navigate(R.id.action_loginFragment_to_listTaskFragment)
                //dismiss sheet and load todolist fragment
                }
                else if(mysur.data?.code()==404){
                    Toast.makeText(
                    context,
                    "Something Went Wrong Please Try Again",
                    Toast.LENGTH_SHORT
                ).show()
                }
            }
        }
    }

    private fun handleResponse(mlr : StateData<Response<LoginResponse>>?) {
        when (mlr?.status) {
            DataStatus.LOADING ->{
                binding?.loginProgressBar?.visibility = View.VISIBLE
            }
            DataStatus.SUCCESS -> {
                binding?.loginProgressBar?.visibility = View.GONE
//                Log.v("Iamhere", "I am here")
                if (mlr.data?.body() != null) {
                    Log.v("Iamhere", "I am here")
                    Toast.makeText(
                        context,
                        getString(R.string.user_login_successful),
                        Toast.LENGTH_SHORT
                    ).show()
                    mlr.data?.body()?.author?.let { user ->
                        savedata(user)
                    }
                    findNavController().navigate(R.id.action_loginFragment_to_listTaskFragment)
                }
                else if(mlr.data?.code()==404){
                    Toast.makeText(
                        context,
                        getString(R.string.please_sign_up),
                        Toast.LENGTH_SHORT
                    ).show()
                    showSignUpUI()
                    signUpFlag = true
                }
            }
        }
    }

    private fun observeLoginViewModel() {
        viewModel.myLoginResponse?.observe(viewLifecycleOwner) {
            handleResponse(
                it
            )
        }
//        viewModel.myLoginResponse?.observe(viewLifecycleOwner) {
//            Log.v("signUpFlagTest",it?.data?.code().toString())
//            Log.v("ThisisDebug", it?.status.toString())
//            when(it?.status)
//            {
//                DataStatus.SUCCESS -> {
//
//                    if (it.data?.body() != null) {
//                        Toast.makeText(
//                            context,
//                            getString(R.string.user_login_successful),
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        it.data?.body()?.author?.let { user ->
//                            savedata(user)
//                        }
//                        findNavController().navigate(R.id.action_loginFragment_to_listTaskFragment)
//                    }
//                    else if(it.data?.code()==404){
//                        Toast.makeText(
//                            context,
//                            getString(R.string.please_sign_up),
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        showSignUpUI()
//                        signUpFlag = true
//                    }
//                }
//                DataStatus.ERROR -> {
////                    val e: Throwable = it.error()
//                }
////                is NetworkResult.Loading -> {}
////                is NetworkResult.Success -> {
////                    if (it.data?.body() != null) {
////                        Toast.makeText(
////                            context,
////                            getString(R.string.user_login_successful),
////                            Toast.LENGTH_SHORT
////                        ).show()
////                        it.data.body()?.author?.let { user ->
////                            savedata(user)
////                        }
////                        findNavController().navigate(R.id.action_loginFragment_to_listTaskFragment)
////                    }
////                    else if(it.data?.code()==404){
////                        Toast.makeText(
////                            context,
////                            getString(R.string.please_sign_up),
////                            Toast.LENGTH_SHORT
////                        ).show()
////                        showSignUpUI()
////                        signUpFlag = true
////                    }
////                }
////                is NetworkResult.Error -> {
////                    Toast.makeText(
////                        context, it.errorMessage.toString(),
////                        Toast.LENGTH_SHORT
////                    ).show()
////                }
//                else -> {}
//            }
//            Log.v("signUpFlagTest",it.code().toString())
//            if (it.body() != null) {
//                Toast.makeText(
//                    context,
//                    getString(R.string.user_login_successful),
//                    Toast.LENGTH_SHORT
//                ).show()
//                it.body()?.author?.let { user ->
//                    savedata(user)
//                }
//                findNavController().navigate(R.id.action_loginFragment_to_listTaskFragment)
//            }
//            else if(it.code()==404){
//                Toast.makeText(
//                    context,
//                    getString(R.string.please_sign_up),
//                    Toast.LENGTH_SHORT
//                ).show()
//                showSignUpUI()
//                signUpFlag = true
//            }
//            else{
//                Toast.makeText(
//                    context,
//                    getString(R.string.noic),
//                    Toast.LENGTH_SHORT
//                ).show()
//                //show Toast something went wrong.
//            }
//        }
    }

    private fun savedata(userDetails: UserDetails) {
        val sharedPreferences = activity!!.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply{
            putString(EMAIL,userDetails.email)
            putString(USER_NAME,userDetails.name)
            putInt(ID,userDetails.id)
            putBoolean(IS_USER_LOGGED_IN, true)
        }.apply()
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