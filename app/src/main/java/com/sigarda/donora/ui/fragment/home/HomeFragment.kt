package com.sigarda.donora.ui.fragment.home

import android.os.Bundle
import android.service.controls.ControlsProviderService
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.graphics.BlendMode.Companion.Plus
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
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
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestServerAuthCode("935126427076-jgcmqckpb2af307vrut7tfhng0j7itkd.apps.googleusercontent.com")
            .requestEmail()
            .build()

        val mGoogleSignInOptions = GoogleSignIn.getClient(this.requireContext(),gso)
        val account = GoogleSignIn.getLastSignedInAccount(this.requireContext())

//        binding.btnLogout.setOnClickListener(){
//            toLogOut()
//            this }
        binding.ava.setOnClickListener(){
            toProfile()
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
                findNavController().navigate(R.id.action_nav_home_to_splashFragment)
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

    private fun navigate(){
        toProfile()
    }

    private fun toProfile(){
        findNavController().navigate(R.id.action_nav_home_to_profileFragment)
    }

//    override fun onClick(v: View) {
//        when (v.id) {
//
//            R.id.btn_logout -> signOut()
//            R.id.btn_logout -> revokeAccess()
//            R.id.btn_logout -> toLogOut()
//        }
//    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}