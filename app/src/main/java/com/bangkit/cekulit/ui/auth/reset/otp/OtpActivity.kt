package com.bangkit.cekulit.ui.auth.reset.otp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.cekulit.R
import com.bangkit.cekulit.databinding.ActivityOtpBinding
import com.bangkit.cekulit.ui.auth.login.LoginActivity
import com.bangkit.cekulit.ui.auth.signup.SignupActivity
import com.bangkit.cekulit.ui.auth.signup.SignupActivity.Companion.EMAIL_USER

class OtpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpBinding
    private val otpViewModel by viewModels<OtpViewModel>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObserver()

        val email = intent.getStringExtra(EMAIL_USER)

        binding.tvTitleSubOtp.text = getString(R.string.tv_title_sub_otp) + email

        binding.btnContinue.setOnClickListener {
            binding.apply {
                val otp = edOtp1.text.toString() + edOtp2.text.toString() + edOtp3.text.toString() + edOtp4.text.toString()
                if (otp.length == 4){
                    otpViewModel.insertOtpUser(email!!, otp)
                } else {
                    showErrorDialog("Otp are required.")
                }
            }
        }

        otpViewModel.isSuccess.observe(this){ response ->
            showSuccessDialog(response.message!!)
        }

        otpViewModel.responseOtp.observe(this){ responseMessage ->
            showErrorDialog(responseMessage)
        }

    }

    private fun moveActivity(){
        val intent = Intent(this@OtpActivity, LoginActivity::class.java).apply {
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
                moveActivity()
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


    private fun showLoading(isLoading: Boolean){
        if(isLoading) binding.progressIndicator.visibility = View.VISIBLE else binding.progressIndicator.visibility = View.GONE
    }

    private fun showButton(isEnabled: Boolean){
        binding.btnContinue.isEnabled = !isEnabled
    }

    private fun setupObserver(){
        otpViewModel.isLoading.observe(this){
            showLoading(it)
            showButton(it)
        }
    }
}