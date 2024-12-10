package com.bangkit.cekulit.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.cekulit.databinding.FragmentHistoryBinding
import com.bangkit.cekulit.ui.ViewModelFactory

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel: HistoryViewModel by viewModels {
            ViewModelFactory.getInstance(requireActivity())
        }



        val historyAdapter = HistoryAdapter()


        viewModel.getListHistory().observe(viewLifecycleOwner){ result ->
            historyAdapter.submitList(result)
            if (result.isNullOrEmpty()){
                binding?.tvEmpty?.visibility = View.VISIBLE
            } else {
                binding?.tvEmpty?.visibility = View.GONE
            }
        }

        binding?.rvHistory?.apply {
            adapter = historyAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}