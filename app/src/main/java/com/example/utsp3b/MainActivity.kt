package com.example.utsp3b

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.utsp3b.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    companion object{
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_PASSWORD = "extra_password"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            registerBtn.setOnClickListener {
                val yearOfBirth = year.text.toString()

                if (yearOfBirth.isEmpty() || yearOfBirth.length != 4) {
                    year.error = "Tahun lahir harus dalam format yyyy"
                    return@setOnClickListener
                }

                val currentYear = Calendar.getInstance().get(Calendar.YEAR)

                val userYearOfBirth = yearOfBirth.toInt()

                val userAge = currentYear - userYearOfBirth

                if (userAge < 15) {
                    year.error = "Anda harus berusia minimal 15 tahun untuk mendaftar."
                    return@setOnClickListener
                }

                val username = username.text.toString()
                val password = password.text.toString()

                val intentToSecondActivity = Intent(this@MainActivity, MainActivity2::class.java)
                intentToSecondActivity.putExtra(EXTRA_USERNAME, username)
                intentToSecondActivity.putExtra(EXTRA_PASSWORD, password)
                startActivity(intentToSecondActivity)
            }
        }
    }
}
