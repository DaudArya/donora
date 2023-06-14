package com.sigarda.donora.ui.fragment.stock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.sigarda.donora.R
import com.sigarda.donora.databinding.FragmentProfileBinding
import com.sigarda.donora.databinding.FragmentStockBinding
import com.sigarda.donora.ui.fragment.base.BaseFragment
import com.sigarda.donora.ui.fragment.profile.ProfileViewModel
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


}