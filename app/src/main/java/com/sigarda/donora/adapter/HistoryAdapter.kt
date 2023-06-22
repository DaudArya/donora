package com.sigarda.donora.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sigarda.donora.R
import com.sigarda.donora.data.network.models.history.Get.TotalHistory
import com.sigarda.donora.databinding.ItemDonorHistoryBinding
import kotlin.reflect.KFunction1

class HistoryAdapter (private val onClick: KFunction1<TotalHistory, Unit>) :
    ListAdapter<TotalHistory, HistoryAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(private val binding: ItemDonorHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TotalHistory) {
            binding.apply {

                tvHistoryDesc.text = item.description
                tvHistoryDate.text = item.date
                val status = item.status
                if (status == "Accepted"){
                    tvHistoryStatus.setBackgroundResource(R.drawable.view_accepted)
                    tvHistoryStatus.text = item.status
                }
                if (status == "Rejected") {
                    tvHistoryStatus.setBackgroundResource(R.drawable.view_delete)
                    tvHistoryStatus.text = item.status
                }
                if (status == "Pending"){
                    tvHistoryStatus.setBackgroundResource(R.drawable.view_login)
                    tvHistoryStatus.text = item.status
                }

                root.setOnClickListener { onClick(item) }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
        val binding =
            ItemDonorHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryAdapter.ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int = currentList.size

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TotalHistory>() {
            override fun areItemsTheSame(oldItem: TotalHistory, newItem: TotalHistory): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TotalHistory, newItem: TotalHistory): Boolean {
                return oldItem == newItem
            }
        }
    }
}