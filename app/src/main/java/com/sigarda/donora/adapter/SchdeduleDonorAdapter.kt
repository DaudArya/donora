package com.sigarda.donora.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sigarda.donora.data.network.models.schedule.Data
import com.sigarda.donora.databinding.ItemScheduleBinding
import kotlin.reflect.KFunction1

class SchdeduleDonorAdapter (private val onClick: KFunction1<Data, Unit>) :
    ListAdapter<Data, SchdeduleDonorAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(private val binding: ItemScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(items: Data) {
            binding.apply {

                val uptdName = items.vendor_profile.name

                val jadwal = items.time_start

                UTD.text = uptdName
                alamat.text = items.address
                tanggal.text = jadwal.dropLast(9).drop(8)
                val month = jadwal.dropLast(12).drop(5)
                if (month == "01"){
                    bulan.text = "JAN"
                }
                if (month == "02"){
                    bulan.text = "FEB"
                }
                if (month == "03"){
                    bulan.text = "MAR"
                }
                if (month == "04"){
                    bulan.text = "APR"
                }
                if (month == "05"){
                    bulan.text = "MAY"
                }
                if (month == "06"){
                    bulan.text = "JUN"
                }
                if (month == "07"){
                    bulan.text = "JUL"
                }
                if (month == "08"){
                    bulan.text = "AGU"
                }
                if (month == "09"){
                    bulan.text = "SEP"
                }
                if (month == "10"){
                    bulan.text = "OKT"
                }
                if (month == "11"){
                    bulan.text = "NOV"
                }
                if (month == "12"){
                    bulan.text = "DES"
                }
                root.setOnClickListener { onClick(items) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchdeduleDonorAdapter.ViewHolder {
        val binding =
            ItemScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SchdeduleDonorAdapter.ViewHolder, position: Int) {
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