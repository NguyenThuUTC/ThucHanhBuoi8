package com.example.activityandnavigationex.ui.entry

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.activityandnavigationex.R
import com.example.activityandnavigationex.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    var binding: FragmentLoginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.run {
            txtLogin.setOnClickListener {
                val email = edtEmail.text.toString().trim()//.trim() remove dau cach o dau va cuooi
                val password = edtPassword.text.toString().trim()

                handleLogin(email, password)
            }
        }
    }

    private fun handleLogin(email: String, password: String) {

        val emailError = checkEmail(email)
        val passwordError = checkPassword(password)

        if (emailError == null && passwordError == null) {
            //valid info
            //navigate to home
        }


    }

    private fun checkPassword(password: String): String? {
        val passwordError = if (password.isEmpty()) {
            "Password cannot be empty"
        } else if (password.length < 8) {
            "Password must be at least 8 chars"
        } else null

        binding?.run {
            txtPasswordError.isVisible = passwordError != null
            txtPasswordError.text = passwordError
        }
        return passwordError
    }

    private fun checkEmail(email: String): String? {
        //check email valid?
        val emailError = if (email.isEmpty()) {
            "Email cannot be empty"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            "Invalid Email Format"
        } else null

        binding?.run {
            txtEmailError.isVisible = emailError != null
            txtEmailError.text = emailError
        }

        return emailError
    }
}