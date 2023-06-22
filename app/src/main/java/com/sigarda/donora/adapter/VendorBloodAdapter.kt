package com.sigarda.donora.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sigarda.donora.data.network.models.stock.vendorBlood.Data
import com.sigarda.donora.data.network.models.stock.vendorBlood.StockBlood

import com.sigarda.donora.databinding.ItemBloodBinding
import com.sigarda.donora.databinding.ItemVendorBloodBinding
import kotlin.reflect.KFunction1

class VendorBloodAdapter(private val onClick: KFunction1<StockBlood, Unit>) :
    ListAdapter<StockBlood, VendorBloodAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(private val binding: ItemVendorBloodBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StockBlood) {
            binding.apply {

                tvStockPerBlood.text = item.stock
                tvUptd.text = item.vendor_profile.name
                tvAddress.text = item.vendor_profile.address

                root.setOnClickListener { onClick(item) }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VendorBloodAdapter.ViewHolder {
        val binding =
            ItemVendorBloodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VendorBloodAdapter.ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int = currentList.size

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StockBlood>() {
            override fun areItemsTheSame(oldItem: StockBlood, newItem: StockBlood): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: StockBlood, newItem: StockBlood): Boolean {
                return oldItem == newItem
            }
        }
    }
}