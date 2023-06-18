package com.sigarda.donora.ui.fragment.auth.register

import android.content.IntentSender
import android.os.Bundle
import android.service.controls.ControlsProviderService
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import com.google.firebase.messaging.FirebaseMessaging
import com.sigarda.donora.R
import com.sigarda.donora.data.network.models.auth.create.CreateProfileRequestBody
import com.sigarda.donora.data.network.models.auth.google.login.GoogleAuthRequestBody
import com.sigarda.donora.data.network.models.auth.register.requestbody.RegisterRequestBody
import com.sigarda.donora.databinding.FragmentRegisterBinding
import com.sigarda.donora.ui.fragment.auth.login.LoginViewModel
import com.sigarda.donora.ui.fragment.base.BaseFragment
import com.sigarda.donora.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import org.chromium.base.ContextUtils

@AndroidEntryPoint
class RegisterFragment : BaseFragment() {

    private  val TAG = "Fragment Register"
    private var mGoogleSignInClient : GoogleSignInClient? = null

    private var oneTapClient: SignInClient? = null
    private var signUpRequest: BeginSignInRequest? = null
    private var signInRequest: BeginSignInRequest? = null

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel : RegisterViewModel by viewModels()
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
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestServerAuthCode("935126427076-jgcmqckpb2af307vrut7tfhng0j7itkd.apps.googleusercontent.com")
            .requestEmail()
            .build()

        bottomNavigationViewVisibility = View.GONE

        mGoogleSignInClient = GoogleSignIn.getClient(this.requireContext(), gso);


        oneTapClient()
        observeDataLoginGoogle()

        binding.tvLogin.setOnClickListener { openLogin() }
        binding.btnResgiter.setOnClickListener { register() }
        binding.registerGoogle.setOnClickListener (){ displaySignUp() }
        observeData()

    }

    fun push() {
        FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
            Log.d(TAG, "push: $token")
            val tokenFCM = token}
    }

    private fun observeData() {
        viewModel.postRegisterUserResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    if (it.data?.success == true) {
                        Toast.makeText(requireContext(), it.data?.message, Toast.LENGTH_LONG).show()
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                        Log.d("Register Response", it.data?.message.toString())
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        }

    }

    private fun observeDataLoginGoogle() {
        viewModel.postLoginGoogleResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    Log.d("loginGoogleResponse", it.data.toString())
                    navigateToHome()
                    saveTokenGoogle()
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        }
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
                val msg = "Helo : $name, Your Email : $email, Your idToken: $idToken"

                var tokenGoogle = "$idToken"

                FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
                    Log.d(TAG, "push: $token")
                    val tokenFCM = token

                viewModel.loginGoogle(parseFormIntoEntityGoogle(tokenGoogle,tokenFCM))
                Log.d("one tap", msg)
                }
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


    private fun register() {
        if (validateInput()) {
            applyRegister()
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


    private fun createUserfromGoogle(){
        viewModel.postLoginGoogleResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success ->{
                    val id = "${it.data?.data?.user?.id}".toInt()
                    if (id != null){
                        authViewModel.getDataStoreToken().observe(viewLifecycleOwner) {
                            viewModel.postCreateUser("Bearer $it", parseFormIntoCreateUser(id))}
                    } else {
                        Snackbar.make(
                            binding.root,
                            getString(R.string.failedCreateUser),
                            Snackbar.LENGTH_LONG
                        )
                            .apply {
                                setAction(getString(R.string.ok)) {
                                }
                                show()
                            }
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
                        authViewModel.setUserToken(token)
                        authViewModel.SaveUserToken(token)
                        createUserfromGoogle()
                        Snackbar.make(
                            binding.root,
                            getString(R.string.successSignIn),
                            Snackbar.LENGTH_LONG
                        )
                            .apply {
                                setAction(getString(R.string.ok)) {
                                }
                                show()
                            }
                    } else {
                        Toast.makeText(requireContext(), "token gagal di set", Toast.LENGTH_LONG).show()
                    }
                }else -> {} }
        }
    }

    private fun applyRegister() {
        val email = binding.etEmailRegister.text.toString()
        val username = binding.etUsernameRegister.text.toString()
        val password = binding.etPasswordRegister.text.toString()
        val passwordConfirm = binding.etPasswordConfirmation.text.toString()

        FirebaseMessaging.getInstance().token.addOnSuccessListener { token -> Log.d(TAG, "push: $token")
            val tokenFCM = token

        registerUser(username,email,password,tokenFCM)
        }
    }

    private fun registerUser(username : String ,email : String ,password: String, deviceKey: String) {
        viewModel.postRegisterUser(RegisterRequestBody(username = username, email = email, password = password, device_key = deviceKey))
        Log.d("register", RegisterRequestBody(username = username, email = email, password = password,device_key = deviceKey).toString())
    }

    private fun validateInput(): Boolean {
        var isValid = true
        val email = binding.etEmailRegister.text.toString()
        val username = binding.etUsernameRegister.text.toString().trim()
        val password = binding.etPasswordRegister.text.toString().trim()
        val passwordConfrim = binding.etPasswordConfirmation.text.toString().trim()



        if (email.isEmpty()) {
            isValid = false
            binding.etEmailRegister.error = "Email must not be empty"
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            isValid = false
            binding.etEmailRegister.error = "Invalid email"
        }
        if (password.isEmpty()) {
            isValid = false
            binding.etPasswordRegister.error = "Invalid Pasword"
        }
        if (username.isEmpty()) {
            isValid = false
            binding.etUsernameRegister.error = "Invalid username"
        }

        if (password.length < 8) {
            isValid = false
            Toast.makeText(
                requireContext(),
                "Password should be at least 8 characters",
                Toast.LENGTH_SHORT
            ).show()
        }

        if (passwordConfrim != password) {
            isValid = false
            Toast.makeText(
                requireContext(),
                "Confirmation Password Is Wrong",
                Toast.LENGTH_SHORT
            ).show()
        }
        return isValid
    }

    private fun navigateToHome() {
        authViewModel.setUserLogin(true)
        findNavController().navigate(R.id.action_registerFragment_to_nav_home)

    }

    private fun openLogin() {
        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
    }

    private fun parseFormIntoEntityGoogle(idToken: String, deviceKey : String): GoogleAuthRequestBody {
        return GoogleAuthRequestBody(idToken,deviceKey)
    }

    private fun parseFormIntoCreateUser(id: Int): CreateProfileRequestBody {
        return CreateProfileRequestBody(id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}