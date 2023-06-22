package com.sigarda.donora.ui.fragment.history

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sigarda.donora.R
import com.sigarda.donora.adapter.HistoryAdapter
import com.sigarda.donora.data.network.models.history.Get.TotalHistory
import com.sigarda.donora.databinding.FragmentHistoryBinding
import com.sigarda.donora.ui.fragment.auth.login.LoginViewModel
import com.sigarda.donora.ui.fragment.base.BaseFragment
import com.sigarda.donora.utils.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HistoryFragment : BaseFragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val adapter: HistoryAdapter by lazy { HistoryAdapter(::onClicked) }

    private val viewModel: HistoryViewModel by viewModels()

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
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavigationViewVisibility = View.GONE

        navigate()
        getHistoryUser()
        observeData()
        getHistory()
        initList()
        getHistoryList()

    }

    private fun observeData(){
        viewModel.getHistoryUserResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success ->{
                    binding.apply {
                        tvTotalHistory.text = it.data?.data?.jumlah.toString()
                        pbHistory.visibility = View.GONE
                    }
                    Log.d("GetHistory", it.message.toString())
                }
                is Resource.Error -> {
                }
                is Resource.Loading ->{
                }
                else -> {}
            }

        }
    }

    private fun getHistoryUser() {
        authViewModel.getDataStoreToken().observe(viewLifecycleOwner) {
            viewModel.getHistoryUser("Bearer $it")
        }
    }

    private fun getHistory() {
        authViewModel.getDataStoreToken().observe(viewLifecycleOwner) {
           viewModel.listBloodData("Bearer $it")
        }
    }

    private fun initList() {
        binding.rvHistory.apply {
            adapter = this@HistoryFragment.adapter
            layoutManager = LinearLayoutManager(this@HistoryFragment.context)
        }

    }

    private fun getHistoryList(){
        viewModel.getHistoryVendorResponse.observe(viewLifecycleOwner) { result ->
            showBannerListAll(result)
            Log.d(ContentValues.TAG, "Fragment -> ${result}")
        }
    }

    private fun showBannerListAll(podium: List<TotalHistory>) {

        adapter.submitList(podium)
        binding.rvHistory.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun navigate(){
        binding.ivBack.setOnClickListener(){
            findNavController().navigate(R.id.action_historyFragment_to_nav_home)
        }
        binding.tvAddHistory.setOnClickListener(){
            findNavController().navigate(R.id.action_historyFragment_to_createHistoryFragment)
        }
    }




    private fun onClicked(history: TotalHistory) {
                viewModel.getHistoryVendorResponse.observe(viewLifecycleOwner) { result ->
            val direction = HistoryFragmentDirections.actionHistoryFragmentToHistoryUnitFragment(history)
            findNavController().navigate(direction)
        }
    }
}