package com.bangkit.cekulit.ui.auth.reset.reset

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.cekulit.databinding.ActivityResetPasswordBinding
import com.bangkit.cekulit.ui.auth.login.LoginActivity
import com.bangkit.cekulit.ui.auth.reset.otp.OtpActivity
import com.bangkit.cekulit.ui.auth.reset.otp.OtpActivity.Companion.EMAIL_FORGET

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResetPasswordBinding
    private val resetPasswordViewModel by viewModels<ResetPasswordViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObserver()

        binding.btnContinue.setOnClickListener {
            val email = intent.getStringExtra(EMAIL_FORGET)
            val password = binding.edPassword.text.toString()
            Log.e("CLICKED", email + password)
            resetPasswordViewModel.resetPassword(email!!, password)
            resetPasswordViewModel.isSuccess.observe(this){
                showSuccessDialog(it.message!!)
            }
        }
    }

    private fun showLoading(isLoading: Boolean){
        if(isLoading) binding.progressIndicator.visibility = View.VISIBLE else binding.progressIndicator.visibility = View.GONE
    }

    private fun showButton(isEnabled: Boolean){
        binding.btnContinue.isEnabled = !isEnabled
    }

    private fun setupObserver(){
        resetPasswordViewModel.isLoading.observe(this){
            showLoading(it)
            showButton(it)
        }

        resetPasswordViewModel.responseResetPw.observe(this){
            showErrorDialog(it)
        }
    }

    private fun showSuccessDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Yeay!")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                moveLoginActivity()
            }
            .setCancelable(false)
            .create()
            .show()
    }

    private fun showErrorDialog(message: String){
        AlertDialog.Builder(this)
            .setTitle("Oops!")
            .setMessage(message)
            .setPositiveButton("OK"){ dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun moveLoginActivity(){
        val intent = Intent(this@ResetPasswordActivity, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish()
    }
}