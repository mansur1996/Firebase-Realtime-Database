package com.example.firbaseauthwithgmail.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.firbaseauthwithgmail.model.User
import com.example.firbaseauthwithgmail.databinding.ActivityDashboardBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseError

import com.google.firebase.database.DataSnapshot

import com.google.firebase.database.ValueEventListener
import java.lang.StringBuilder

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val TAG = "DashboardActivity"

    //NoSql database
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser

        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("user")

        addUser(currentUser)
        getAllUsers()

        binding.tvId.text = currentUser?.uid
        binding.tvName.text = currentUser?.displayName
        binding.tvEmail.text = currentUser?.email
        Glide.with(this).load(currentUser?.photoUrl).into(binding.ivProfile);

        binding.btnSignOut.setOnClickListener {
            firebaseAuth.signOut()
            val signInIntent = Intent(this, SignInActivity::class.java)
            startActivity(signInIntent)
            finish()
        }

    }

    private fun getAllUsers() {
        // Read from the database
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val stringBuilder = StringBuilder()
                val children = snapshot.children
                for (child in children){
                    val value = child.getValue(User::class.java)
                    stringBuilder.append(value?.displayName + "\n")
                    Log.d(TAG, "Value is: ${value?.displayName}")
                }

                binding.tvUsers.text = stringBuilder.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    private fun addUser(currentUser: FirebaseUser?) {
        val uid = currentUser?.uid
        val photoUrl= currentUser?.photoUrl
        val displayName = currentUser?.displayName
        val email = currentUser?.email
        val phoneNumber = currentUser?.phoneNumber

        val user = User(uid, photoUrl.toString(), displayName, email, phoneNumber)
        reference.child(uid!!).setValue(user)
    }


}