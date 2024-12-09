package com.bangkit.cekulit.ui.auth.reset.otp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.cekulit.R
import com.bangkit.cekulit.databinding.ActivityOtpBinding
import com.bangkit.cekulit.ui.auth.login.LoginActivity
import com.bangkit.cekulit.ui.auth.reset.reset.ResetPasswordActivity
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

        val emailUser = intent.getStringExtra(EMAIL_USER)
        val emailForget = intent.getStringExtra(EMAIL_FORGET)


        if (emailForget != null) {
            binding.tvTitleSubOtp.text = getString(R.string.tv_title_sub_otp) + emailForget
            Log.e("OTPACTIVITYFORGET", emailForget)
        } else {
            binding.tvTitleSubOtp.text = getString(R.string.tv_title_sub_otp) + emailUser
            Log.e("OTPACTIVITYUSER", emailUser!!)
        }

        binding.btnContinue.setOnClickListener {
            binding.apply {
                val otp = edOtp1.text.toString() + edOtp2.text.toString() + edOtp3.text.toString() + edOtp4.text.toString()
                if (otp.length == 4){
                    if (emailForget != null){
                        otpViewModel.insertOtpUser(emailForget, otp)
                    } else {
                        otpViewModel.insertOtpUser(emailUser!!, otp)
                    }
                } else {
                    showErrorDialog("Otp are required.")
                }
            }
        }

        otpViewModel.isSuccess.observe(this){ response ->
            showSuccessDialog(response.message!!, emailForget!!)
        }

        otpViewModel.responseOtp.observe(this){ responseMessage ->
            showErrorDialog(responseMessage)
        }

    }

    private fun moveLoginActivity(){
        val intent = Intent(this@OtpActivity, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish()
    }

    private fun moveResetActivity(bundle: String){
        val intent = Intent(this@OtpActivity, ResetPasswordActivity::class.java).apply {
            putExtra(EMAIL_FORGET, bundle)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish()
    }

    private fun showSuccessDialog(message: String, bundle: String) {
        AlertDialog.Builder(this)
            .setTitle("Yeay!")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                val forgetEmail = intent.getStringExtra(EMAIL_FORGET)
                if(forgetEmail != null) {
                    moveResetActivity(bundle)
                } else {
                    moveLoginActivity()
                }
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

    companion object {
        const val NAME_USER = "name"
        const val EMAIL_FORGET = "forget"
    }
}