package com.bangkit.cekulit.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.cekulit.data.response.ProductResponseItem
import com.bangkit.cekulit.databinding.ItemProductSkincareBinding
import com.bangkit.cekulit.ui.detail.product.DetailProductActivity
import com.bangkit.cekulit.ui.detail.product.DetailProductActivity.Companion.DESC_PRODUCT
import com.bangkit.cekulit.ui.detail.product.DetailProductActivity.Companion.PHOTO_PRODUCT
import com.bangkit.cekulit.ui.detail.product.DetailProductActivity.Companion.TITLE_PRODUCT
import com.bumptech.glide.Glide

class ProductAdapter: ListAdapter<ProductResponseItem, ProductAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {
    inner class MyViewHolder(private val binding: ItemProductSkincareBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(listProduct: ProductResponseItem){
            Glide
                .with(binding.ivPhotoSkincare.context)
                .load(listProduct.poster)
                .into(binding.ivPhotoSkincare)
            binding.tvBrandSkincare.text = listProduct.title
//            binding.tvNameSkincare.text = listProduct.desc
            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, DetailProductActivity::class.java)
                intent.putExtra(PHOTO_PRODUCT, listProduct.poster)
                intent.putExtra(TITLE_PRODUCT, listProduct.title)
                intent.putExtra(DESC_PRODUCT, listProduct.desc)
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
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProductResponseItem>() {
            override fun areItemsTheSame(oldItem: ProductResponseItem, newItem: ProductResponseItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ProductResponseItem, newItem: ProductResponseItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}