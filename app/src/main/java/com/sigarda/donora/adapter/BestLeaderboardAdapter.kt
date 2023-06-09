package com.sigarda.donora.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sigarda.donora.R
import com.sigarda.donora.data.network.models.leaderboard.commonplace.Data
import com.sigarda.donora.databinding.ItemBestplaceLeaderboardBinding
import com.sigarda.donora.databinding.ItemLeaderboardBinding
import kotlin.reflect.KFunction1

class BestLeaderboardAdapter (private val onClick: KFunction1<Data, Unit>) :
    ListAdapter<Data, BestLeaderboardAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(private val binding: ItemBestplaceLeaderboardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Data) {
            binding.apply {

                tvUsername.text = item.name
                tvPoin.text = item.point.toString() + " Points"
                tvList.text = position.plus(1).toString()
                val point = item.point
                if (point >= 50){
                    ivLevel.setImageResource(R.drawable.ic_knight)
                }else if (point >= 100){
                    ivLevel.setImageResource(R.drawable.ic_king)
                }else if (point >= 200){
                    ivLevel.setImageResource(R.drawable.ic_emperor)
                }else if (point >= 300){
                    ivLevel.setImageResource(R.drawable.ic_god)
                }else{
                    ivLevel.setImageResource(R.drawable.ic_knight)
                }


            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestLeaderboardAdapter.ViewHolder {
        val binding =
            ItemBestplaceLeaderboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BestLeaderboardAdapter.ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int = currentList.size

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Data>() {
            override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem == newItem
            }
        }
    }
}