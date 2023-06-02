package com.sigarda.donora.ui.fragment.editprofile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.sigarda.donora.R
import com.sigarda.donora.databinding.FragmentEditProfileBinding
import com.sigarda.donora.databinding.FragmentProfileBinding
import com.sigarda.donora.ui.fragment.auth.login.LoginViewModel
import com.sigarda.donora.ui.fragment.profile.ProfileViewModel
import com.sigarda.donora.utils.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private var mGoogleSignInClient : GoogleSignInClient? = null
    private val binding get() = _binding!!


    private val userViewModel: ProfileViewModel by viewModels()
    private val viewModel: EditProfileViewModel by viewModels()
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

        val v: View = inflater.inflate(R.layout.fragment_edit_profile, container, false)
        val spinner = v.findViewById<View>(R.id.bloodType) as Spinner
        val adapter = ArrayAdapter(
            this.requireActivity()!!, android.R.layout.simple_spinner_dropdown_item, bloodType
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // Inflate the layout for this fragment
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getProfile()
        observeGet()
    }

    private fun observeGet(){
        userViewModel.GetProfileUserResponse.observe(viewLifecycleOwner){
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
                            editProfile.etFullname.setText("-")
                        }else{
                            editProfile.etFullname.setText("${it.data?.data?.name}")
                        }
                        val phoneNumber = it.data?.data?.phone_number
                        if (phoneNumber == null ){
                            editProfile.etPhoneNumber.setText("-")
                        }else{
                            editProfile.etPhoneNumber.setText("${it.data?.data?.phone_number}")
                        }
                        val nik = it.data?.data?.NIK
                        if (nik == null ){
                            editProfile.etNik.setText("-")
                        }else{
                            editProfile.etNik.setText("${it.data?.data?.NIK}")
                        }
                        val age = it.data?.data?.age
                        if (age == null ){
                            editProfile.etAge.setText("-")
                        }else{
                            editProfile.etAge.setText("${it.data?.data?.age}")
                        }
                        val kodeDonor = it.data?.data?.code_donor
                        if (kodeDonor == null ){
                            editProfile.etDonorId.setText("-")
                        }else{
                            editProfile.etDonorId.setText("${it.data?.data?.code_donor}")
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

                        var genderType = it.data?.data?.gender.toString()

                        binding.editProfile.radioMale.setOnClickListener {
                            binding.editProfile.radioFemale.isChecked = !binding.editProfile.radioFemale.isChecked
                            genderType = "male"
                        }
                        binding.editProfile.radioFemale.setOnClickListener {
                            binding.editProfile.radioMale.isChecked = !binding.editProfile.radioMale.isChecked
                            genderType = "female"
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
            userViewModel.GetProfileUser("Bearer $it")
        }
        userViewModel.user.observe(viewLifecycleOwner) {
            binding.apply {
                if (it != null) {
                    tvUsername.setText("${it.data?.user?.username.toString()}")
                    editProfile.etFullname.setText("${it.data?.name.toString()}")
                    editProfile.etPhoneNumber.setText("${it.data?.phone_number.toString()}")
                    editProfile.etNik.setText("${it.data?.NIK.toString()}")
                    editProfile.etAge.setText("${it.data?.age.toString()}")
                    editProfile.etDonorId.setText("${it.data?.code_donor.toString()}")


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

                    var gender = it.data?.gender.toString()

                    binding.editProfile.radioMale.setOnClickListener {
                        binding.editProfile.radioFemale.isChecked = !binding.editProfile.radioFemale.isChecked
                        gender = "male"
                    }
                    binding.editProfile.radioFemale.setOnClickListener {
                        binding.editProfile.radioMale.isChecked = !binding.editProfile.radioMale.isChecked
                        gender = "female"
                    }

                }
            }
        }
    }


    var bloodType = arrayOf(
        "A+","A-","B+","B-","O+","O-","AB+","AB-"
    )


}