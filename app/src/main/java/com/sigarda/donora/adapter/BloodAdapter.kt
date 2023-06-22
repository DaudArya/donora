package com.sigarda.donora.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sigarda.donora.data.network.models.stock.ProductData
import com.sigarda.donora.databinding.ItemBloodBinding
import kotlin.reflect.KFunction1

class BloodAdapter(private val onClick: KFunction1<ProductData, Unit>) :
    ListAdapter<ProductData, BloodAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(private val binding: ItemBloodBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductData) {
            binding.apply {

                tvId.text = item.product_Blood_id.toString()
                tvNameBlood.text = item.product_name
                tvTotalStock.text = item.stock_product.toString()

                root.setOnClickListener { onClick(item) }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BloodAdapter.ViewHolder {
        val binding =
            ItemBloodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BloodAdapter.ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int = currentList.size

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProductData>() {
            override fun areItemsTheSame(oldItem: ProductData, newItem: ProductData): Boolean {
                return oldItem.product_Blood_id == newItem.product_Blood_id
            }

            override fun areContentsTheSame(oldItem: ProductData, newItem: ProductData): Boolean {
                return oldItem == newItem
            }
        }
    }
}