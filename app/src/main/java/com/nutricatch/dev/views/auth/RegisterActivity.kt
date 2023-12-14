package com.nutricatch.dev.views.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.nutricatch.dev.data.ResultState
import com.nutricatch.dev.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            register()
        }
    }

    private fun register() {
        val name = binding.namaEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        viewModel.register(name, email, password).observe(this) { result ->
            showLoading(true)
            if (result != null) {
                when (result) {
                    is ResultState.Success<*> -> {
                        showLoading(false)
                        AlertDialog.Builder(this).apply {
                            setTitle("You're Now Registered")
                            setMessage("You have been successfully registered to the app")
                            setPositiveButton("Next") { _, _ ->
                                val intent = Intent(context, LoginActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            }
                            create()
                            show()
                        }
                    }

                    is ResultState.Error -> {
                        showLoading(false)
                        AlertDialog.Builder(this).apply {
                            setTitle("Something is wrong")
                            setMessage("Error : " + result.error)
                            setNegativeButton("Try Again") { _, _ -> }
                            create()
                            show()
                        }
                    }

                    else -> {
                        /// handle loading
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}