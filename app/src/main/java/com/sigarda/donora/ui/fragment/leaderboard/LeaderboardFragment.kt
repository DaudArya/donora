package com.sigarda.donora.ui.fragment.leaderboard

import android.content.ContentValues
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.sigarda.donora.R
import com.sigarda.donora.adapter.BannerAdapter
import com.sigarda.donora.adapter.BestLeaderboardAdapter
import com.sigarda.donora.adapter.LeaderboardAdapter
import com.sigarda.donora.data.network.models.leaderboard.bestplace.BestLeaderBoardResponse
import com.sigarda.donora.data.network.models.leaderboard.commonplace.Data
import com.sigarda.donora.databinding.FragmentLeaderboardBinding
import com.sigarda.donora.databinding.FragmentProfileBinding
import com.sigarda.donora.ui.fragment.auth.login.LoginViewModel
import com.sigarda.donora.ui.fragment.base.BaseFragment
import com.sigarda.donora.ui.fragment.home.HomeFragmentDirections
import com.sigarda.donora.ui.fragment.profile.ProfileViewModel
import com.sigarda.donora.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeaderboardFragment : BaseFragment() {

    private var _binding: FragmentLeaderboardBinding? = null

    private val adapterAll: LeaderboardAdapter by lazy { LeaderboardAdapter(::onClicked) }
    private val adapterBest: BestLeaderboardAdapter by lazy { BestLeaderboardAdapter (::onClickedBest) }
    private val binding get() = _binding!!

    private val viewModel: LeaderboardViewModel by viewModels()
    private val userViewModel : ProfileViewModel by viewModels()
    private val authViewModel : LoginViewModel by viewModels()


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

        getProfile()
        observeGetProfile()

        viewModel.getAllLeaderboard()
        getAllLeaderboard()
        initList()

        viewModel.getBestLeaderboard()
        getBestLeaderboard()
        initListBest()

    }


    private fun initList() {
        binding.rvCommon.apply {
            adapter = this@LeaderboardFragment.adapterAll
            layoutManager = LinearLayoutManager(this@LeaderboardFragment.context)
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

    private fun initListBest() {
        binding.rvBest.apply {
            adapter = this@LeaderboardFragment.adapterBest
            layoutManager = LinearLayoutManager(this@LeaderboardFragment.context)
        }
    }


    private fun getBestLeaderboard(){
        viewModel.getBestLeaderBoardResponse.observe(viewLifecycleOwner) { result ->
            showBannerListBest(result)
            Log.d(ContentValues.TAG, "Fragment -> ${result}")
        }
    }
    private fun showBannerListBest(bestPodium: List<com.sigarda.donora.data.network.models.leaderboard.bestplace.Data>) {
        adapterBest.submitList(bestPodium)
        binding.rvBest.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun getProfile() {
        authViewModel.getDataStoreToken().observe(viewLifecycleOwner) {
            userViewModel.GetProfileUser("Bearer $it")
        }
    }

    private fun observeGetProfile(){
        userViewModel.GetProfileUserResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success ->{
                    binding.apply {
                        pbLeaderboard.visibility = View.GONE
                    }
                    Log.d("GetUserProfileResponse", it.data.toString())
                }
                is Resource.Error -> {
                }
                is Resource.Loading ->{
                }
                else -> {}
            }

        }
    }



    private fun onClicked(banner: Data) {
//        viewModel.getAllLeaderBoardResponse.observe(viewLifecycleOwner) { result ->
//            val direction = LeaderboardFragmentDirections.actionLeaderboardFragmentToLeaderboardProfileFragment(banner)
//            findNavController().navigate(direction)
//        }
    }

    private fun onClickedBest(banner: com.sigarda.donora.data.network.models.leaderboard.bestplace.Data) {
//        viewModel.getAllLeaderBoardResponse.observe(viewLifecycleOwner) { result ->
//            val direction = LeaderboardFragmentDirections.actionLeaderboardFragmentToLeaderboardProfileFragment(banner)
//            findNavController().navigate(direction)
//        }
    }




}