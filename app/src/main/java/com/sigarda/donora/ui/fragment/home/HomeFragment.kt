package com.sigarda.donora.ui.fragment.home

import android.os.Bundle
import android.service.controls.ControlsProviderService
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.OnCompleteListener
import com.sigarda.donora.R
import com.sigarda.donora.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import org.chromium.base.ContextUtils


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val RC_SIGN_IN = 9001
    private var mGoogleSignInClient : GoogleSignInClient? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

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

        binding.btnLogout.setOnClickListener(){
            toLogOut()
                }

            }


    private fun updateUI(account: GoogleSignInAccount?) {
        if (account != null) {
            try {
                val email = account.email
                val fullName = account.displayName
                val authCode = account.serverAuthCode.toString()

                Log.d("authcode", authCode)

            } catch (e: Exception) {
                if (e.message != null) Log.e(ControlsProviderService.TAG, e.message!!)
                Toast.makeText(
                    ContextUtils.getApplicationContext(),
                    "error",
                    Toast.LENGTH_SHORT
                ).show()
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
                findNavController().navigate(R.id.action_nav_home_to_splashFragment)
            } else {
                requireContext()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}