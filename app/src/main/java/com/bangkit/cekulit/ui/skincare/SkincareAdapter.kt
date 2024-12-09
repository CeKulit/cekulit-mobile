package com.bangkit.cekulit.ui.skincare

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.cekulit.data.response.ListSkincareResponse
import com.bangkit.cekulit.databinding.ItemSkincareBinding
import com.bangkit.cekulit.ui.camera.CameraActivity.Companion.EXTRA_RESULT_ANALYSIS
import com.bangkit.cekulit.ui.detail.skincare.DetailSkincareActivity
import com.bangkit.cekulit.ui.detail.skincare.DetailSkincareActivity.Companion.TITLE_SKINCARE
import com.bumptech.glide.Glide

class SkincareAdapter(private val analysisResult: String): ListAdapter<ListSkincareResponse, SkincareAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {
    inner class MyViewHolder(private val binding: ItemSkincareBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(listSkincare: ListSkincareResponse){
            Glide
                .with(binding.ivSkincarePhoto.context)
                .load(listSkincare.photoUrl)
                .into(binding.ivSkincarePhoto)
            binding.tvTitleSkincare.text = listSkincare.title
            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, DetailSkincareActivity::class.java)
                intent.putExtra(EXTRA_RESULT_ANALYSIS, analysisResult)
                intent.putExtra(TITLE_SKINCARE, listSkincare.title)
                binding.root.context.startActivity(intent)
            }
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
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListSkincareResponse>() {
            override fun areItemsTheSame(oldItem: ListSkincareResponse, newItem: ListSkincareResponse): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ListSkincareResponse, newItem: ListSkincareResponse): Boolean {
                return oldItem == newItem
            }
        }
    }
}