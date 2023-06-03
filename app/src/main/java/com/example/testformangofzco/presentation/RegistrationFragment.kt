package com.example.testformangofzco.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testformangofzco.R
import com.example.testformangofzco.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment(R.layout.fragment_registration) {

    private val binding by viewBinding(FragmentRegistrationBinding::bind)

    private val phone = requireArguments().getString(PHONE_KEY)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPhone()
    }

    private fun initPhone() {
        val formattedPhone =
            phone?.let {
                it.substring(ZERO, FIRST) + " " +
                it.substring(FIRST, FOURTH) + "-" +
                it.substring(FOURTH, SEVENTH) + "-" +
                it.substring(SEVENTH, NINTH) + "-" +
                it.substring(NINTH)
            }
        binding.tvPhoneNumber.text = formattedPhone
    }

    companion object {
        const val PHONE_KEY = "PHONE_KEY"
        private const val ZERO = 0
        private const val FIRST = 1
        private const val FOURTH = 4
        private const val SEVENTH = 7
        private const val NINTH = 9
    }
}