package com.bangkit.cekulit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bangkit.cekulit.databinding.ActivityMainBinding
import com.bangkit.cekulit.ui.ViewModelFactory
import com.bangkit.cekulit.ui.auth.login.LoginActivity
import com.bangkit.cekulit.ui.auth.login.LoginViewModel
import com.bangkit.cekulit.ui.camera.CameraActivity
import com.bangkit.cekulit.ui.camera.CameraActivity.Companion.CAMERAX_RESULT
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val loginViewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




        loginViewModel.authToken.observe(this){ token ->
            if (token.isNullOrEmpty()) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        binding.navFabScan.setOnClickListener {
            startCameraX()
        }

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_scan, R.id.navigation_setting
            )
        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }


    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERAX_RESULT) {
            Log.d("MAIN", "URI: ${it.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()}")
        }
    }

}