package com.example.utsp3b

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.utsp3b.MainActivity.Companion.EXTRA_PASSWORD
import com.example.utsp3b.MainActivity.Companion.EXTRA_USERNAME
import com.example.utsp3b.databinding.ActivityMain2Binding
import com.example.utsp3b.databinding.ActivityMainBinding

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val username = intent.getStringExtra(EXTRA_USERNAME)
        val password = intent.getStringExtra(EXTRA_PASSWORD)

        with(binding) {
                loginBtn.setOnClickListener {
                    val loginUser = loginUsername.text.toString()
                    val loginPass = loginPassword.text.toString()
                    if(loginUser == username && loginPass == password){
                        val intentToThirdActivity = Intent(this@MainActivity2, MainActivity3::class.java)
                        startActivity(intentToThirdActivity)
                    }
                    else{
                        Toast.makeText(this@MainActivity2,"Username atau password salah",Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}