package com.sigarda.donora.ui.fragment.leaderboard

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.sigarda.donora.R
import com.sigarda.donora.adapter.BestLeaderboardAdapter
import com.sigarda.donora.adapter.LeaderboardAdapter
import com.sigarda.donora.data.network.models.leaderboard.commonplace.Data
import com.sigarda.donora.databinding.FragmentLeaderboardBinding
import com.sigarda.donora.databinding.FragmentProfileBinding
import com.sigarda.donora.ui.fragment.base.BaseFragment
import com.sigarda.donora.ui.fragment.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeaderboardFragment : BaseFragment() {

    private var _binding: FragmentLeaderboardBinding? = null
    private var mGoogleSignInClient : GoogleSignInClient? = null

    private lateinit var adapterAll: LeaderboardAdapter
    private lateinit var adapterBest: BestLeaderboardAdapter
    private val binding get() = _binding!!

    private val viewModel: LeaderboardViewModel by viewModels()

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
        _binding = FragmentLeaderboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomNavigationViewVisibility = View.VISIBLE
        viewModel.getBestLeaderboard()
        viewModel.getAllLeaderboard()
        getBestLeaderboard()
        getAllLeaderboard()
        initList()
    }

    private fun getBestLeaderboard(){
        viewModel.getBestLeaderBoardResponse.observe(viewLifecycleOwner) { result ->
            showBannerListBest(result)
            Log.d(ContentValues.TAG, "Fragment -> ${result}")
        }
    }

    private fun getAllLeaderboard(){
        viewModel.getAllLeaderBoardResponse.observe(viewLifecycleOwner) { result ->
            showBannerListAll(result)
            Log.d(ContentValues.TAG, "Fragment -> ${result}")
        }
    }

    private fun showBannerListAll(podium: List<Data>) {
        adapterAll.submitList(podium)
        binding.rvCommon.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun showBannerListBest(podium: List<Data>) {
        adapterBest.submitList(podium)
        binding.rvBest.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun initList() {
        binding.rvCommon.apply {
            adapter = this@LeaderboardFragment.adapterAll
            layoutManager = LinearLayoutManager(this@LeaderboardFragment.context)
        }
        binding.rvBest.apply {
            adapter = this@LeaderboardFragment.adapterBest
            layoutManager = LinearLayoutManager(this@LeaderboardFragment.context)
        }
    }




}