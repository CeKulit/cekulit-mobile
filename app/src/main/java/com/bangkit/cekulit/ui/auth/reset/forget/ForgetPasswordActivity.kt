package com.bangkit.cekulit.ui.auth.reset.forget

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.cekulit.databinding.ActivityForgetPasswordBinding
import com.bangkit.cekulit.ui.auth.reset.otp.OtpActivity
import com.bangkit.cekulit.ui.auth.reset.otp.OtpActivity.Companion.EMAIL_FORGET

class ForgetPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgetPasswordBinding
    private val forgetPasswordViewModel by viewModels<ForgetPasswordViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObserver()

        binding.btnContinue.setOnClickListener {
            val email = binding.edEmail.text.toString()
            forgetPasswordViewModel.forgetPassword(email)
            forgetPasswordViewModel.isSuccess.observe(this){
                showSuccessDialog(it.message!!, email)
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
        forgetPasswordViewModel.isLoading.observe(this){
            showLoading(it)
            showButton(it)
        }

        forgetPasswordViewModel.responseForgetPw.observe(this){
            showErrorDialog(it)
        }
    }

    private fun showSuccessDialog(message: String, bundle: String) {
        AlertDialog.Builder(this)
            .setTitle("Yeay!")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                moveActivity(bundle)
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

    private fun moveActivity(bundle: String){
        val intent = Intent(this@ForgetPasswordActivity, OtpActivity::class.java)
        intent.putExtra(EMAIL_FORGET, bundle)
        startActivity(intent)
    }
}