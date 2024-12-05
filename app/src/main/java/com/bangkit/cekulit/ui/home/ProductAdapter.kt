package com.bangkit.cekulit.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.cekulit.data.response.ListStoryItem
import com.bangkit.cekulit.databinding.ItemProductSkincareBinding
import com.bangkit.cekulit.ui.detail.product.DetailProductActivity
import com.bangkit.cekulit.ui.detail.product.DetailProductActivity.Companion.ID_PRODUCT
import com.bumptech.glide.Glide

class ProductAdapter: ListAdapter<ListStoryItem, ProductAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {
    inner class MyViewHolder(private val binding: ItemProductSkincareBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(listProduct: ListStoryItem){
            Glide
                .with(binding.ivPhotoSkincare.context)
                .load(listProduct.photoUrl)
                .into(binding.ivPhotoSkincare)
            binding.tvBrandSkincare.text = listProduct.name
            binding.tvNameSkincare.text = listProduct.description
            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, DetailProductActivity::class.java)
                intent.putExtra(ID_PRODUCT, listProduct.id)
                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemProductSkincareBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemProduct = getItem(position)
        holder.bind(itemProduct)
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}