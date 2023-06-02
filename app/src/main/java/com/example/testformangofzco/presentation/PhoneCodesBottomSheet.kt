package com.example.testformangofzco.presentation

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testformangofzco.R
import com.example.testformangofzco.databinding.BottomSheetPhoneCodesBinding
import com.example.testformangofzco.presentation.adapters.CountryCode
import com.example.testformangofzco.presentation.adapters.CountryCodesAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PhoneCodesBottomSheet : BottomSheetDialogFragment(R.layout.bottom_sheet_phone_codes) {

    private val binding by viewBinding(BottomSheetPhoneCodesBinding::bind)

    private var adapter: CountryCodesAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }

    private fun initAdapter() {
        adapter = CountryCodesAdapter { code ->
            setFragmentResult(
                REQUEST_ACTION_KEY,
                bundleOf(PHONE_CODE_KEY to code)
            )
            findNavController().navigateUp()
        }
        adapter?.submitList(
            listOf(
                CountryCode(code = "+7", country = "Russia"),
                CountryCode(code = "+1", country = "USA")
            )
        )
        binding.rvCountryCodes.adapter = adapter
    }

    companion object {
        const val REQUEST_ACTION_KEY = "REQUEST_ACTION_KEY"
        const val PHONE_CODE_KEY = "PHONE_CODE_KEY"
    }
}