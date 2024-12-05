package com.bangkit.cekulit.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.cekulit.data.response.Story
import com.bangkit.cekulit.databinding.ItemFavProductBinding
import com.bangkit.cekulit.ui.detail.product.DetailProductActivity
import com.bangkit.cekulit.ui.detail.product.DetailProductActivity.Companion.ID_PRODUCT
import com.bumptech.glide.Glide

class FavProductAdapter: ListAdapter<Story, FavProductAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {
    inner class MyViewHolder(private val binding: ItemFavProductBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(listFavProduct: Story){
            Glide
                .with(binding.ivPhotoSkincare.context)
                .load(listFavProduct.photoUrl)
                .into(binding.ivPhotoSkincare)
            binding.tvBrandSkincare.text = listFavProduct.name
            binding.tvNameSkincare.text = listFavProduct.description
            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, DetailProductActivity::class.java)
                intent.putExtra(ID_PRODUCT, listFavProduct.id)
                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemFavProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemProduct = getItem(position)
        holder.bind(itemProduct)
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }
        }
    }
}