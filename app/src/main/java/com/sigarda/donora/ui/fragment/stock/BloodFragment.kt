package com.sigarda.donora.ui.fragment.stock

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sigarda.donora.R
import com.sigarda.donora.adapter.BloodAdapter
import com.sigarda.donora.adapter.LeaderboardAdapter
import com.sigarda.donora.data.network.models.auth.register.requestbody.RegisterRequestBody
import com.sigarda.donora.data.network.models.leaderboard.commonplace.Data
import com.sigarda.donora.data.network.models.stock.BloodRequestBody
import com.sigarda.donora.data.network.models.stock.ProductData
import com.sigarda.donora.databinding.FragmentBloodBinding
import com.sigarda.donora.ui.fragment.base.BaseFragment
import com.sigarda.donora.ui.fragment.stock.viewmodel.StockViewModel
import com.sigarda.donora.utils.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BloodFragment : BaseFragment() {

    private var _binding: FragmentBloodBinding? = null
    private val binding get() = _binding!!
    private val adapter: BloodAdapter by lazy { BloodAdapter(::onClicked) }
    private val viewModel: StockViewModel by viewModels()

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
        _binding = FragmentBloodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomNavigationViewVisibility = View.GONE

        val sharedViewModel = ViewModelProvider(requireActivity()).get(StockViewModel::class.java)
        sharedViewModel.sharedValue.observe(viewLifecycleOwner) { value ->
            val blood_id = value
            getBlood(blood_id)
            initList()
            getBloodStock()
        }


        binding.ivBack.setOnClickListener(){
            findNavController().navigate(R.id.action_bloodFragment_to_stockFragment)
        }

    }

    private fun getBlood(id_blood : Int) {
        viewModel.listBloodData(BloodRequestBody(blood_id = id_blood))
    }

    private fun initList() {
        binding.rvBlood.apply {
            adapter = this@BloodFragment.adapter
            layoutManager = LinearLayoutManager(this@BloodFragment.context)
        }

    }

    private fun getBloodStock(){
        viewModel.getBloodResponse.observe(viewLifecycleOwner) { result ->
            showBannerListAll(result)
            Log.d(ContentValues.TAG, "Fragment -> ${result}")
        }
    }

    private fun showBannerListAll(podium: List<ProductData>) {

        adapter.submitList(podium)
        binding.rvBlood.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }



    private fun onClicked(banner: ProductData) {
//        viewModel.getAllLeaderBoardResponse.observe(viewLifecycleOwner) { result ->
//            val direction = LeaderboardFragmentDirections.actionLeaderboardFragmentToLeaderboardProfileFragment(banner)
//            findNavController().navigate(direction)
//        }
    }

}