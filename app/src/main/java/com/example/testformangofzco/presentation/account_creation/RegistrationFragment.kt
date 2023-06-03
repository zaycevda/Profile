package com.example.testformangofzco.presentation.account_creation

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.domain.models.UserMini
import com.example.testformangofzco.App
import com.example.testformangofzco.R
import com.example.testformangofzco.databinding.FragmentRegistrationBinding
import com.example.testformangofzco.presentation.user_profile.UserProfileActivity
import com.example.testformangofzco.utils.AuthSharedPrefs
import com.example.testformangofzco.utils.showToast
import com.example.testformangofzco.viewmodels.account_creation.RegistrationViewModel
import com.example.testformangofzco.viewmodels.account_creation.RegistrationViewModelFactory
import javax.inject.Inject

class RegistrationFragment : Fragment(R.layout.fragment_registration) {

    @Inject
    lateinit var registrationViewModelFactory: RegistrationViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, registrationViewModelFactory)[RegistrationViewModel::class.java]
    }

    private val binding by viewBinding(FragmentRegistrationBinding::bind)

    private val sharedPreferences by lazy { AuthSharedPrefs(requireActivity()) }

    private val phone by lazy { arguments?.getString(PHONE_KEY) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inject()

        initPhone()

        registration()
    }

    private fun inject() {
        App.component.injectRegistrationFragment(registrationFragment = this)
    }

    private fun initPhone() {
        val formattedPhone =
            phone?.let {
                it.substring(ZERO, SECOND) + SPACE +
                it.substring(SECOND, FIFTH) + DASH +
                it.substring(FIFTH, EIGHTH) + DASH +
                it.substring(EIGHTH, TENTH) + DASH +
                it.substring(TENTH)
            }
        binding.tvPhoneNumber.text = formattedPhone
    }

    private fun registration() {
        binding.btnSignUp.setOnClickListener {
            if (binding.etUsername.text.length < 5) showToast(getString(R.string.username_min_length))
            else {
                val name = binding.etName.text.toString()
                val username = binding.etUsername.text.toString()
                val userMini = UserMini(phone = phone ?: BLANK, name = name, username = username)

                viewModel.registration(userMini = userMini)
                lifecycleScope.launchWhenCreated {
                    viewModel.tokens.collect { state ->
                        state.on(
                            error = {
                                showToast(message = getString(R.string.error, it.message))
                                binding.pbRegistration.isGone = true
                                binding.llRegistration.isGone = false
                            },
                            loading = {
                                binding.pbRegistration.isGone = false
                                binding.llRegistration.isGone = true
                            },
                            success = {
                                sharedPreferences.logIn(UserProfileActivity())
                            }
                        )
                    }
                }
            }
        }
    }

    companion object {
        const val PHONE_KEY = "PHONE_ID"
        private const val BLANK = ""
        private const val DASH = "-"
        private const val SPACE = " "
        private const val ZERO = 0
        private const val SECOND = 2
        private const val FIFTH = 5
        private const val EIGHTH = 8
        private const val TENTH = 10
    }
}