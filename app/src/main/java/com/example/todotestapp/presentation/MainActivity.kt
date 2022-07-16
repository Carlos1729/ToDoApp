package com.example.todotestapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.todotestapp.presentation.logIn.ui.LoginFragment
import com.facebook.stetho.Stetho

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Stetho.initializeWithDefaults(this)

//        var loginFragment = LoginFragment()
//        loginFragment.show(supportFragmentManager, "TAG")
//        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
//        if(sharedPreferences.contains("EMAILID"))
//        {
//
//        }


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.naviHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)


    }

   override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.naviHostFragment)
       return navController.navigateUp() || super.onSupportNavigateUp()
}





}