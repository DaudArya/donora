package com.sigarda.donora.ui.fragment.auth.login


import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.sigarda.donora.R
import com.sigarda.donora.data.network.models.auth.login.requestbody.LoginRequestBody
import com.sigarda.donora.databinding.FragmentLoginBinding
import com.sigarda.donora.utils.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()
    
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
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLoginStatus().observe(viewLifecycleOwner) {
            if (it == true) {
                navigateToHome()
            } else {
                requireContext()
            }
        }
        observeDataLogin()
        binding.tvCreateAccount.setOnClickListener { openRegister() }
        binding.btnLogin.setOnClickListener (){
            loginUser()
        }
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

    private fun parseFormIntoEntity(username: String, password: String): LoginRequestBody {
        return LoginRequestBody(username, password)
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

    private fun openRegister() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }
    private fun navigateToHome() {
        viewModel.setUserLogin(true)
        findNavController().navigate(R.id.action_loginFragment_to_nav_home)

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}