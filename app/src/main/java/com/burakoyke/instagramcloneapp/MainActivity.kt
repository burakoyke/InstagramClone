package com.burakoyke.instagramcloneapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.burakoyke.instagramcloneapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val i = Intent(this@MainActivity, FeedActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    fun signInClick(view: View) {
        val mail = binding.editMail.text.toString()
        val password = binding.editPassword.text.toString()

        if (mail.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(mail, password).addOnSuccessListener {
                val i = Intent(this@MainActivity, FeedActivity::class.java)
                startActivity(i)
            }.addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Enter mail and password", Toast.LENGTH_LONG).show()
        }
    }

    fun signUpClick(view: View) {
        val mail = binding.editMail.text.toString()
        val password = binding.editPassword.text.toString()

        if (mail.isNotEmpty() && password.isNotEmpty()) {

            auth.createUserWithEmailAndPassword(mail, password).addOnSuccessListener {
                val i = Intent(this@MainActivity, FeedActivity::class.java)
                startActivity(i)
            }.addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
            }

        } else {
            Toast.makeText(this, "Enter mail and password", Toast.LENGTH_LONG).show()
        }

    }

}