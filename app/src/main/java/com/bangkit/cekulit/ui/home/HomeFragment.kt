package com.bangkit.cekulit.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bangkit.cekulit.data.response.ProductResponseItem
import com.bangkit.cekulit.databinding.FragmentHomeBinding
import com.bangkit.cekulit.ui.ViewModelFactory
import com.bangkit.cekulit.ui.auth.login.LoginActivity
import com.bangkit.cekulit.ui.auth.reset.otp.OtpActivity.Companion.NAME_USER
import com.bangkit.cekulit.ui.setting.SettingViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    private val settingViewModel: SettingViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       setupObserver()

        homeViewModel.authToken.observe(viewLifecycleOwner) { token ->
            Log.e("TOKEN", token )
            if (!token.isNullOrEmpty()) {
                homeViewModel.getProducts()
                homeViewModel.getProfile()
                homeViewModel.profile.observe(viewLifecycleOwner){
                    requireActivity().intent.putExtra(NAME_USER, it.name)
                    binding.tvTitleSubHomepage.text = it.name
                }
            } else {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }

        homeViewModel.product.observe(viewLifecycleOwner){
            showProduct(it)
        }

        binding.searchView.setOnQueryTextListener(object: OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    Log.e("onQueryTextChange", newText)
                }
                filter(newText ?: "")
                return true
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showProduct(product: List<ProductResponseItem>) {
        val adapter = ProductAdapter()
        adapter.submitList(product)
        binding.rvProduct.adapter = adapter
        binding.rvProduct.layoutManager = StaggeredGridLayoutManager( 2, StaggeredGridLayoutManager.VERTICAL)

    }

    private fun showLoading(isLoading: Boolean){
        if(isLoading) binding.progressBar.visibility = View.VISIBLE else binding.progressBar.visibility = View.GONE
    }

    private fun filter(query: String?){
        homeViewModel.viewModelScope.launch {
            if(!query.isNullOrEmpty()){
                homeViewModel.showFilter(query)
            }
        }
    }

    private fun setListEmpty(isEmpty: Boolean) {
        binding.tvEmpty.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    private fun setupObserver() {
        homeViewModel.responseProfile.observe(viewLifecycleOwner){
            showErrorDialog(it)
        }
        homeViewModel.responseProducts.observe(viewLifecycleOwner){
            showErrorDialog(it).also {
                settingViewModel.logout()
            }
        }
        homeViewModel.isEmpty.observe(viewLifecycleOwner){
            setListEmpty(it)
        }
        homeViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }
    }

    private fun showErrorDialog(message: String){
        AlertDialog.Builder(requireContext())
            .setTitle("Oops!")
            .setMessage(message)
            .setPositiveButton("OK"){ dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }


}