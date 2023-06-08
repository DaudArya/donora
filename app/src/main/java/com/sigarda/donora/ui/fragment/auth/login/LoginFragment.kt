package com.sigarda.donora.ui.fragment.auth.login


import android.content.Intent
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
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.sigarda.donora.R
import com.sigarda.donora.data.network.models.auth.create.CreateProfileRequestBody
import com.sigarda.donora.data.network.models.auth.google.login.GoogleAuthRequestBody
import com.sigarda.donora.data.network.models.auth.login.requestbody.LoginRequestBody
import com.sigarda.donora.databinding.FragmentLoginBinding
import com.sigarda.donora.ui.fragment.base.BaseFragment
import com.sigarda.donora.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import org.chromium.base.ContextUtils.getApplicationContext


@AndroidEntryPoint
class LoginFragment : BaseFragment(), View.OnClickListener {

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
         arguments.let {

         }
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

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestServerAuthCode("935126427076-jgcmqckpb2af307vrut7tfhng0j7itkd.apps.googleusercontent.com")
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this.requireContext(), gso);
//        binding.loginGoogle.setOnClickListener(this);

        oneTapClient()
        observeDataLogin()
        observeDataLoginGoogle()

        bottomNavigationViewVisibility = View.GONE

        binding.btnLogin.setOnClickListener (){ loginUser() }
        binding.btnLoginguest.setOnClickListener (){ navigateToHome() }
        binding.tvCreateAccount.setOnClickListener (){ openRegister() }
        binding.loginGoogle.setOnClickListener (){ displaySignUp() }

    }

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this.requireContext())
        updateUI(account)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        activity.apply {
            super.onActivityResult(requestCode, resultCode, data)

            // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
            if (requestCode == RC_SIGN_IN) {
                // The Task returned from this call is always completed, no need to attach
                // a listener.
                val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(task)
            }
        }

    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        activity.apply {
            try {
                val account = completedTask.getResult(ApiException::class.java)

                // Signed in successfully, show authenticated UI.
                updateUI(account)
            } catch (e: ApiException) {
                // The ApiException status code indicates the detailed failure reason.
                // Please refer to the GoogleSignInStatusCodes class reference for more information.
                Log.w(TAG, "signInResult:failed code=" + e.statusCode)
                updateUI(null)
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
                    val msg = "Helo : $name, Your Email : $email, Your idToken: $idToken"
                    var tokenGoogle = "$idToken"
                    viewModel.loginGoogle(parseFormIntoEntityGoogle(tokenGoogle))
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

    private fun signIn() {
        val signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
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
                    Log.d("loginGoogleResponse", it.data.toString())
                    viewModel.statusLogin(true)
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
                val authCode = account.serverAuthCode
                val idToken = account.idToken
                val name = account.displayName
                val email = account.email
//                viewModel.loginGoogle(parseFormIntoEntityGoogle(authCode))
                val msg = "Helo : $name,AuthCode : $authCode, Your Email : $email, Your Token: $idToken"
                Log.d("authcode",msg)

            } catch (e: Exception) {
                if (e.message != null) Log.e(TAG, e.message!!)
                Toast.makeText(
                    getApplicationContext(),
                    "Error",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("authcode", "Error getting google auth code")
            }
        }else {
            Log.d("authcode", "Error getting google auth code")
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

    private fun createUser(){
        viewModel.postLoginUserResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success ->{
                    val id = "${it.data?.data?.user?.id}".toInt()
                    val profile = "${it.data?.data?.user?.user_profile}"
                    if (profile == "null"){
                        viewModel.getDataStoreToken().observe(viewLifecycleOwner) {
                            viewModel.postCreateUser("Bearer $it", parseFormIntoCreateUser(id))}
                        val msg = "Your Id : $id"
                        Log.d("Id User",msg)
                    } else {
                        Snackbar.make(
                            binding.root,
                            getString(R.string.useralreadycreated),
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

    private fun createUserfromGoogle(){
        viewModel.postLoginGoogleResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success ->{
                    val id = "${it.data?.data?.user?.id}".toInt()
                    val profile = "${it.data?.data?.user?.user_profile}"
                    if (profile == "null"){
                        viewModel.getDataStoreToken().observe(viewLifecycleOwner) {
                            viewModel.postCreateUser("Bearer $it", parseFormIntoCreateUser(id))}
                        val msg = "Your Id : $id"
                        Log.d("Id User",msg)
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
                }is Resource.Error ->{
                Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_LONG).show()
                }

                else -> {}
            }
        }
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
//                        createUser()
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
                        Toast.makeText(requireContext(), "${it.data?.message}", Toast.LENGTH_LONG).show()
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
//                        createUserfromGoogle()
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

    private fun openRegister() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }
    private fun navigateToHome() {
        viewModel.setUserLogin(true)
        findNavController().navigate(R.id.action_loginFragment_to_nav_home)

    }

    private fun parseFormIntoEntity(username: String, password: String): LoginRequestBody {
        return LoginRequestBody(username, password)
    }

    private fun parseFormIntoEntityGoogle(idToken: String): GoogleAuthRequestBody {
        return GoogleAuthRequestBody(idToken)
    }

    private fun parseFormIntoCreateUser(id: Int): CreateProfileRequestBody {
        return CreateProfileRequestBody(id)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.login_google -> signIn()
        }
    }




}


