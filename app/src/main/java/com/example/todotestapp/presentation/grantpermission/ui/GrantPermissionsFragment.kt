package com.example.todotestapp.presentation.grantpermission.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todotestapp.R
import com.example.todotestapp.data.db.AddAdminRequest
import com.example.todotestapp.data.db.AddAdminResponse
import com.example.todotestapp.data.db.LoginOTPErrorResponse
import com.example.todotestapp.data.db.StateData
import com.example.todotestapp.data.repository.Constants
import com.example.todotestapp.databinding.FragmentGrantPermissionsListDialogBinding
import com.example.todotestapp.domain.repositoryinterface.ToDoRepository
import com.example.todotestapp.presentation.MainActivity
import com.example.todotestapp.presentation.grantpermission.viewmodel.GrantPermissionViewModel
import com.example.todotestapp.presentation.grantpermission.viewmodel.GrantPermissionViewModelFactory
import com.example.todotestapp.presentation.logIn.viewmodel.LoginViewModel
import com.example.todotestapp.presentation.logIn.viewmodel.LoginViewModelFactory
import com.google.gson.Gson
import dagger.android.support.DaggerFragment
import retrofit2.Response
import javax.inject.Inject


class GrantPermissionsFragment : DaggerFragment() {


    @Inject
    lateinit var viewModelFactory: GrantPermissionViewModelFactory

    @Inject
    lateinit var repository : ToDoRepository

    @Inject
    lateinit var sharedPreferences : SharedPreferences

    private lateinit var viewModel: GrantPermissionViewModel
    private var grantPermissionUserId: Int = -1
    private var grantPermissionUserRole: String = ""
    private var revokeFlag = false

    private var _binding: FragmentGrantPermissionsListDialogBinding? = null
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentGrantPermissionsListDialogBinding.inflate(inflater, container, false)
        return binding.root

    }


    private fun setUpClickListeners() {
        binding.approveButton.setOnClickListener{
            var currentGrantPermissionEmail = binding.emailPermissionInputEditText.text.toString()
            if(grantPermissionInputCheck(currentGrantPermissionEmail))
            {
                val addAdminRequest = AddAdminRequest(currentGrantPermissionEmail,true)
                revokeFlag = false
                viewModel.addAdminFun(grantPermissionUserId,addAdminRequest)
                Toast.makeText(
                    context,"Email is Valid",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.revokeButton.setOnClickListener{
            var currentGrantPermissionEmail = binding.emailPermissionInputEditText.text.toString()
            if(grantPermissionInputCheck(currentGrantPermissionEmail))
            {
                val addAdminRequest = AddAdminRequest(currentGrantPermissionEmail,false)
                revokeFlag = true
                viewModel.addAdminFun(grantPermissionUserId,addAdminRequest)
                Toast.makeText(
                    context,"Email is Valid",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun grantPermissionInputCheck(currentGrantPermissionEmail: String): Boolean {

        var grantPermissionInputCheckFlag = true
        if(!isValidEmail(currentGrantPermissionEmail))
        {
            binding.emailPermissionInputLayout.helperText = getString(R.string.inve)
            grantPermissionInputCheckFlag = false
        }
        else
        {
            binding.emailPermissionInputLayout.helperText = " "
        }
        return grantPermissionInputCheckFlag
    }

    private fun isValidEmail(email: String): Boolean {
        if (!email.matches(emailPattern.toRegex())) {
            return false
        }
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this, viewModelFactory)[GrantPermissionViewModel::class.java]
        checkForUserLocalData()
        setUpClickListeners()
        observeLiveData()
    }

    private fun observeLiveData() {
        observeGrantPermissionViewModel()
    }

    private fun observeGrantPermissionViewModel() {
        viewModel.myAddAdminResponse.observe(viewLifecycleOwner){
            handleAddAdminResponse(it)
        }
    }

    private fun handleAddAdminResponse(addAdminFragResponse: StateData<Response<AddAdminResponse>>?) {

        when(addAdminFragResponse?.status)
        {
            StateData.DataStatus.LOADING -> {
                binding.grantProgressBar.visibility = View.VISIBLE
            }
            StateData.DataStatus.SUCCESS -> {
                binding.grantProgressBar.visibility = View.GONE
                if(addAdminFragResponse.data?.body() != null)
                {
                    if(!revokeFlag) {
                        Toast.makeText(
                            context,
                            "Added Admin Access SuccessFully",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else if(revokeFlag){
                        Toast.makeText(
                            context,
                            "Revoked Admin Access SuccessFully",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    findNavController().navigate(R.id.action_grantPermissionsFragment_to_listTaskFragment)
                }
                else if (addAdminFragResponse.data?.code() == 404) {
                    Toast.makeText(context, "User Not Found",Toast.LENGTH_SHORT).show()
                }
            }
            else -> {}
        }


    }

    private fun checkForUserLocalData() {
        sharedPreferences.let {
            if (sharedPreferences.contains(Constants.ID)) {
                grantPermissionUserId = sharedPreferences.getInt(Constants.ID,-1)
                grantPermissionUserRole = sharedPreferences.getString(Constants.ROLE,"").toString()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}