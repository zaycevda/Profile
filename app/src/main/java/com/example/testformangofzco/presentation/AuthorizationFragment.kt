package com.example.testformangofzco.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testformangofzco.R
import com.example.testformangofzco.databinding.FragmentAuthorizationBinding
import com.example.testformangofzco.presentation.PhoneCodesBottomSheet.Companion.PHONE_CODE_KEY
import com.example.testformangofzco.presentation.PhoneCodesBottomSheet.Companion.REQUEST_ACTION_KEY
import com.example.testformangofzco.utils.MaskTextWatcher
import com.example.testformangofzco.utils.setCountryCode

class AuthorizationFragment : Fragment(R.layout.fragment_authorization) {

    private val binding by viewBinding(FragmentAuthorizationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCountryCode()

        binding.etPhoneNumber.addTextChangedListener(MaskTextWatcher)
    }

    private fun setCountryCode() {
        binding.tvPhoneCountryCode.setOnClickListener {
            findNavController().navigate(R.id.action_authorizationFragment_to_phoneCodesBottomSheet)
        }
        setFragmentResultListener(REQUEST_ACTION_KEY) { _, bundle ->
            bundle.getString(PHONE_CODE_KEY)?.let { code ->
                binding.tvPhoneCountryCode.text = code
                binding.tvPhoneCountryCode.setCountryCode(code)
            }
        }
    }

}