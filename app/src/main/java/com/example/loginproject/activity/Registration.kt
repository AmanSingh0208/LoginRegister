package com.example.loginproject.activity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.loginproject.R
import com.example.loginproject.databinding.FragmentRegistrationBinding
import com.example.loginproject.viewmodel.RegistrationViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class Registration : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding

    private val viewModel by viewModels<RegistrationViewModel>()

    private lateinit var auth: FirebaseAuth

    companion object {
        fun newInstance() = Registration()
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            findNavController().navigate(R.id.action_loginUser_to_home2)            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_registration, container, false)

        binding.lifecycleOwner = this
        binding.registrationViewModel = viewModel

        auth = Firebase.auth

        binding.btnRegister.setOnClickListener {
            binding.regProgress.visibility = View.VISIBLE

            val email = binding.username.editText?.text.toString()
            val password = binding.password.editText?.text.toString()

            Log.e("Result", "Email = $email")
            Log.e("Result", "Pass = $password")


            if (email.isNotEmpty() && password.isNotEmpty()) {

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            binding.regProgress.visibility = View.GONE

                            // Sign in success, update UI with the signed-in user's information
//                            val user = auth.currentUser
                            showToast("User registered successfully. Please login.")
                        } else {
                            binding.regProgress.visibility = View.GONE

                            // If sign in fails, display a message to the user.
                            showToast("Authentication failed.")
                        }
                    }

                findNavController().navigate(R.id.action_registration_to_loginUser)
            } else {
                binding.regProgress.visibility = View.GONE

                showToast("Please input email/password")
            }
        }

        binding.btnBackLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registration_to_loginUser)
        }

        return binding.root
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}