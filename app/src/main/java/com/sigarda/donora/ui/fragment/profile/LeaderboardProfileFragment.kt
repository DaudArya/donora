package com.sigarda.donora.ui.fragment.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.sigarda.donora.R
import com.sigarda.donora.databinding.FragmentLeaderboardBinding
import com.sigarda.donora.databinding.FragmentLeaderboardProfileBinding
import com.sigarda.donora.databinding.FragmentProfileBinding
import com.sigarda.donora.ui.fragment.banner.BannerFragmentArgs
import com.sigarda.donora.ui.fragment.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LeaderboardProfileFragment : BaseFragment() {

    private val args: LeaderboardProfileFragmentArgs by navArgs()
    private var _binding: FragmentLeaderboardProfileBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLeaderboardProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.apply {
            args.leaderboardDetails.apply {


                var name = name
                var point = point

                tvUsername.text = name
                pointUser.text = point.toString() + " Poin"

                if (point >= 50){
                    profileImage.setImageResource(R.drawable.ic_knight)
                }else if (point >= 100){
                    profileImage.setImageResource(R.drawable.ic_king)
                }else if (point >= 200){
                    profileImage.setImageResource(R.drawable.ic_emperor)
                }else if (point >= 300){
                    profileImage.setImageResource(R.drawable.ic_god)
                }else{
                    profileImage.setImageResource(R.drawable.ic_knight)
                }

            }

        }
    }


}