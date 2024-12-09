package com.bangkit.cekulit.ui.auth.reset.edit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.cekulit.R
import com.bangkit.cekulit.databinding.ActivityEditProfileBinding
import com.bangkit.cekulit.ui.ViewModelFactory
import com.bangkit.cekulit.ui.auth.reset.otp.OtpActivity
import com.bangkit.cekulit.ui.auth.signup.SignupActivity.Companion.EMAIL_USER
import com.bangkit.cekulit.ui.main.MainActivity

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding

    private val editProfileViewModel by viewModels<EditProfileViewModel>{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObserver()
        setupAction()


    }

    private fun setupObserver() {
        editProfileViewModel.responseEdit.observe(this){
            showErrorDialog(it)
        }
        editProfileViewModel.isLoading.observe(this){
            showLoading(it)
            showButton(it)
        }
        editProfileViewModel.authToken.observe(this){token ->
            if(token.isNullOrEmpty()){
                moveActivity()
            }
        }
    }

    private fun setupAction() {
        binding.btnSave.setOnClickListener {
            val name = binding.edName.text.toString()
            val age = binding.edAge.text.toString()
            val gender = binding.edGender.text.toString()

            editProfileViewModel.editProfile(name, age, gender)

            editProfileViewModel.editProfile.observe(this) { response ->
                showSuccessDialog(response.message!!)
            }
        }
    }

    private fun showSuccessDialog(message: String){
        AlertDialog.Builder(this)
            .setTitle("Yeay!")
            .setMessage(message)
            .setPositiveButton("OK"){ dialog, _ ->
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

    private fun moveActivity(){
        val intent = Intent(this@EditProfileActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showLoading(isLoading: Boolean){
        if(isLoading) binding.progressIndicator.visibility = View.VISIBLE else binding.progressIndicator.visibility = View.GONE
    }

    private fun showButton(isEnabled: Boolean){
        binding.btnSave.isEnabled = !isEnabled
    }
}