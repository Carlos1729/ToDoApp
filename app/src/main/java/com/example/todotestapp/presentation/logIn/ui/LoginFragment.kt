package com.example.todotestapp.presentation.logIn.ui

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todotestapp.R
import com.example.todotestapp.data.db.*
import com.example.todotestapp.data.db.StateData.DataStatus
import com.example.todotestapp.data.repository.Constants.EMAIL
import com.example.todotestapp.data.repository.Constants.ID
import com.example.todotestapp.data.repository.Constants.IS_USER_LOGGED_IN
import com.example.todotestapp.data.repository.Constants.ROLE
import com.example.todotestapp.data.repository.Constants.SELECTED_PRIORITY
import com.example.todotestapp.data.repository.Constants.SELECTED_SORT
import com.example.todotestapp.data.repository.Constants.SELECTED_STATUS
import com.example.todotestapp.data.repository.Constants.SHARED_PREFERENCES
import com.example.todotestapp.data.repository.Constants.USER_NAME
import com.example.todotestapp.databinding.FragmentLoginBinding
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import com.example.todotestapp.presentation.MainActivity
import com.example.todotestapp.presentation.logIn.viewmodel.LoginViewModel
import com.example.todotestapp.presentation.logIn.viewmodel.LoginViewModelFactory
import com.google.gson.Gson
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
    val gson = Gson()
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
        obsereLiveData()
        setUpClickListeners()

    }

    private fun obsereLiveData() {
        observeLoginViewModel()
        observeLoginByOTPViewModel()
        observeSignUpByOTPViewModel()
    }

    private fun setUpClickListeners() {

        binding?.loginButton?.setOnClickListener {
            val useremail = binding?.emailInputEditText?.text.toString()
            if (isValidEmail(useremail)) {
                viewModel.loginUser(useremail)
//                startTimer()
            } else {
                binding?.emailInputLayout?.error = getString(R.string.invalid_email)
            }
        }

        binding?.textResendOTP?.setOnClickListener{
            val useremail = binding?.emailInputEditText?.text.toString()
            viewModel.loginUser(useremail)
            disableNotReceivedOTPUI()
//            startTimer()
        }

        binding?.verifyButtonLogin?.setOnClickListener {
            val currentLoginUserEmail = binding?.emailInputEditText?.text.toString()
            val currentLoginUserOTP = binding?.otpInputEditText?.text.toString()
            if(loginInputCheck(currentLoginUserEmail,currentLoginUserOTP))
            {
                val currentLoginOTPRequest = LoginOTPRequest(currentLoginUserEmail,currentLoginUserOTP,false)
                viewModel.loginUserByOTP(currentLoginOTPRequest)
            }
        }

        binding?.verifyButton?.setOnClickListener {
            val currentSignUpUserEmail = binding?.emailInputEditText?.text.toString()
            val currentSignUpUserName = binding?.usernameInputEditText?.text.toString()
            val currentSignUpUserOTP = binding?.otpInputEditText?.text.toString()
            if(signUpInputCheck(currentSignUpUserEmail,currentSignUpUserName,currentSignUpUserOTP)) {
                    val currentSignUpOTPRequest = SignUpOTPRequest(currentSignUpUserEmail,currentSignUpUserName,currentSignUpUserOTP,true)
                    viewModel.signUpUserByOTP(currentSignUpOTPRequest)
            }
        }

    }

    private fun signUpInputCheck(currentSignUpUserEmail: String, currentSignUpUserName: String, currentSignUpUserOTP: String): Boolean {

        var signUpInputCheckFlag = true
        if(!isValidEmail(currentSignUpUserEmail))
        {
            binding?.emailInputLayout?.helperText = getString(R.string.inve)
            signUpInputCheckFlag = false
        }
        else
        {
            binding?.emailInputLayout?.helperText = " "
        }
        if(!checkOTP(currentSignUpUserOTP))
        {
            binding?.otpInputLayout?.helperText = getString(R.string.fourdigit)
            signUpInputCheckFlag = false
        }
        else
        {
            binding?.otpInputLayout?.helperText = " "
        }
        if(!checkOTP(currentSignUpUserName))
        {
            binding?.userInputLayout?.helperText = getString(R.string.invalid_username)
            signUpInputCheckFlag = false
        }
        else
        {
            binding?.userInputLayout?.helperText = " "
        }
        return signUpInputCheckFlag
    }

    private fun loginInputCheck(currentLoginUserEmail: String, currentLoginUserOTP: String): Boolean {

        var loginInputCheckFlag = true
        if(!isValidEmail(currentLoginUserEmail))
        {
            binding?.emailInputLayout?.helperText = getString(R.string.inve)
            loginInputCheckFlag = false
        }
        else
        {
            binding?.emailInputLayout?.helperText = " "
        }
        if(!checkOTP(currentLoginUserOTP))
        {
            binding?.otpInputLayout?.helperText = getString(R.string.fourdigit)
            loginInputCheckFlag = false
        }
        else
        {
            binding?.otpInputLayout?.helperText = " "
        }

        return loginInputCheckFlag
    }


    // Normal Login
    private fun observeLoginViewModel() {
        viewModel.myLoginResponse?.observe(viewLifecycleOwner) {
            handleResponse(
                it
            )
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
                    Toast.makeText(
                        context,"An OTP has been Sent Please Check",
                        Toast.LENGTH_SHORT
                    ).show()
                    startTimer()
                } else if (mlr.data?.code() == 404) {
                    if(!signUpFlag)
                    {
                        Toast.makeText(
                            context,
                            getString(R.string.please_sign_up),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    signUpFlag = true
                    (activity as MainActivity).supportActionBar?.title = "Sign Up"
                    showOTPUI()
                    startTimer()
                    Toast.makeText(
                        context,"An OTP has been Sent Please Check",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            else -> {}
        }
    }


    //Login By OTP
    private fun observeLoginByOTPViewModel(){
        viewModel.myLoginUserByOTPResponse.observe(viewLifecycleOwner){
            handleResponseLoginOtp(it)
        }
    }
    private fun handleResponseLoginOtp(otpResponse: StateData<Response<LoginOTPResponse>>?) {

        when (otpResponse?.status) {
            DataStatus.LOADING -> {
                binding?.loginProgressBar?.visibility = View.VISIBLE
            }
            DataStatus.SUCCESS -> {
                binding?.loginProgressBar?.visibility = View.GONE
                if (otpResponse.data?.body() != null) {
                    if (otpResponse.data?.body()!!.isAuthenticated) {
                        otpResponse.data?.body()?.author?.let { user ->
                            savedata(user)
                        }
                    }
                    Toast.makeText(
                        context,
                        getString(R.string.user_login_successful),
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().navigate(R.id.action_loginFragment_to_listTaskFragment)
                } else if (otpResponse.data?.code() == 400) {
                    val errorRes = Gson().fromJson(otpResponse.data?.errorBody()?.string(),LoginOTPErrorResponse::class.java)
                    Toast.makeText(context, errorRes.errorMessage,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



    //Sign Up By OTP
    private fun observeSignUpByOTPViewModel() {
        viewModel.mySignUpUserByOTPResponse.observe(viewLifecycleOwner){
            handleResponseSignUpOtp(it)
        }
    }
    private fun handleResponseSignUpOtp(signUpOtpResponse: StateData<Response<SignUpOTPResponse>>?) {
        when(signUpOtpResponse?.status)
        {
            DataStatus.LOADING->{
                binding?.loginProgressBar?.visibility = View.VISIBLE
            }
            DataStatus.SUCCESS -> {
                binding?.loginProgressBar?.visibility = View.GONE
                if(signUpOtpResponse.data?.body() != null)
                {
                    if(signUpOtpResponse.data?.body()!!.isAuthenticated){
                        signUpOtpResponse.data?.body()?.author?.let { user ->
                            savedata(user)
                        }
                    }
                    Toast.makeText(
                        context, getString(R.string.usus),
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().navigate(R.id.action_loginFragment_to_listTaskFragment)
                }
                else if(signUpOtpResponse.data?.code() == 400){
                    val errorRes = Gson().fromJson(signUpOtpResponse.data?.errorBody()?.string(),SignUpOtpErrorResponse::class.java)
                    Toast.makeText(context, errorRes.errorMessage,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



    private fun startTimer() {
        var cTimer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding?.otptimer?.text = (millisUntilFinished / 1000).toString() + " sec   "
            }
            override fun onFinish() {
                     showNotReceivedOTPUI()
                      binding?.otptimer?.visibility = View.GONE
            }
        }
        binding?.otptimer?.visibility = View.VISIBLE
        cTimer.start()
    }


    private fun savedata(userDetails: UserDetails) {
        val sharedPreferences = activity!!.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply{
            putString(EMAIL,userDetails.email)
            putString(USER_NAME,userDetails.name)
            putInt(ID,userDetails.id)
            putString(ROLE,userDetails.role)
            putBoolean(IS_USER_LOGGED_IN, true)
            putInt(SELECTED_STATUS,0)
            putInt(SELECTED_PRIORITY,0)
            putInt(SELECTED_SORT,0)
        }.apply()
    }



    //Set UP UI

        private fun showOTPUI() {
            binding?.otpInputLayout?.visibility = View.VISIBLE
            binding?.loginButton?.visibility = View.GONE
            if(signUpFlag) {
                binding?.verifyButtonLogin?.visibility = View.GONE
                binding?.verifyButton?.visibility = View.VISIBLE
                binding?.userInputLayout?.visibility = View.VISIBLE
                binding?.verifyButton?.text = "Verify & SignUp"
            }
            else {
                binding?.verifyButton?.visibility = View.GONE
                binding?.verifyButtonLogin?.visibility = View.VISIBLE
            }
        }
        private fun showNotReceivedOTPUI() {
            binding?.notReceived?.visibility = View.VISIBLE
            binding?.textResendOTP?.visibility = View.VISIBLE
        }
        private fun disableNotReceivedOTPUI() {
            binding?.notReceived?.visibility = View.GONE
            binding?.textResendOTP?.visibility = View.GONE
        }

    //Input Checks
    private fun checkemail(): String? {
        val email = binding?.emailInputEditText?.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding?.emailInputLayout?.error = getString(R.string.invalid_email)
        }
        return null
    }
    private fun checkOTP(otp: String): Boolean {
        if (otp.length < 2 || otp.length > 7) {
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

    //Destroy Binding
    override fun onDestroyView() {
            super.onDestroyView()
            binding = null
        }

}


