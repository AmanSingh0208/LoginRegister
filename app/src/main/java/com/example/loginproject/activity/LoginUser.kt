package com.example.loginproject.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.loginproject.R
import com.example.loginproject.databinding.FragmentLoginUserBinding
import com.example.loginproject.viewmodel.LoginUserViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginUser : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onStart() {
        auth = Firebase.auth
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            findNavController().navigate(R.id.action_loginUser_to_home2)
        }
    }

    private lateinit var binding: FragmentLoginUserBinding

    private val viewModel by viewModels<LoginUserViewModel>()


    companion object {
        fun newInstance() = LoginUser()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.fragment_login_user, container, false)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_login_user, container, false)

        binding.lifecycleOwner = this
        binding.loginViewModel = viewModel

        auth = Firebase.auth

        onStart()

        binding.btnLogin.setOnClickListener {
            binding.loginProgress.visibility = View.VISIBLE

            val email = binding.textLoginUsername.editText?.text.toString()
            val password = binding.textLoginPass.editText?.text.toString()



            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            binding.loginProgress.visibility = View.GONE

                            // Sign in success, update UI with the signed-in user's information
                            val user = auth.currentUser
                            showToast("Logged in successfully.")
                            findNavController().navigate(R.id.action_loginUser_to_home2)
                        } else {
                            binding.loginProgress.visibility = View.GONE

                            // If sign in fails, display a message to the user.
                            showToast("Authentication failed. User not found.")
                        }

                    }

            } else {
                binding.loginProgress.visibility = View.GONE

                showToast("Please input username/password")
            }

        }



        binding.btnLoginRegistration.setOnClickListener {
            findNavController().navigate(R.id.action_loginUser_to_registration)
        }

        return binding.root

    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}