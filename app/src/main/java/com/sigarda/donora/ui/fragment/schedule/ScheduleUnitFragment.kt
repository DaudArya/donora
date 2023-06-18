package com.sigarda.donora.ui.fragment.schedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.sigarda.donora.R
import com.sigarda.donora.data.network.models.schedule.Data
import com.sigarda.donora.databinding.FragmentScheduleBinding
import com.sigarda.donora.databinding.FragmentScheduleUnitBinding
import com.sigarda.donora.ui.fragment.banner.BannerFragmentArgs
import com.sigarda.donora.ui.fragment.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduleUnitFragment : BaseFragment() {

    // TODO: Rename and change types of parameters

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap

    private val args: ScheduleUnitFragmentArgs by navArgs()
    private var _binding: FragmentScheduleUnitBinding? = null
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

        _binding = FragmentScheduleUnitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val mapViewSchedule = binding.mapViewSchedule
//        mapViewSchedule.onCreate(savedInstanceState)
//
//        mapViewSchedule.getMapAsync { map ->
//            googleMap = map
//
//            val latitude = 37.4220
//            val longitude = -122.0841
//            val zoomLevel = 10f
//
//            val coordinate = LatLng(latitude, longitude)
//
//            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate, zoomLevel))}

        bottomNavigationViewVisibility = View.GONE
        initView()

    }

    private fun initView() {
        binding.apply {
            args.ScheduleDonorDetail.apply {
                val jadwal = time_start

                val date = jadwal.dropLast(8)
                val time = jadwal.drop(10).dropLast(3) + " - Selesai"
                val name = vendor_profile.name
                val alamat = address

                tvUptd.text = name
                tvTanggal.text = date
                tvWaktu.text = time
                tvLokasi.text = alamat
            }

        }
    }




}