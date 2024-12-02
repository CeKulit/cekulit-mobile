package com.bangkit.cekulit.ui.auth.signup

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.cekulit.databinding.ActivitySignupBinding
import com.bangkit.cekulit.ui.auth.login.LoginActivity

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val signupViewModel by viewModels<SignupViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAction()
        setupSignupObserver()
        supportActionBar?.hide()



        binding.tvRedirect.setOnClickListener {
            val intent = Intent(this@SignupActivity, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            finish()
        }

    }


    private fun setupAction() {
        binding.btnSignup.setOnClickListener {
            val name = binding.edSignupName.text.toString()
            val email = binding.edSignupEmail.text.toString()
            val password = binding.edSignupPassword.text.toString()

            signupViewModel.signupUser(name, email, password)

            signupViewModel.isSuccess.observe(this) { response ->
                showSuccessDialog(response.message!!)
            }
        }
    }


    private fun moveToLogin(){
        val intent = Intent(this@SignupActivity, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish()

    }

    private fun showSuccessDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Yeay!")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                moveToLogin()
            }
            .setCancelable(false)
            .show()
    }



    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this).apply {
            setTitle("Kesalahan")
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
        binding.btnSignup.isEnabled = !isEnabled
    }

    private fun setupSignupObserver(){
        signupViewModel.isLoading.observe(this){
//            showLoading(it)
            showButton(it)
        }

        signupViewModel.responseSignup.observe(this){
            showErrorDialog(it)
        }
    }
}