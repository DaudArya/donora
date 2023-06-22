package com.sigarda.donora.ui.fragment.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.sigarda.donora.R
import com.sigarda.donora.databinding.FragmentHistoryBinding
import com.sigarda.donora.databinding.FragmentHistoryUnitBinding
import com.sigarda.donora.ui.fragment.base.BaseFragment
import com.sigarda.donora.ui.fragment.stock.VendorBloodFragmentArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryUnitFragment : BaseFragment() {

    private var _binding: FragmentHistoryUnitBinding? = null
    private val binding get() = _binding!!
    private val args: HistoryUnitFragmentArgs by navArgs()

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
        _binding = FragmentHistoryUnitBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        binding.ivBack.setOnClickListener(){
            findNavController().navigate(R.id.action_historyUnitFragment_to_historyFragment)
        }
    }

    private fun initViews() {
        binding.apply {
            args.historyUserDetail.apply {

                tvDesc.text = description
                tvDate.text = date

                val image = receipt_donor

                if (image == ""|| image == null){
                    historyImage.setImageResource(R.drawable.ic_list_history)
                } else{
                    Glide.with(requireContext())
                        .load(image)
                        .circleCrop()
                        .into(binding.historyImage)
                }

            }
        }
    }

}