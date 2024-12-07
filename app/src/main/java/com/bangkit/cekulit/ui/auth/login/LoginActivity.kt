package com.bangkit.cekulit.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.cekulit.ui.main.MainActivity
import com.bangkit.cekulit.databinding.ActivityLoginBinding
import com.bangkit.cekulit.ui.ViewModelFactory
import com.bangkit.cekulit.ui.auth.reset.ForgetPasswordActivity
import com.bangkit.cekulit.ui.auth.signup.SignupActivity


class LoginActivity : AppCompatActivity() {
    private val loginViewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
        setupLoginObserver()

        loginViewModel.authToken.observe(this){ token ->
            if(token.isNotEmpty()){
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }
        }

        binding.tvRedirect.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignupActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            finish()
        }


        binding.tvForgotPassword.setOnClickListener {
            startActivity(Intent(this@LoginActivity, ForgetPasswordActivity::class.java))
        }
    }


    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()
            loginViewModel.loginUser(email, password)

            loginViewModel.userLogin.observe(this) {
                val token = it.token.toString()
                loginViewModel.saveSession(token)
            }
        }
    }

    private fun setupErrorDialog(message: String) {
        AlertDialog.Builder(this).apply {
            setTitle("Oops!")
            setMessage(message)
            setPositiveButton("OK", null)
            create()
            show()
        }
    }

    private fun showLoading(isLoading: Boolean){
        if(isLoading) binding.progressIndicator.visibility = View.VISIBLE else binding.progressIndicator.visibility = View.GONE
    }

    private fun showButton(isEnabled: Boolean){
        binding.btnLogin.isEnabled = !isEnabled
    }

    private fun setupLoginObserver(){
        loginViewModel.isLoading.observe(this){
            showLoading(it)
            showButton(it)
        }

        loginViewModel.responseLogin.observe(this) {
            setupErrorDialog(it)
        }

    }

}