package com.sigarda.donora.ui.fragment.auth.register

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
import com.sigarda.donora.R
import com.sigarda.donora.data.network.models.auth.register.requestbody.RegisterRequestBody
import com.sigarda.donora.databinding.FragmentRegisterBinding
import com.sigarda.donora.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private val existUsername = listOf<String>("shawn","peter","raul","mendes")
    private val existEmail = listOf<String>("shawn@test.com","peter@test.com","raul@test.com","mendes@test.com")

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel : RegisterViewModel by viewModels()


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

        binding.tvLogin.setOnClickListener { openLogin() }
        binding.btnResgiter.setOnClickListener { register() }
        observeData()

    }

    private fun openLogin() {
        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
    }

    private fun register() {
        if (validateInput()) {
            applyRegister()
        }
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

    private fun applyRegister() {
        val email = binding.etEmailRegister.text.toString()
        val username = binding.etUsernameRegister.text.toString()
        val password = binding.etPasswordRegister.text.toString()
        val passwordConfirm = binding.etPasswordConfirmation.text.toString()


        registerUser(username,email,password)

    }

    private fun registerUser(username : String ,email : String ,password: String) {
        viewModel.postRegisterUser(RegisterRequestBody(username = username, email = email, password = password))
        Log.d("register", RegisterRequestBody(username = username, email = email, password = password).toString())
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}