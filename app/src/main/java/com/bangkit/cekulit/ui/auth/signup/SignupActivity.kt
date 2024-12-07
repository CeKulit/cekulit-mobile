package com.bangkit.cekulit.ui.auth.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.cekulit.R
import com.bangkit.cekulit.databinding.ActivitySignupBinding
import com.bangkit.cekulit.ui.auth.login.LoginActivity
import com.bangkit.cekulit.ui.auth.reset.otp.OtpActivity

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val signupViewModel by viewModels<SignupViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAction()
        setupSignupObserver()

        binding.btnSignup.isEnabled = false

        binding.cbTermsCondition.setOnCheckedChangeListener { _, isChecked ->
            binding.btnSignup.isEnabled = isChecked
        }

        binding.tvTermsConditionRedirect.setOnClickListener {
            showDialog(
                getString(R.string.tv_title_terms_condition),
                getString(R.string.tv_title_sub_terms_condition)
            )
        }

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


    private fun moveActivity(email: String){
        val intent = Intent(this@SignupActivity, OtpActivity::class.java)
        intent.putExtra(EMAIL_USER, email)
        startActivity(intent)
    }

    private fun showSuccessDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Yeay!")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                val email = binding.edSignupEmail.text.toString()
                moveActivity(email)
            }
            .setCancelable(false)
            .create()
            .show()
    }

    private fun showDialog(title: String, message: String){
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK"){ dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }


    private fun showLoading(isLoading: Boolean){
        if(isLoading) binding.progressIndicator.visibility = View.VISIBLE else binding.progressIndicator.visibility = View.GONE
    }

    private fun showButton(isEnabled: Boolean){
        binding.btnSignup.isEnabled = !isEnabled
    }

    private fun setupSignupObserver(){
        signupViewModel.isLoading.observe(this){
            showLoading(it)
            showButton(it)
        }

        signupViewModel.responseSignup.observe(this){
            showDialog("Oops!", it)
        }
    }

    companion object {
        const val EMAIL_USER = "email"
    }
}