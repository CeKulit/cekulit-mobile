package com.bangkit.cekulit.ui.skincare

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.cekulit.data.response.ProductResponseItem
import com.bangkit.cekulit.databinding.ItemSkincareBinding
import com.bumptech.glide.Glide

class SkincareAdapter: ListAdapter<ProductResponseItem, SkincareAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {
    inner class MyViewHolder(private val binding: ItemSkincareBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(listSkincare: ProductResponseItem){
            Glide
                .with(binding.ivSkincarePhoto.context)
                .load(listSkincare.poster)
                .into(binding.ivSkincarePhoto)
            binding.tvTitleSkincare.text = listSkincare.title
//            binding.root.setOnClickListener {
//                val intent = Intent(binding.root.context, DetailProductActivity::class.java)
//                intent.putExtra(ID_PRODUCT, listProduct.id)
//                binding.root.context.startActivity(intent)
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemSkincareBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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