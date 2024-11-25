package com.dicoding.cekulit.ui.auth.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.cekulit.MainActivity
import com.dicoding.cekulit.databinding.ActivityLoginBinding
import com.dicoding.cekulit.ui.ViewModelFactory
import com.dicoding.cekulit.ui.auth.signup.SignupActivity


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
        supportActionBar?.hide()

        binding.tvRedirect.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignupActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            finish()

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

//    private fun showLoading(isLoading: Boolean){
//        if(isLoading) binding.progressBar.visibility = View.VISIBLE else binding.progressBar.visibility = View.GONE
//    }

    private fun showButton(isEnabled: Boolean){
        binding.btnLogin.isEnabled = !isEnabled
    }

    private fun moveActivity(){
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun setupLoginObserver(){
        loginViewModel.isLoading.observe(this){
//            showLoading(it)
            showButton(it)
        }

        loginViewModel.authToken.observe(this){ token ->
            if(token.isNotEmpty()){
                moveActivity()
            }
        }

        loginViewModel.responseLogin.observe(this) {
            setupErrorDialog(it)
        }

    }


}