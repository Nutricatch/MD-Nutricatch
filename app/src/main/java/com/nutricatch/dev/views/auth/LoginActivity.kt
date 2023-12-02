package com.nutricatch.dev.views.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.nutricatch.dev.databinding.ActivityLoginBinding
import com.nutricatch.dev.views.navigation.HomeActivity

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btnLogin -> {
                /// TODO handle login login

                val intent = Intent(this, HomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }
        }
    }
}