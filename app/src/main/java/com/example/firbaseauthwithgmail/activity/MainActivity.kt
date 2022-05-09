package com.example.firbaseauthwithgmail.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.firbaseauthwithgmail.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser

        /**If user is not authenticated, send him/her to SignInActivity to authenticate first
        Else send him/her to DashboardActivity*/


        /****** Create Thread that will sleep for 5 seconds */
        val background: Thread = object : Thread() {
            override fun run() {
                try {
                    // Thread will sleep for 5 seconds
                    sleep((2 * 1000).toLong())

                    // After 5 seconds redirect to another intent
                    if(user != null){
                        val dashboardIntent = Intent(baseContext, DashboardActivity::class.java)
                        startActivity(dashboardIntent)
                        finish()
                    }else{
                        val signInIntent = Intent(baseContext, SignInActivity::class.java)
                        startActivity(signInIntent)
                        finish()
                    }
                    //Remove activity
                    finish()
                } catch (e: Exception) {
                }
            }
        }
        // start thread
        background.start()
    }
}
