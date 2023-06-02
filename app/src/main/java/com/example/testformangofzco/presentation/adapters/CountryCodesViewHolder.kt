package com.example.testformangofzco.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testformangofzco.R
import com.example.testformangofzco.databinding.ItemCountryCodeBinding

class CountryCodesViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val binding by viewBinding(ItemCountryCodeBinding::bind)

    var countryCode: CountryCode? = null
        private set

    fun bind(countryCode: CountryCode) {
        this.countryCode = countryCode
        binding.tvCountryCode.text =
            view.resources.getString(R.string.country_code, countryCode.country, countryCode.code)
    }

    companion object {
        fun create(parent: ViewGroup) =
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_country_code, parent, false)
                .let(::CountryCodesViewHolder)
    }
}