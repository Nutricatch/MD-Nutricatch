package com.nutricatch.dev.views.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.nutricatch.dev.data.repository.Result
import com.nutricatch.dev.databinding.ActivityLoginBinding
import com.nutricatch.dev.views.navigation.HomeActivity

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btnLogin -> {
                login()
            }
        }
    }
    private fun login()
    {
        /// TODO handle login login
        val email = binding.emailEt.text.toString()
        val password = binding.passwordEt.text.toString()
        viewModel.login(email, password).observe(this){result->
            showLoading(true)
            if (result!= null){
                when(result) {
                    is Result.Success<*> ->{
                        showLoading(false)
                    }

                    is Result.Error ->{
                        showLoading(false)
                        AlertDialog.Builder(this).apply {
                            setTitle("Something is Wrong")
                            setMessage(result.error)
                            setNegativeButton("Redo"){_,_ ->
                            }
                            create()
                            show()
                        }
                    }
                    else ->{

                    }
                }
            }
        }
    }
    private fun showLoading(isLoading: Boolean){
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}