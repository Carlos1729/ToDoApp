package com.example.todotestapp.presentation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.todotestapp.R
import com.example.todotestapp.data.repository.Constants.IS_USER_LOGGED_IN
import com.example.todotestapp.data.repository.Constants.SHARED_PREFERENCES
import com.facebook.stetho.Stetho

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var  navHostFragment : NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Stetho.initializeWithDefaults(this)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.naviHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        if(checkForLoginSignUpFlow()){
            navigateToListScreen()
        }
        else{
            navigateToSignUpLoginFlow()
        }
    }

    private fun navigateToSignUpLoginFlow() {
    }

    private fun navigateToListScreen() {
        val graph = navController.navInflater.inflate(R.navigation.navi_graph)
        graph.setStartDestination(R.id.listTaskFragment)
        navController.setGraph(graph,null)
    }

    private fun checkForLoginSignUpFlow() : Boolean {
        val sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
      return sharedPreferences.contains(IS_USER_LOGGED_IN) && sharedPreferences.getBoolean(
                IS_USER_LOGGED_IN,false)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.naviHostFragment)
       return navController.navigateUp() || super.onSupportNavigateUp()
}





}