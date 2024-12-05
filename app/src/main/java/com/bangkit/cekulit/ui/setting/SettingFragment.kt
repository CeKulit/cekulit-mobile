package com.bangkit.cekulit.ui.setting

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bangkit.cekulit.R
import com.bangkit.cekulit.databinding.FragmentSettingBinding
import com.bangkit.cekulit.ui.ViewModelFactory
import com.bangkit.cekulit.ui.help.HelpActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private val settingViewModel: SettingViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        auth = Firebase.auth

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.llHelp.setOnClickListener {
            startActivity(Intent(requireContext(), HelpActivity::class.java))
        }

        binding.llLogout.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog(){
        AlertDialog.Builder(requireContext())
            .setMessage(R.string.confirm_logout)
            .setPositiveButton(R.string.confirm_yes){_, _ ->
                auth.signOut()
                settingViewModel.logout()
            }
            .setNegativeButton(R.string.confirm_no){dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
//        (activity as? AppCompatActivity)?.supportActionBar?.hide()

    }
}