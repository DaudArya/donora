package com.sigarda.donora.ui.fragment.schedule

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sigarda.donora.adapter.SchdeduleDonorAdapter
import com.sigarda.donora.data.network.models.schedule.Data
import com.sigarda.donora.databinding.FragmentScheduleBinding
import com.sigarda.donora.ui.fragment.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ScheduleFragment : BaseFragment() {

    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ScheduleViewModel by viewModels()

    private val adapter: SchdeduleDonorAdapter by lazy { SchdeduleDonorAdapter(::onClicked) }
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
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getDonorSchedule()
        initList()
        getSchedule()

    }

    private fun initList() {
        binding.rvSchedule.apply {
            adapter = this@ScheduleFragment.adapter
            layoutManager = LinearLayoutManager(this@ScheduleFragment.context)
        }

    }

    private fun getSchedule(){
        viewModel.getScheduleResponse.observe(viewLifecycleOwner) { result ->
            showBannerListAll(result)
            Log.d(ContentValues.TAG, "Fragment -> ${result}")
        }
    }

    private fun showBannerListAll(podium: List<Data>) {

        adapter.submitList(podium)
        binding.rvSchedule.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }


    private fun onClicked(banner: Data) {
        viewModel.getScheduleResponse.observe(viewLifecycleOwner) { result ->
            val direction = ScheduleFragmentDirections.actionScheduleFragmentToScheduleUnitFragment(banner)
            findNavController().navigate(direction)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}