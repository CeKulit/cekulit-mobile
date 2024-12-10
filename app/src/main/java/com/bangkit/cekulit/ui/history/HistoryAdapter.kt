package com.bangkit.cekulit.ui.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.cekulit.data.room.history.HistoryEntity
import com.bangkit.cekulit.databinding.ItemHistoryBinding
import com.bumptech.glide.Glide
import java.text.NumberFormat

class HistoryAdapter: ListAdapter<HistoryEntity, HistoryAdapter.MyViewHolder>(
    DIFF_CALLBACK) {

    inner class MyViewHolder(private val binding: ItemHistoryBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(listHistory: HistoryEntity){
            Glide
                .with(binding.imgHistory.context)
                .load(listHistory.image)
                .into(binding.imgHistory)
            binding.tvResult.text = listHistory.result
            binding.tvScore.text = NumberFormat.getPercentInstance()
                .format(listHistory.confidenceScore).trim()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemEvent = getItem(position)
        holder.bind(itemEvent)
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HistoryEntity>() {
            override fun areItemsTheSame(oldItem: HistoryEntity, newItem: HistoryEntity): Boolean {
                return oldItem == newItem
            }
            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: HistoryEntity, newItem: HistoryEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}