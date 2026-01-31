package com.example.activityandnavigationex.ui.entry

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.activityandnavigationex.R
import com.example.activityandnavigationex.databinding.FragmentLoginBinding
import com.example.activityandnavigationex.ui.main.HomeActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    var binding: FragmentLoginBinding? = null
    val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewLifecycleOwner.lifecycleScope.launch {
//            delay(1000)
//            Log.d("test", "coroutine")
//            val count = 1
//            //other commands
//        }
//
//        viewModel.viewModelScope.launch {
//            //
//        }
        binding?.run {

            //c1 handle data with savedInstanceState
//            val emailErr = savedInstanceState?.getString(EMAIL_ERROR_KEY)
//            val passwordErr = savedInstanceState?.getString(PASSWORD_ERROR_KEY)
//            txtEmailError.text = emailErr
//            txtEmailError.isVisible = emailErr != null

            txtLogin.setOnClickListener {

                viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Default) {
                    Log.d("coroutine delay", "1")
                    delay(10000)
                    Log.d("coroutine delay", "2")
                }

                val email = edtEmail.text.toString().trim()//.trim() remove dau cach o dau va cuooi
                val password = edtPassword.text.toString().trim()
                viewModel.handleLogin(email, password)
            }
        }

        observeData()
    }

    private fun observeData() {
        viewModel.uiLoginState.observe(viewLifecycleOwner) { uiState ->
            when(uiState) {
                is ValidationResult.Valid -> {
                    val intent = Intent(requireContext(),
                        HomeActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
                is ValidationResult.Invalid -> {
                    val emailError = uiState.error.first
                    val passwordError = uiState.error.second
                    showEmailError(emailError)
                    showPasswordError(passwordError)
                }
            }
        }
    }

    private fun showEmailError(emailErr: ErrorUiState?) = binding?.run {
        txtEmailError.isVisible = emailErr != null
        when(emailErr) {
            is ErrorUiState.EmailEmpty -> {
                txtEmailError.text = getString(R.string.email_cannot_be_empty)
            }

            is ErrorUiState.WrongEmailFormat -> {
                txtEmailError.text = getString(R.string.invalid_email_format)
            }
            else ->  {
                //do nothing
            }
        }
    }

    private fun showPasswordError(passwordErr: ErrorUiState?) = binding?.run {
        txtPasswordError.isVisible = passwordErr != null
        when(passwordErr) {
            is ErrorUiState.PasswordEmpty -> {
                txtPasswordError.text = getString(R.string.password_cannot_be_empty)
            }

            is ErrorUiState.PasswordTooShort -> {
                txtPasswordError.text = getString(R.string.password_must_be_at_least_8_chars)
            }
            else ->  {
                //do nothing
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EMAIL_ERROR_KEY, "${binding?.txtEmailError?.text}")
        outState.putString(PASSWORD_ERROR_KEY, "${binding?.txtPasswordError?.text}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val EMAIL_ERROR_KEY = "EMAIL_ERROR_KEY"
        const val PASSWORD_ERROR_KEY = "PASSWORD_ERROR_KEY"
    }
}