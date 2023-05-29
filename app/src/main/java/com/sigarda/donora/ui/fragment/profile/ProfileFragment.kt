package com.sigarda.donora.ui.fragment.profile

import android.os.Bundle
import android.service.controls.ControlsProviderService
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.OnCompleteListener
import com.sigarda.donora.R
import com.sigarda.donora.databinding.FragmentProfileBinding
import com.sigarda.donora.ui.fragment.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.chromium.base.ContextUtils

@AndroidEntryPoint
class ProfileFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentProfileBinding? = null
    private var mGoogleSignInClient : GoogleSignInClient? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

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

        binding.displayProfile.logout.setOnClickListener(){
            toLogOut() }

        binding.back.setOnClickListener(){
            findNavController().navigate(R.id.action_profileFragment_to_nav_home)
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
            .setPopUpTo(R.id.nav_home, true)
            .build()

        viewModel.statusLogin(false)
        viewModel.getLoginStatus().observe(viewLifecycleOwner) {
            if (it == true) {
                findNavController().navigate(R.id.action_profileFragment_to_splashFragment)
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

            R.id.logout -> signOut()
            R.id.logout -> revokeAccess()
            R.id.logout -> toLogOut()
        }
    }


}