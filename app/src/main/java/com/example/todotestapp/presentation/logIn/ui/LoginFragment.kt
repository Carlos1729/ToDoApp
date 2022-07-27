package com.example.todotestapp.presentation.logIn.ui

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.adapters.TextViewBindingAdapter.setText
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
import com.example.todotestapp.databinding.FragmentLoginBinding
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import com.example.todotestapp.presentation.MainActivity
import com.example.todotestapp.presentation.logIn.viewmodel.LoginViewModel
import com.example.todotestapp.presentation.logIn.viewmodel.LoginViewModelFactory
import dagger.android.support.DaggerFragment
import retrofit2.Response
import javax.inject.Inject


class LoginFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: LoginViewModelFactory

    @Inject
    lateinit var repository : ToDoRepository

    private lateinit var viewModel: LoginViewModel

    private var binding: FragmentLoginBinding? = null
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
        return view

    }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        signUpFlag = false
        (activity as MainActivity).supportActionBar?.title = "Login ToDo"
            viewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]

            binding?.loginButton?.setOnClickListener {
                binding?.emailInputEditText?.setOnFocusChangeListener { _, focused ->
                    if (focused) {
                        binding?.emailInputLayout?.helperText = checkemail()
                    }
                }
                val useremail = binding?.emailInputEditText?.text.toString()
//                Log.v("UseremailOTP",useremail)
                if (!signUpFlag) {
                    if (isValidEmail(useremail)) {
                        viewModel.loginUser(useremail)
                        observeLoginViewModel()
                    } else {
                        binding?.emailInputLayout?.error = getString(R.string.invalid_email)
                    }
                } else {
                    (activity as MainActivity).supportActionBar?.title = "Sign Up"
                    binding?.emailInputEditText?.setOnFocusChangeListener { _, focused ->
                        if (!focused) {
                            binding?.emailInputLayout?.helperText = checkemail()
                        }
                    }
                    val username = binding?.usernameInputEditText?.text.toString()
                    if (!isValidEmail((useremail)) && !checkInputs(username)) {
                        binding?.emailInputLayout?.error = getString(R.string.invalid_email)
                        binding?.userInputLayout?.error = getString(R.string.invalid_username)
                    } else if (!isValidEmail((useremail))) {
                        binding?.emailInputLayout?.error = getString(R.string.invalid_email)
                    } else if (!checkInputs(username)) {
                        binding?.userInputLayout?.error = getString(R.string.invalid_username)
                    } else {
                        val presentUser = SignUpUserRequest(useremail, username)
                        viewModel.signUpUser(presentUser)
                        binding?.userInputLayout?.helperText = ""
                        observeSignUpViewModel()
                    }
                }
            }
        }

        private fun checkemail(): String? {
            val email = binding?.emailInputEditText?.text.toString()
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding?.emailInputLayout?.error = getString(R.string.invalid_email)
            }
            return null
        }

        private fun checkOTP(otp: String): Boolean {
//            Log.v("OTP Length",otp.length.toString())
            if (otp.length < 2) {
                binding?.otpInputLayout?.error = getString(R.string.fourdigit)
                return false
            }
            return true
        }

        private fun checkInputs(username: String): Boolean {
            if (username == "" || username.trim().isEmpty()) {
                binding?.userInputLayout?.error = getString(R.string.invalid_username)
                return false
            }
            return true
        }

        private fun isValidEmail(email: String): Boolean {
            if (!email.matches(emailPattern.toRegex())) {
                binding?.emailInputLayout?.error = getString(R.string.invalid_email)
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
                DataStatus.LOADING -> {
                    binding?.loginProgressBar?.visibility = View.VISIBLE
                }
                DataStatus.SUCCESS -> {
                    binding?.loginProgressBar?.visibility = View.GONE
                    if (mysur.data?.body() != null) {
                        showOTPUI()//If this is sucessfull then only proceed to list
                        startTimer()
                        binding?.verifyButton?.setOnClickListener {
                            val currentOTP = binding?.otpInputEditText?.text.toString()
                            if(!checkOTP(currentOTP)){
                                binding?.otpInputLayout?.error = getString(R.string.fourdigit)
                            }
                            else {
                                Toast.makeText(
                                    context, "User Signed Up Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                mysur.data!!.body()?.author?.let { user ->
                                    savedata(user)
                                }
                                findNavController().navigate(R.id.action_loginFragment_to_listTaskFragment)
                            }

                        }
                    }else if (mysur.data?.code() == 404) {
                        Toast.makeText(
                            context,
                            "Something Went Wrong Please Try Again",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (mysur.data?.code() == 400) {
                        Log.v("deee", mysur.data?.body()!!.errorMessage.toString())
                        binding?.emailInputLayout?.error = getString(R.string.invalid_email)
                    }
                }
                else -> {}
            }
        }

        private fun handleResponse(mlr: StateData<Response<LoginResponse>>?) {
            when (mlr?.status) {
                DataStatus.LOADING -> {
                    binding?.loginProgressBar?.visibility = View.VISIBLE
                }
                DataStatus.SUCCESS -> {
                    binding?.emailInputLayout?.isErrorEnabled = false
                    binding?.loginProgressBar?.visibility = View.GONE
                    if (mlr.data?.body() != null) {
                        showOTPUI()
                        startTimer()
                        binding?.verifyButton?.setOnClickListener {
                            val currentOTP = binding?.otpInputEditText?.text.toString()
                            if(!checkOTP(currentOTP)){
                                binding?.otpInputLayout?.error = getString(R.string.fourdigit)
                            }
                            else{
                                val currentUsername = binding?.emailInputEditText?.text.toString()
                                val currentUserOTP = binding?.otpInputEditText?.text.toString()
                                val currentLoginOTPRequest = LoginOTPRequest(currentUsername,currentUserOTP,false)
                                viewModel.loginUserByOTP(currentLoginOTPRequest)
                                observeLoginByOTPViewModel()
                            }
                        }
                    } else if (mlr.data?.code() == 404) {
                        Toast.makeText(
                            context,
                            getString(R.string.please_sign_up),
                            Toast.LENGTH_SHORT
                        ).show()
                        showSignUpUI()
                        signUpFlag = true
                    }
                }
                else -> {}
            }
        }

    private fun startTimer() {
        var cTimer = object : CountDownTimer(180000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding?.otptimer?.text = (millisUntilFinished / 1000).toString() + " sec   "
            }
            override fun onFinish() {
                     showNotReceivedOTPUI()
                      binding?.otptimer?.visibility = View.GONE
                      binding?.textResendOTP?.setOnClickListener{
                          disableNotReceivedOTPUI()
                          startTimer()
                      }
            }
        }
        binding?.otptimer?.visibility = View.VISIBLE
        cTimer.start()
    }



    private fun observeLoginViewModel() {
            viewModel.myLoginResponse?.observe(viewLifecycleOwner) {
                handleResponse(
                    it
                )
            }
        }

    private fun observeLoginByOTPViewModel(){
        viewModel.myLoginUserByOTPResponse.observe(viewLifecycleOwner){
            handleResponseLoginOtp(it)
        }
    }

//    private fun handleResponse(mlr: StateData<Response<LoginResponse>>?) {
//        when (mlr?.status) {
//            DataStatus.LOADING -> {
//                binding?.loginProgressBar?.visibility = View.VISIBLE
//            }
//            DataStatus.SUCCESS -> {
//                binding?.emailInputLayout?.isErrorEnabled = false
//                binding?.loginProgressBar?.visibility = View.GONE
//                if (mlr.data?.body() != null) {
//                    showOTPUI()
//                    startTimer()
//                    binding?.verifyButton?.setOnClickListener {
//                        val currentOTP = binding?.otpInputEditText?.text.toString()
//                        if(!checkOTP(currentOTP)){
//                            binding?.otpInputLayout?.error = getString(R.string.fourdigit)
//                        }
//                        else{
//                            //If OTP is valid then only execute this body
//                            Toast.makeText(
//                                context,
//                                getString(R.string.user_login_successful),
//                                Toast.LENGTH_SHORT
//                            ).show()
//                            mlr.data?.body()?.author?.let { user ->
//                                savedata(user)
//                            }
//                            findNavController().navigate(R.id.action_loginFragment_to_listTaskFragment)
//                        }
//                    }
//                } else if (mlr.data?.code() == 404) {
//                    Toast.makeText(
//                        context,
//                        getString(R.string.please_sign_up),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    showSignUpUI()
//                    signUpFlag = true
//                }
//            }
//            else -> {}
//        }
//    }

    private fun handleResponseLoginOtp(mlotpr: StateData<Response<LoginOTPResponse>>?) {

        when(mlotpr?.status)
        {
            DataStatus.LOADING -> {
                binding?.loginProgressBar?.visibility = View.VISIBLE
            }
            DataStatus.SUCCESS -> {
                binding?.loginProgressBar?.visibility = View.GONE
                if(mlotpr.data?.body() != null)
                {
                    if(mlotpr.data?.body()!!.isAuthenticated)
                    {
                        mlotpr.data?.body()?.author?.let { user ->
                                savedata(user)
                            }
                    }
                    Toast.makeText(
                        context,
                        getString(R.string.user_login_successful),
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().navigate(R.id.action_loginFragment_to_listTaskFragment)
                }
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

        private fun showOTPUI()
        {
            binding?.otpInputLayout?.visibility = View.VISIBLE
            binding?.loginButton?.visibility = View.GONE
            binding?.verifyButton?.visibility = View.VISIBLE
        }

        private fun showNotReceivedOTPUI()
        {
            binding?.notReceived?.visibility = View.VISIBLE
            binding?.textResendOTP?.visibility = View.VISIBLE
        }

        private fun disableNotReceivedOTPUI() {
            binding?.notReceived?.visibility = View.GONE
            binding?.textResendOTP?.visibility = View.GONE
        }

        override fun onDestroyView() {
            super.onDestroyView()
            binding = null
        }

}