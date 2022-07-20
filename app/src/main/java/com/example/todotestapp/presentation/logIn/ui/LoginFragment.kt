package com.example.todotestapp.presentation.logIn.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todotestapp.R
import com.example.todotestapp.data.db.SignUpUserRequest
import com.example.todotestapp.data.db.UserDetails
import com.example.todotestapp.data.repository.Constants.EMAIL
import com.example.todotestapp.data.repository.Constants.ID
import com.example.todotestapp.data.repository.Constants.IS_USER_LOGGED_IN
import com.example.todotestapp.data.repository.Constants.SHARED_PREFERENCES
import com.example.todotestapp.data.repository.Constants.USER_NAME
import com.example.todotestapp.data.repository.ToDoRepositoryImpl
import com.example.todotestapp.databinding.FragmentLoginBinding
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import com.example.todotestapp.presentation.MainActivity
import com.example.todotestapp.presentation.listToDo.viewmodel.ListViewModel
import com.example.todotestapp.presentation.listToDo.viewmodel.ListViewModelFactory
import com.example.todotestapp.presentation.logIn.viewmodel.LoginViewModel
import com.example.todotestapp.presentation.logIn.viewmodel.LoginViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class LoginFragment : BottomSheetDialogFragment() {




    private var binding: FragmentLoginBinding ?= null
    private var signUpFlag = false
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    @Inject
    lateinit var loginViewModelFactory: LoginViewModelFactory

    private val viewModel : LoginViewModel by activityViewModels {loginViewModelFactory}





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
     //   var loginResponseData: Response<LoginResponse>

        signUpFlag = false

        (activity as MainActivity).supportActionBar?.title = "Login ToDo"

        binding?.loginButton?.setOnClickListener {


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
            if (it.body() != null) {
                Toast.makeText(
                    context,"User Signed Up Successfully",
                    Toast.LENGTH_SHORT
                ).show()
                it.body()?.author?.let { user ->
                    savedata(user)
                }
                findNavController().navigate(R.id.action_loginFragment_to_listTaskFragment)
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


    //the first parameter is the owner and the second parameter is the  owner ok so based on the lifecycle of the login fragment fetch or send dara
    //whenever there is change in the present login response this gets executed
    private fun observeLoginViewModel() {
        viewModel.myLoginResponse.observe(viewLifecycleOwner) {
            Log.v("signUpFlagTest",it.code().toString())
            if (it.body() != null) {
                Toast.makeText(
                    context,
                    getString(R.string.user_login_successful),
                    Toast.LENGTH_SHORT
                ).show()
                it.body()?.author?.let { user ->
                    savedata(user)
                }
                findNavController().navigate(R.id.action_loginFragment_to_listTaskFragment)
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
                Toast.makeText(
                    context,
                    getString(R.string.noic),
                    Toast.LENGTH_SHORT
                ).show()
                //show Toast something went wrong.
            }
        }
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