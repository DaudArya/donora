package com.sigarda.donora.ui.fragment.stock

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.sigarda.donora.R
import com.sigarda.donora.adapter.VendorBloodAdapter
import com.sigarda.donora.data.network.models.stock.vendor.Data
import com.sigarda.donora.data.network.models.stock.VendorBloodRequestBody
import com.sigarda.donora.data.network.models.stock.vendorBlood.StockBlood
import com.sigarda.donora.databinding.FragmentVendorBloodBinding
import com.sigarda.donora.ui.fragment.base.BaseFragment
import com.sigarda.donora.ui.fragment.stock.viewmodel.StockViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class VendorBloodFragment : BaseFragment() {

    private val args: VendorBloodFragmentArgs by navArgs()
    private var _binding: FragmentVendorBloodBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StockViewModel by viewModels()
    private val adapter: VendorBloodAdapter by lazy { VendorBloodAdapter(::onClicked) }

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
        _binding = FragmentVendorBloodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomNavigationViewVisibility = View.GONE
        getVendor()
        initList()
        getBloodVendor()

        binding.ivBack.setOnClickListener(){
            findNavController().navigate(R.id.action_vendorBloodFragment_to_bloodFragment)
        }
    }

    private fun getBloodData(product_blood_id : String) {
        viewModel.getVendorBlood(VendorBloodRequestBody(product_blood_id = product_blood_id))
    }


    private fun getVendor() {
        binding.apply {
            args.ProductDataDetail.apply {
                val id_product = product_Blood_id.toString()
                getBloodData(id_product)
            }
        }
    }

    private fun initList() {
        binding.rvVendor.apply {
            adapter = this@VendorBloodFragment.adapter
            layoutManager = LinearLayoutManager(this@VendorBloodFragment.context)
        }

    }

    private fun getBloodVendor(){
        viewModel.getBloodVendorResponse.observe(viewLifecycleOwner) { result ->
            showBannerListAll(result)
            Log.d(ContentValues.TAG, "Fragment -> ${result}")
        }
    }

    private fun showBannerListAll(podium: List<StockBlood>) {

        adapter.submitList(podium)
        binding.rvVendor.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun onClicked(blood: StockBlood) {
//        viewModel.getBloodResponse.observe(viewLifecycleOwner) { result ->
//            val direction = BloodFragmentDirections.actionBloodFragmentToVendorBloodFragment(blood)
//            findNavController().navigate(direction)
//        }
    }


}