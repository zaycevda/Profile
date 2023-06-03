package com.example.testformangofzco.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.domain.models.CheckAuth
import com.example.domain.models.Phone
import com.example.testformangofzco.App
import com.example.testformangofzco.R
import com.example.testformangofzco.databinding.FragmentAuthorizationBinding
import com.example.testformangofzco.presentation.PhoneCodesBottomSheet.Companion.COUNTRY_CODE_KEY
import com.example.testformangofzco.presentation.PhoneCodesBottomSheet.Companion.REQUEST_ACTION_KEY
import com.example.testformangofzco.presentation.RegistrationFragment.Companion.PHONE_KEY
import com.example.testformangofzco.utils.AuthSharedPrefs
import com.example.testformangofzco.utils.MaskTextWatcher
import com.example.testformangofzco.utils.setCountryCode
import com.example.testformangofzco.utils.showToast
import com.example.testformangofzco.viewmodels.AuthorizationViewModel
import com.example.testformangofzco.viewmodels.AuthorizationViewModelFactory
import javax.inject.Inject

class AuthorizationFragment : Fragment(R.layout.fragment_authorization) {

    @Inject
    lateinit var authorizationViewModelFactory: AuthorizationViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, authorizationViewModelFactory)[AuthorizationViewModel::class.java]
    }

    private val binding by viewBinding(FragmentAuthorizationBinding::bind)

    private val sharedPreferences by lazy { AuthSharedPrefs(requireActivity()) }

    private var countryCode = "+7"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inject()

        auth()

        checkAuth()

        setCountryCode()

        binding.etPhoneNumber.addTextChangedListener(MaskTextWatcher)
    }

    private fun inject() {
        App.component.injectAuthorizationFragment(authorizationFragment = this)
    }

    private fun auth() {
        binding.btnGetCode.setOnClickListener {
            val phoneNumber = binding.etPhoneNumber.text?.replace("-".toRegex(), "").toString()
            val phone = Phone(phone = countryCode + phoneNumber)

            Log.d(TAG, "auth: phone = $phone")

            viewModel.auth(phone = phone)
            lifecycleScope.launchWhenCreated {
                viewModel.success.collect { state ->
                    state.on(
                        error = {
                            showToast("error: ${it.message}")
                            Log.e(TAG, "auth: ${it.message}")
                        },
                        loading = {
                            binding.clAuthorization.isGone = true
                            binding.pbAuthorization.isGone = false
                        },
                        success = { success ->
                            Log.d(TAG, "auth: isSuccess = $success")
                            if (success.isSuccess) {
                                binding.clAuthorization.isGone = false
                                binding.pbAuthorization.isGone = true
                                binding.etCode.isGone = false
                                binding.rlPhone.isGone = true
                                binding.btnGetCode.isGone = true
                                binding.btnLogIn.isGone = false
                            } else {
                                binding.clAuthorization.isGone = false
                                binding.pbAuthorization.isGone = true
                            }
                        }
                    )
                }
            }
        }
    }

    private fun checkAuth() {
        binding.btnLogIn.setOnClickListener {
            val phoneNumber = binding.etPhoneNumber.text?.replace("-".toRegex(), "").toString()
            val phone = countryCode + phoneNumber
            val code = binding.etCode.text.toString()
            val checkAuth = CheckAuth(phoneNumber = phone, code = code)

            Log.d(TAG, "checkAuth: phone = $checkAuth")
            viewModel.checkAuth(checkAuth = checkAuth)
            lifecycleScope.launchWhenCreated {
                viewModel.auth.collect { state ->
                    state.on(
                        error = {
                            showToast("error: ${it.message}")
                            Log.e(TAG, "checkAuth: $it")
                            binding.clAuthorization.isGone = false
                            binding.pbAuthorization.isGone = true
                        },
                        loading = {
                            binding.clAuthorization.isGone = true
                            binding.pbAuthorization.isGone = false
                        },
                        success = { auth ->
                            Log.d(TAG, "checkAuth: auth = $auth")
                            if (auth.isUserExists) sharedPreferences.logIn(MainActivity())
                            else findNavController().navigate(
                                R.id.action_authorizationFragment_to_registrationFragment,
                                bundleOf(PHONE_KEY to phone)
                            )
                        }
                    )
                }
            }
        }
    }

    private fun setCountryCode() {
        binding.tvCountryCode.setOnClickListener {
            findNavController().navigate(R.id.action_authorizationFragment_to_phoneCodesBottomSheet)
        }
        setFragmentResultListener(REQUEST_ACTION_KEY) { _, bundle ->
            bundle.getString(COUNTRY_CODE_KEY)?.let { countryCode ->
                this.countryCode = countryCode
                binding.tvCountryCode.text = countryCode
                binding.tvCountryCode.setCountryCode(countryCode)
            }
        }
    }

    private companion object {
        private const val TAG = "AuthorizationFragment"
    }
}