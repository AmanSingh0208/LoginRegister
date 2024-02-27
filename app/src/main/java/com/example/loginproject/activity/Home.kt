package com.example.loginproject.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.loginproject.R
import com.example.loginproject.databinding.FragmentHomeBinding
import com.example.loginproject.viewmodel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Home : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser
    companion object {
        fun newInstance() = Home()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.fragment_home, container, false)
        auth = FirebaseAuth.getInstance()

        user = auth.currentUser!!

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        binding.lifecycleOwner = this
        binding.homeViewModel = viewModel

        if (user == null) {
            findNavController().navigate(R.id.action_home2_to_loginUser)
        } else {
            binding.txtLogout.text = "User: ${user.email}"
        }

        binding.btnLogout.setOnClickListener {
            auth.signOut()
            findNavController().navigate(R.id.action_home2_to_loginUser)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}