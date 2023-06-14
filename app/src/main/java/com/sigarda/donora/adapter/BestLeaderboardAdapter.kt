package com.sigarda.donora.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sigarda.donora.R
import com.sigarda.donora.data.network.models.leaderboard.bestplace.Data
import com.sigarda.donora.databinding.ItemBestplaceLeaderboardBinding
import kotlin.reflect.KFunction1


class BestLeaderboardAdapter (private val onClick: KFunction1<Data, Unit>) :
    ListAdapter<Data, BestLeaderboardAdapter.ViewHolder>(DIFF_CALLBACK) {



    inner class ViewHolder(private val binding: ItemBestplaceLeaderboardBinding) :
        RecyclerView.ViewHolder(binding.root) {



        val cvPodium: ConstraintLayout = itemView.findViewById(R.id.cv_podium)
        val clPodium: ConstraintLayout = itemView.findViewById(R.id.cl_podium)
        val ivlevel: ImageView = itemView.findViewById(R.id.iv_level_best)
        val tvusername: TextView = itemView.findViewById(R.id.tv_username_best)
        val tvlist: TextView = itemView.findViewById(R.id.tv_list)



        fun bind(item: Data) {

            val params1 = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            val params2 = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            val params3 = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            // Set the height by params
            // Set the height by params
            params1.height = 950
            params1.width = 120

            params2.height = 900
            params2.width = 120

            params3.height = 800
            params3.width = 120
            // Set height of RecyclerView
            // Set height of RecyclerView




            binding.apply {

                tvUsernameBest.text = item.name.take(13)
                tvPoin.text = item.point.toString() + " Points"
                tvList.text = position.plus(1).toString()


                val point = item.point
                if (point >= 50){
                    ivLevelBest.setImageResource(R.drawable.ic_knight)
                }else{
                    ivLevelBest.setImageResource(R.drawable.ic_knight)
                }
                if (point >= 100){
                    ivLevelBest.setImageResource(R.drawable.ic_king)
                }
                if (point >= 200){
                    ivLevelBest.setImageResource(R.drawable.ic_emperor)
                }
                if (point >= 300){
                    ivLevelBest.setImageResource(R.drawable.ic_god)
                }

                when (tvlist.text){
                    "1" -> {
                        layoutPodium.setBackgroundResource(R.drawable.view_juara_satu)
                        layoutPodium.setLayoutParams(params1)
                    }
                    "2" -> {
                        layoutPodium.setBackgroundResource(R.drawable.view_juara_dua)
                        layoutPodium.setLayoutParams(params2)
                    }
                    "3" -> {
                        layoutPodium.setBackgroundResource(R.drawable.view_juara_tiga)
                        layoutPodium.setLayoutParams(params3)
                    }
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestLeaderboardAdapter.ViewHolder {
        val binding = ItemBestplaceLeaderboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: BestLeaderboardAdapter.ViewHolder, position: Int) {
        holder.bind(currentList[position])


        val slideUpAnimation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.anim_slideup)
        holder.cvPodium.startAnimation(slideUpAnimation)
        holder.ivlevel.startAnimation(slideUpAnimation)
        holder.tvusername.startAnimation(slideUpAnimation)





        // Atur data ke elemen ViewHolder sesuai kebutuhan Anda
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