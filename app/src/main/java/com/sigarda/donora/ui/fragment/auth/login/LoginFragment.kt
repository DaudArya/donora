package com.sigarda.donora.ui.fragment.auth.login


import android.content.IntentSender
import android.os.Bundle
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.material.snackbar.Snackbar
import com.sigarda.donora.data.network.models.auth.google.login.GoogleAuthRequestBody
import com.sigarda.donora.data.network.models.auth.login.requestbody.LoginRequestBody
import com.sigarda.donora.databinding.FragmentLoginBinding
import com.sigarda.donora.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import org.chromium.base.ContextUtils.getApplicationContext


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val RC_SIGN_IN = 9001
    private var mGoogleSignInClient : GoogleSignInClient? = null


    private var oneTapClient: SignInClient? = null
    private var signUpRequest: BeginSignInRequest? = null
    private var signInRequest: BeginSignInRequest? = null

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
             .requestServerAuthCode("935126427076-jgcmqckpb2af307vrut7tfhng0j7itkd.apps.googleusercontent.com")
             .requestEmail()
             .build()
         mGoogleSignInClient = GoogleSignIn.getClient(this.requireContext(), gso)
     }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        oneTapClient()
        observeDataLogin()
        observeDataLoginGoogle()

        binding.btnLogin.setOnClickListener (){ loginUser() }
        binding.loginGoogle.setOnClickListener (){ displaySignIn() }
        binding.btnLoginguest.setOnClickListener (){ navigateToHome() }
        binding.tvCreateAccount.setOnClickListener (){ openRegister() }

    }

    private fun oneTapClient(){
        oneTapClient = Identity.getSignInClient(this.requireContext())
        signUpRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // server's client ID, not your Android client ID.
                    .setServerClientId("935126427076-jgcmqckpb2af307vrut7tfhng0j7itkd.apps.googleusercontent.com")
                    // Show all accounts on the device.
                    .setFilterByAuthorizedAccounts(false)
                    .build())
            .build()
        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // server's client ID, not your Android client ID.
                    .setServerClientId("935126427076-jgcmqckpb2af307vrut7tfhng0j7itkd.apps.googleusercontent.com")
                    // Show all accounts on the device.
                    .setFilterByAuthorizedAccounts(true)
                    .build())
            .build()
    }


    private fun loginUser() {
        if (validateInput()) {

            val emailOrUsername = binding.etEmailLogin.text.toString().trim()
            val password = binding.etPasswordLogin.text.toString().trim()

            binding.etEmailLogin.isEnabled = false
            binding.etPasswordLogin.isEnabled = false
            viewModel.login(parseFormIntoEntity(emailOrUsername, password))
        }
    }

    private val oneTapResult = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult())
    { result -> try {
            val credential = oneTapClient?.getSignInCredentialFromIntent(result.data)
            val idToken = credential?.googleIdToken
            val name = credential?.displayName
            val email = credential?.id
            when {
                idToken != null -> {
                    // Got an ID token from Google. Use it to authenticate
                    // with your backend.
                    binding.pgGoogle.visibility = View.GONE
                    val msg = "Helo : $name, Your Email : $email, Your Token: $idToken"
                    var tokenGoogle = "$idToken"
                    viewModel.loginGoogle(parseFormIntoEntityGoogle(tokenGoogle))

                    Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
                    viewModel.statusLogin(true)
                    navigateToHome()

                    Log.d("one tap", msg)
                }
                else -> {
                    // Shouldn't happen.
                    Log.d("one tap", "No ID token!")
                    Snackbar.make(binding.root, "No ID token!", Snackbar.LENGTH_SHORT).show()
                }
            }
        } catch (e: ApiException) {
            when (e.statusCode) {
                CommonStatusCodes.CANCELED -> {
                    Log.d("one tap", "One-tap dialog was closed.")
                    // Don't re-prompt the user.
                    Snackbar.make(binding.root, "One-tap dialog was closed.", Snackbar.LENGTH_SHORT).show()
                }
                CommonStatusCodes.NETWORK_ERROR -> {
                    Log.d("one tap", "One-tap encountered a network error.")
                    // Try again or just ignore.
                    Snackbar.make(binding.root, "One-tap encountered a network error.", Snackbar.LENGTH_SHORT).show()
                }
                CommonStatusCodes.TIMEOUT -> {
                    Log.d("one tap", "One-tap encountered timeout.")
                    // Try again or just ignore.
                    Snackbar.make(binding.root, "One-tap encountered a timeout.", Snackbar.LENGTH_SHORT).show()
                }

                else -> {
                    Log.d("one tap", "Couldn't get credential from result." +
                            " (${e.localizedMessage})")
                    Snackbar.make(binding.root, "Couldn't get credential from result.\" +\n" +
                            " (${e.localizedMessage})", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun parseFormIntoEntity(username: String, password: String): LoginRequestBody {
        return LoginRequestBody(username, password)
    }

    private fun parseFormIntoEntityGoogle(idToken: String): GoogleAuthRequestBody {
        return GoogleAuthRequestBody(idToken)
    }

    private fun observeDataLogin() {
        viewModel.postLoginUserResponse.observe(viewLifecycleOwner) {
            binding.etEmailLogin.isEnabled = true
            binding.etPasswordLogin.isEnabled = true
            when (it) {
                is Resource.Success -> {
                    Log.d("loginResponse", it.data.toString())
                    viewModel.statusLogin(true)
                    navigateToHome()
                    saveToken()
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        }
        //datastore
        viewModel.getUserLoginStatus().observe(viewLifecycleOwner) {
            Log.d("getlogin", it.toString())
            if (it) {
            }
        }
    }

    private fun observeDataLoginGoogle() {
        viewModel.postLoginGoogleResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    Log.d("loginResponse", it.data.toString())
                    viewModel.statusLogin(true)
                    saveTokenGoogle()
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        }
    }



    private fun displaySignIn(){
        activity.apply {
            this?.let {
                binding.pgGoogle.visibility = View.VISIBLE
                oneTapClient?.beginSignIn(signInRequest!!)
                    ?.addOnSuccessListener(it) { result ->
                        try {
                            val ib = IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                            oneTapResult.launch(ib)
                        } catch (e: IntentSender.SendIntentException) {
                            Log.e("btn click", "Couldn't start One Tap UI: ${e.localizedMessage}")
                        }
                    }
                    ?.addOnFailureListener(this) { e ->
                        // No Google Accounts found. Just continue presenting the signed-out UI.
                        displaySignUp()
                        Log.d("btn click", e.localizedMessage!!)
                    }
            }
        }
    }

    private fun displaySignUp() {
        activity.apply {
            this?.let {
                binding.pgGoogle.visibility = View.VISIBLE
                oneTapClient?.beginSignIn(signUpRequest!!)
                    ?.addOnSuccessListener(it) { result ->
                        try {
                            val ib = IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                            oneTapResult.launch(ib)
                        } catch (e: IntentSender.SendIntentException) {
                            Log.e("btn click", "Couldn't start One Tap UI: ${e.localizedMessage}")
                        }
                    }
                    ?.addOnFailureListener(this) { e ->
                        // No Google Accounts found. Just continue presenting the signed-out UI.
                        displaySignUp()
                        Log.d("btn click", e.localizedMessage!!)
                    }
            }
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
                if (e.message != null) Log.e(TAG, e.message!!)
                Toast.makeText(
                    getApplicationContext(),
                    getString(androidx.compose.ui.R.string.default_error_message),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true
        val email = binding.etEmailLogin.text.toString().trim()
        val password = binding.etPasswordLogin.text.toString().trim()

        if (email.isEmpty()) {
            isValid = false
            binding.etEmailLogin.error = "Email or Username empty"
        }

        if (password.isEmpty()) {
            isValid = false
            binding.etPasswordLogin.error = "Password empty"
        }
        if (password.length < 8) {
            isValid = false
            Toast.makeText(
                requireContext(),
                "Password should be at least 8 characters",
                Toast.LENGTH_SHORT
            ).show()
        }
        return isValid
    }

    private fun saveToken() {
        viewModel.postLoginUserResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success ->{
                    val token = "${it.data?.data?.token}"
                    if (token != ""){
                        //ini untuk set tokennya ke datastore
                        viewModel.setUserToken(token)
                        viewModel.SaveUserToken(token)
                        Snackbar.make(
                            binding.root,
                            getString(com.sigarda.donora.R.string.successSignIn),
                            Snackbar.LENGTH_LONG
                        )
                            .apply {
                                setAction(getString(com.sigarda.donora.R.string.ok)) {
                                }
                                show()
                            }
                    } else {
                        Toast.makeText(requireContext(), "token gagal di set", Toast.LENGTH_LONG).show()
                    }
                }else -> {} }
        }
    }

    private fun saveTokenGoogle() {
        viewModel.postLoginGoogleResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success ->{
                    val token = "${it.data?.data?.token}"
                    if (token != ""){
                        //ini untuk set tokennya ke datastore
                        viewModel.setUserToken(token)
                        viewModel.SaveUserToken(token)
                        Snackbar.make(
                            binding.root,
                            getString(com.sigarda.donora.R.string.successSignIn),
                            Snackbar.LENGTH_LONG
                        )
                            .apply {
                                setAction(getString(com.sigarda.donora.R.string.ok)) {
                                }
                                show()
                            }
                    } else {
                        Toast.makeText(requireContext(), "token gagal di set", Toast.LENGTH_LONG).show()
                    }
                }else -> {} }
        }
    }

    private fun openRegister() {
        findNavController().navigate(com.sigarda.donora.R.id.action_loginFragment_to_registerFragment)
    }
    private fun navigateToHome() {
        viewModel.setUserLogin(true)
        findNavController().navigate(com.sigarda.donora.R.id.action_loginFragment_to_nav_home)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}


