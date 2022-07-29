package com.example.todotestapp.presentation

import android.app.PendingIntent.getActivity
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.todotestapp.R
import com.example.todotestapp.data.repository.Constants.IS_USER_LOGGED_IN
import com.example.todotestapp.data.repository.Constants.SHARED_PREFERENCES
import com.facebook.stetho.Stetho
import dagger.android.support.DaggerAppCompatActivity
import java.net.InetAddress
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

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

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v: View? = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm: InputMethodManager =
                        getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
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


    fun isInternetAvailable(): Boolean {
        return try {
            val ipAddr: InetAddress = InetAddress.getByName("google.com")
            //You can replace it with your name
            !ipAddr.equals("")
        } catch (e: Exception) {
            false
        }
    }





}