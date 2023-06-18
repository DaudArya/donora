package com.sigarda.donora.ui.fragment.stock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sigarda.donora.R
import com.sigarda.donora.databinding.FragmentStockBinding
import com.sigarda.donora.ui.fragment.base.BaseFragment
import com.sigarda.donora.ui.fragment.stock.viewmodel.StockViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class StockFragment : BaseFragment() {


    private var _binding: FragmentStockBinding? = null
    private val binding get() = _binding!!
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
        _binding = FragmentStockBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedViewModel = ViewModelProvider(requireActivity()).get(StockViewModel::class.java)

        binding.bloodAPositive.setOnClickListener(){
            findNavController().navigate(R.id.action_stockFragment_to_bloodFragment)
            sharedViewModel.sharedValue.value = 1
        }
        binding.bloodANegative.setOnClickListener(){
            findNavController().navigate(R.id.action_stockFragment_to_bloodFragment)
            sharedViewModel.sharedValue.value = 2
        }
        binding.bloodBPositive.setOnClickListener(){
            findNavController().navigate(R.id.action_stockFragment_to_bloodFragment)
            sharedViewModel.sharedValue.value = 3
        }
        binding.bloodBNegative.setOnClickListener(){
            findNavController().navigate(R.id.action_stockFragment_to_bloodFragment)
            sharedViewModel.sharedValue.value = 4
        }
        binding.bloodABPositive.setOnClickListener(){
            findNavController().navigate(R.id.action_stockFragment_to_bloodFragment)
            sharedViewModel.sharedValue.value = 5
        }
        binding.bloodABNegative.setOnClickListener(){
            findNavController().navigate(R.id.action_stockFragment_to_bloodFragment)
            sharedViewModel.sharedValue.value = 6
        }
        binding.bloodOPositive.setOnClickListener(){
            findNavController().navigate(R.id.action_stockFragment_to_bloodFragment)
            sharedViewModel.sharedValue.value = 7
        }
        binding.bloodONegative.setOnClickListener(){
            findNavController().navigate(R.id.action_stockFragment_to_bloodFragment)
            sharedViewModel.sharedValue.value = 8
        }

    }


}