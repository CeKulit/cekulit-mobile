package com.bangkit.cekulit.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bangkit.cekulit.databinding.FragmentFavoriteBinding
import com.bangkit.cekulit.ui.ViewModelFactory

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel: FavoriteViewModel by viewModels {
            ViewModelFactory.getInstance(requireActivity())
        }



        val favAdapter = FavProductAdapter()


        viewModel.getListFavProducts().observe(viewLifecycleOwner){ result ->
            favAdapter.submitList(result)
            if (result.isNullOrEmpty()){
                binding?.tvEmpty?.visibility = View.VISIBLE
            } else {
                binding?.tvEmpty?.visibility = View.GONE
            }
        }

        binding?.rvFavProduct?.apply {
            adapter = favAdapter
            layoutManager = StaggeredGridLayoutManager( 2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}