package com.sigarda.donora.ui.fragment.home

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.messaging.FirebaseMessaging
import com.sigarda.donora.R
import com.sigarda.donora.adapter.BannerAdapter
import com.sigarda.donora.data.network.models.notification.Data
import com.sigarda.donora.data.network.models.notification.NotificationModel
import com.sigarda.donora.databinding.FragmentHomeBinding
import com.sigarda.donora.ui.activity.MainViewModel
import com.sigarda.donora.ui.fragment.auth.login.LoginViewModel
import com.sigarda.donora.ui.fragment.base.BaseFragment
import com.sigarda.donora.ui.fragment.profile.ProfileViewModel
import com.sigarda.donora.utils.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private  val TAG = "Fragment Home"
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private val userViewModel : ProfileViewModel by viewModels()
    private val authViewModel : LoginViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()

    private val adapter: BannerAdapter by lazy { BannerAdapter(::onClicked) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getBanner()
        getProfile()
        getHistory()
        getSchecule()
        getTitle()
        getBanner()
        initList()

        observeGetProfile()
        observeGetTitle()
        observeGetSchedule()
        observeGetHistory()
        observeFcm()

        bottomNavigationViewVisibility = View.VISIBLE
        binding.ava.setOnClickListener(){
            toProfile()
        }

        binding.cvInfo.setOnClickListener(){
            push()
        }

    }

    fun push() {
        FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
            Log.d(TAG, "push: $token")

            mainViewModel.sendNotification(
                NotificationModel(
                    token,
                    Data("FCM Notification","this notification from android")
                )
            )
        }
    }


    private fun observeFcm(){
        mainViewModel.connectionError.observe(viewLifecycleOwner){
            when(it){
                "sending"-> {
                    Toast.makeText(requireContext(), "sending notification", Toast.LENGTH_SHORT).show()
                }
                "sent"-> {
                    Toast.makeText(requireContext(), "notification sent", Toast.LENGTH_SHORT).show()
                }
                "error while sending"-> {
                    Toast.makeText(requireContext(), "error while sending", Toast.LENGTH_SHORT).show()
                }
            }
        }

        mainViewModel.response.observe(viewLifecycleOwner){
            if (it.isNotEmpty())
                Log.d(TAG, "Notification in Kotlin: $it ")
        }
    }



    private fun getBanner(){
        viewModel.getBannerResponse.observe(viewLifecycleOwner) { result ->
            showBannerList(result)
            Log.d(ContentValues.TAG, "Fragment -> ${result}")
        }
    }

    private fun initList() {
        binding.rvBanner.apply {
            adapter = this@HomeFragment.adapter
            layoutManager = LinearLayoutManager(this@HomeFragment.context)
        }
    }

    private fun getProfile() {
        authViewModel.getDataStoreToken().observe(viewLifecycleOwner) {
            userViewModel.GetProfileUser("Bearer $it")
        }
    }

    private fun getTitle() {
        authViewModel.getDataStoreToken().observe(viewLifecycleOwner) {
            viewModel.getTitle("Bearer $it")
        }
    }

    private fun getSchecule() {
        authViewModel.getDataStoreToken().observe(viewLifecycleOwner) {
            viewModel.getSchedule("Bearer $it")
        }
    }

    private fun getHistory() {
        authViewModel.getDataStoreToken().observe(viewLifecycleOwner) {
            viewModel.getHistory("Bearer $it")
        }
    }

    private fun showBannerList(banner: List<com.sigarda.donora.data.network.models.dashboard.banner.Data>) {
        adapter.submitList(banner)
        binding.rvBanner.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }



    private fun observeGetProfile(){
        userViewModel.GetProfileUserResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success ->{
                    binding.apply {
                        val name = it.data?.data?.user?.username
                        if (name == null){
                            usernameHeader.setText("Halo, User-${it.data?.data?.id.toString()}${it.data?.data?.user_id.toString()}donora")
                        }else{
                            usernameHeader.setText("Halo, ${it.data?.data?.user?.username}")
                        }
                        val fullname = it.data?.data?.name
                        if (fullname == null ){
                            username.setText("${it.data?.data?.user?.username}")
                        }else{
                            username.setText("${it.data?.data?.name}")
                        }

                        val bloodType = it.data?.data?.blood
                        if (bloodType == null ){
                            tvBloodType.setText("-")
                        }else{
                            tvBloodType.setText("${it.data?.data?.blood}")
                        }

                        val avatar = it.data?.data?.profile_picture
                        if (avatar != null){
                            Glide.with(requireContext())
                                .load(it.data?.data?.profile_picture)
                                .circleCrop()
                                .into(binding.ava)
                        }
                        if(avatar == null){
                            binding.ava.setImageResource(R.drawable.mask_group)
                        }
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


    private fun observeGetTitle(){
        viewModel.getTitleResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success ->{
                    binding.apply {
                        val title = it?.data?.message
                        if (title == "Ksatria Darah"){
                            ivTitle.setImageResource(R.drawable.ic_knight)
                            tvTitle.setText("Ksatria Darah")
                        } else if (title == "Raja Darah"){
                            ivTitle.setImageResource(R.drawable.ic_king)
                            tvTitle.setText("Raja Darah")
                        }else if (title == "Kaisar Darah"){
                            ivTitle.setImageResource(R.drawable.ic_emperor)
                            tvTitle.setText("Kaisar Darah")
                        }else if (title == "Dewa Darah"){
                            ivTitle.setImageResource(R.drawable.ic_god)
                            tvTitle.setText("Dewa Darah")
                        }else {
                            ivTitle.setImageResource(R.drawable.ic_knight)
                            tvTitle.setText(it.message)
                        }
                    }
                    Log.d("GetTitle", it.message.toString())
                }
                is Resource.Error -> {
                }
                is Resource.Loading ->{
                }
                else -> {}
            }

        }
    }


    private fun observeGetSchedule(){
        viewModel.getScheduleResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success ->{
                    binding.apply {
                        val schedule = it.data?.data?.last()?.date
                        tvNextDonor.setText(schedule)
                    }
                    Log.d("GetSchedule", it.message.toString())
                }
                is Resource.Error -> {
                }
                is Resource.Loading ->{
                }
                else -> {}
            }

        }
    }

    private fun observeGetHistory(){
        viewModel.getHistoryResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success ->{
                    binding.apply {
                        val last_donor = it.data?.data?.jumlah.toString()
                        tvLastDonor.setText(last_donor)
                        val total_donor = it.data?.data?.jumlah.toString()
                        tvTotalDonor.setText(total_donor)
                        val point = it.data?.data?.jumlah?.times(5).toString()
                        tvPoint.setText(point+" Poin")
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



    private fun toProfile(){
        findNavController().navigate(R.id.action_nav_home_to_profileFragment)
    }

    fun toStock(view: View){
        findNavController().navigate(R.id.action_nav_home_to_stockFragment)
    }

    private fun onClicked(banner: com.sigarda.donora.data.network.models.dashboard.banner.Data) {
        viewModel.getBannerResponse.observe(viewLifecycleOwner) { result ->
            val direction = HomeFragmentDirections.actionNavHomeToBannerFragment(banner)
            findNavController().navigate(direction)
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}