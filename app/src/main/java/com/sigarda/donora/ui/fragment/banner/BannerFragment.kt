package com.sigarda.donora.ui.fragment.banner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.sigarda.donora.R
import com.sigarda.donora.databinding.FragmentBannerBinding
import com.sigarda.donora.databinding.FragmentHomeBinding
import com.sigarda.donora.ui.fragment.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BannerFragment : BaseFragment() {

    private val args: BannerFragmentArgs by navArgs()
    private var _binding: FragmentBannerBinding? = null
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
        _binding = FragmentBannerBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }




    private fun initView() {
        binding.apply {
            args.bannerDetails.apply {

                var title = necessity
                var desc = description
                var name = name
                var phone = phone_number

                tvTitle.text = title
                tvDescription.text = desc
                tvName.text = "Pemohon : "+name
                btnCall.text = phone
            }

        }
    }


}