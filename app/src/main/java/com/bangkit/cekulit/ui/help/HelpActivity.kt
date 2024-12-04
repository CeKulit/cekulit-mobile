package com.bangkit.cekulit.ui.help

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.cekulit.R
import com.bangkit.cekulit.databinding.ActivityHelpBinding

class HelpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHelpBinding
    private var isDropdown1 = false
    private var isDropdown2 = false
    private var isDropdown3 = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.llFaq1.setOnClickListener {
            isDropdown1 = !isDropdown1
            if(isDropdown1) {
                binding.apply {
                    ivDropdown1.setImageResource(R.drawable.ic_drop_up)
                    tvTitleSubFaq1.visibility = View.VISIBLE
                }
            } else {
                binding.apply {
                    ivDropdown1.setImageResource(R.drawable.ic_drop_down)
                    tvTitleSubFaq1.visibility = View.GONE
                }
            }
        }

        binding.llFaq2.setOnClickListener {
            isDropdown2 = !isDropdown2
            if(isDropdown2) {
                binding.apply {
                    ivDropdown2.setImageResource(R.drawable.ic_drop_up)
                    tvTitleSubFaq2.visibility = View.VISIBLE
                }
            } else {
                binding.apply {
                    ivDropdown2.setImageResource(R.drawable.ic_drop_down)
                    tvTitleSubFaq2.visibility = View.GONE
                }
            }
        }

        binding.llFaq3.setOnClickListener {
            isDropdown3 = !isDropdown3
            if(isDropdown3) {
                binding.apply {
                    ivDropdown3.setImageResource(R.drawable.ic_drop_up)
                    tvTitleSubFaq3.visibility = View.VISIBLE
                }
            } else {
                binding.apply {
                    ivDropdown3.setImageResource(R.drawable.ic_drop_down)
                    tvTitleSubFaq3.visibility = View.GONE
                }

            }
        }
    }
}