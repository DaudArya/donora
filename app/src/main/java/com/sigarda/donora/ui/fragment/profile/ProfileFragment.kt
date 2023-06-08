package com.sigarda.donora.ui.fragment.profile

import android.content.Intent
import android.os.Bundle
import android.service.controls.ControlsProviderService
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.OnCompleteListener
import com.sigarda.donora.R
import com.sigarda.donora.databinding.FragmentProfileBinding
import com.sigarda.donora.ui.fragment.auth.login.LoginViewModel
import com.sigarda.donora.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import org.chromium.base.ContextUtils
import com.google.gson.Gson
import com.sigarda.donora.data.network.models.profile.profile.Data
import com.sigarda.donora.data.network.models.profile.profile.GetProfileUserResponse
import com.sigarda.donora.ui.activity.MainActivity

@AndroidEntryPoint
class ProfileFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentProfileBinding? = null
    private var mGoogleSignInClient : GoogleSignInClient? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()
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
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getProfile()
        observeGet()

        binding.profileImage.setImageResource(R.drawable.mask_group)

        binding.logoutProfile.setOnClickListener(){
            toLogOut() }

        binding.back.setOnClickListener(){
            findNavController().navigate(R.id.action_profileFragment_to_nav_home)
        }
        binding.displayProfile.toEditProfile.setOnClickListener(){
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }

    }



    private fun observeGet(){
        viewModel.GetProfileUserResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success ->{
                    binding.apply {
                        val name = it.data?.data?.user?.username
                        if (name == null){
                            tvUsername.setText("User-${it.data?.data?.id.toString()}${it.data?.data?.user_id.toString()}donora")
                        }else{
                            tvUsername.setText("${it.data?.data?.user?.username}")
                        }
                        val fullname = it.data?.data?.name
                        if (fullname == null ){
                            displayProfile.tvFullname.setText("-")
                        }else{
                            displayProfile.tvFullname.setText("${it.data?.data?.name}")
                        }
                        val phoneNumber = it.data?.data?.phone_number
                        if (phoneNumber == null ){
                            displayProfile.tvPhoneNumber.setText("-")
                        }else{
                            displayProfile.tvPhoneNumber.setText("${it.data?.data?.phone_number}")
                        }
                        val nik = it.data?.data?.NIK
                        if (nik == null ){
                            displayProfile.tvNik.setText("-")
                        }else{
                            displayProfile.tvNik.setText("${it.data?.data?.NIK}")
                        }
                        val age = it.data?.data?.age
                        if (age == null ){
                            displayProfile.tvAge.setText("-")
                        }else{
                            displayProfile.tvAge.setText("${it.data?.data?.age}")
                        }
                        val gender = it.data?.data?.gender
                        if (gender == null ){
                            displayProfile.tvGender.setText("-")
                        }else{
                            displayProfile.tvGender.setText("${it.data?.data?.gender}")
                        }
                        val kodeDonor = it.data?.data?.code_donor
                        if (kodeDonor == null ){
                            displayProfile.tvDonorId.setText("-")
                        }else{
                            displayProfile.tvDonorId.setText("${it.data?.data?.code_donor}")
                        }
                            val avatar = it.data?.data?.profile_picture
                            if (avatar != null){
                                Glide.with(requireContext())
                                    .load(it.data?.data?.profile_picture)
                                    .circleCrop()
                                    .into(binding.profileImage)
                            }
                           if(avatar == null){
                                binding.profileImage.setImageResource(R.drawable.mask_group)
                            }
                    }
                    Log.d("GetUserProfileResponse", it.data.toString())
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "Reload Gagal : ObserveGet", Toast.LENGTH_LONG).show()

                }
                is Resource.Loading ->{
                    binding.proggresBar.visibility = View.VISIBLE
                }
                else -> {}
            }

        }
    }

    private fun getProfile() {
        authViewModel.getDataStoreToken().observe(viewLifecycleOwner) {
            viewModel.GetProfileUser("Bearer $it")
        }
        viewModel.user.observe(viewLifecycleOwner) {
            binding.apply {
                if (it != null) {
                    tvUsername.setText("${it.data?.user?.username.toString()}")
                    displayProfile.tvFullname.setText("${it.data?.name.toString()}")
                    displayProfile.tvPhoneNumber.setText("${it.data?.phone_number.toString()}")
                    displayProfile.tvNik.setText("${it.data?.NIK.toString()}")
                    displayProfile.tvAge.setText("${it.data?.age.toString()}")
                    displayProfile.tvGender.setText("${it.data?.gender.toString()}")
                    displayProfile.tvDonorId.setText("${it.data?.code_donor.toString()}")


                    val avatar = it.data?.profile_picture
                    if (avatar != null){
                        Glide.with(requireContext())
                            .load(it.data?.profile_picture)
                            .circleCrop()
                            .into(binding.profileImage)
                    }
                    if(avatar == null){
                        binding.profileImage.setImageResource(R.drawable.mask_group)
                    }

                }
            }
        }
    }

    private fun signOut() {
        Log.d("signOut", "signOut")
        updateUI(null)
        activity.apply {
            this?.let {
                mGoogleSignInClient!!.signOut()
                    .addOnCompleteListener(it.parent, OnCompleteListener<Void?> {
                        // ...
                    })
            }
        }

    }
    private fun revokeAccess() {
        Log.d("revokeAccess", "Revoke")
        activity.apply {
            this?.let {
                mGoogleSignInClient!!.revokeAccess()
                    .addOnCompleteListener(it.parent, OnCompleteListener<Void?> {
                        // ...
                    })
            }
        }

    }
    private fun toLogOut() {
        val option = NavOptions.Builder()
            .setPopUpTo(R.id.profileFragment, true)
            .build()

        viewModel.statusLogin(false)
        viewModel.getLoginStatus().observe(viewLifecycleOwner) {
            if (it == true) {
                activity?.let { it ->
                    val intent = Intent(it, MainActivity::class.java)
                    it.startActivity(intent)}
            } else {
                requireContext()
            }
        }

    }
    private fun updateUI(account: GoogleSignInAccount?) {
        if (account != null) {
            try {
                val authCode = account.serverAuthCode
                val idToken = account.idToken
                val name = account.displayName
                val email = account.email
//                viewModel.loginGoogle(parseFormIntoEntityGoogle(authCode))
                val msg = "Helo : $name,AuthCode : $authCode, Your Email : $email, Your Token: $idToken"
                Log.d("authcode",msg)

            } catch (e: Exception) {
                if (e.message != null) Log.e(ControlsProviderService.TAG, e.message!!)
                Toast.makeText(
                    ContextUtils.getApplicationContext(),
                    "Error",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("authcode", "Error getting google auth code")
            }
        }else {
            Log.d("authcode", "Error getting google auth code")
        }
    }

        override fun onClick(v: View) {
        when (v.id) {

            R.id.logout_profile -> signOut()
            R.id.logout_profile -> revokeAccess()
            R.id.logout_profile -> toLogOut()
        }
    }


}